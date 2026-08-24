package com.mybury.waver.service;

import com.mybury.waver.common.code.BucketStatus;
import com.mybury.waver.common.code.ExposureStatus;
import com.mybury.waver.common.code.MigrationStatus;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.Category;
import com.mybury.waver.domain.MigrationInfo;
import com.mybury.waver.domain.User;
import com.mybury.waver.domain.mybury.MyburyBucketlist;
import com.mybury.waver.domain.mybury.MyburyCategory;
import com.mybury.waver.domain.mybury.MyburyUser;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.CategoryRepository;
import com.mybury.waver.repository.MigrationInfoRepository;
import com.mybury.waver.repository.MyburyBucketlistRepository;
import com.mybury.waver.repository.MyburyCategoryRepository;
import com.mybury.waver.repository.MyburyUserRepository;
import com.mybury.waver.repository.UserRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * mybury 레거시 DB → waver 데이터 이관.
 * 사용자 단위로 한 트랜잭션 — 중간 실패 시 전체 롤백되어 재시도해도 중복 생성되지 않는다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MyburyMigrationService {

  private final MigrationInfoRepository migrationInfoRepository;
  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;
  private final BucketRepository bucketRepository;
  private final MyburyUserRepository myburyUserRepository;
  private final MyburyCategoryRepository myburyCategoryRepository;
  private final MyburyBucketlistRepository myburyBucketlistRepository;

  @Transactional
  public void migrateUser(long migrationInfoId) {
    // 스케줄러가 트랜잭션 밖에서 조회한 엔티티는 detached라 이 트랜잭션에서 재조회한다
    MigrationInfo info = migrationInfoRepository.findById(migrationInfoId).orElse(null);
    if (info == null || info.getStatus() != MigrationStatus.REQUESTED) {
      return;
    }

    User user = userRepository.findById(info.getUserId()).orElse(null);
    if (user == null) {
      log.warn("mybury migration skipped. waver user not found: userId={}", info.getUserId());
      return;
    }

    MyburyUser myburyUser = myburyUserRepository.findByEmail(user.getEmail()).orElse(null);
    if (myburyUser == null) {
      log.warn("mybury migration skipped. mybury user not found: userId={}", info.getUserId());
      return;
    }

    Map<String, Long> categoryIdMap = migrateCategories(user.getId(), myburyUser.getId());
    int bucketCount = migrateBuckets(user.getId(), myburyUser.getId(), categoryIdMap);

    info.setStatus(MigrationStatus.COMPLETED);
    log.info("mybury migration completed. userId={}, categories={}, buckets={}",
        user.getId(), categoryIdMap.size(), bucketCount);
  }

  // mybury 카테고리 id → waver 카테고리 id 매핑을 만든다.
  // 기본 카테고리는 waver 가입 시 생성된 기본 카테고리로 합치고, 나머지는 같은 이름이 있으면 재사용, 없으면 생성.
  private Map<String, Long> migrateCategories(long userId, String myburyUserId) {
    Long defaultCategoryId = categoryRepository.findIdByUserIdAndDefaultYn(userId, YesNo.Y);

    Map<String, Long> categoryIdMap = new HashMap<>();
    List<MyburyCategory> categories = myburyCategoryRepository.findByUserIdOrderByPriorityAsc(myburyUserId);
    for (MyburyCategory myburyCategory : categories) {
      if ((myburyCategory.isDefaultCategory() || myburyCategory.getName() == null) && defaultCategoryId != null) {
        categoryIdMap.put(myburyCategory.getId(), defaultCategoryId);
        continue;
      }
      Category category = categoryRepository
          .findFirstByUserIdAndNameAndDeleted(userId, myburyCategory.getName(), YesNo.N)
          .orElseGet(() -> categoryRepository.save(Category.builder()
              .name(myburyCategory.getName())
              .userId(userId)
              .seq(myburyCategory.getPriority() == null ? 0 : myburyCategory.getPriority())
              .build()));
      categoryIdMap.put(myburyCategory.getId(), category.getId());
    }
    return categoryIdMap;
  }

  // 이관 버킷은 요구사항에 따라 전부 PRIVATE (레거시 open 값 무시)
  private int migrateBuckets(long userId, String myburyUserId, Map<String, Long> categoryIdMap) {
    Long defaultCategoryId = categoryRepository.findIdByUserIdAndDefaultYn(userId, YesNo.Y);

    List<MyburyBucketlist> buckets = myburyBucketlistRepository.findByUserIdOrderByOrderSeqAsc(myburyUserId);
    for (MyburyBucketlist mybury : buckets) {
      boolean completed = mybury.getCompletedDt() != null;
      Bucket bucket = Bucket.builder()
          .title(mybury.getTitle())
          .memo(mybury.getMemo())
          .userId(userId)
          .categoryId(categoryIdMap.getOrDefault(mybury.getCategoryId(), defaultCategoryId))
          .exposureStatus(ExposureStatus.PRIVATE)
          .pin(Boolean.TRUE.equals(mybury.getPin()) ? YesNo.Y : YesNo.N)
          .status(completed ? BucketStatus.COMPLETE : BucketStatus.PROGRESS)
          .completedDate(mybury.getCompletedDt())
          .targetDate(mybury.getDDate() == null ? null : mybury.getDDate().toLocalDate())
          .goalCount(mybury.getGoalCount() == null ? 1 : mybury.getGoalCount())
          .userCount(mybury.getUserCount() == null ? 0 : mybury.getUserCount())
          .seq(mybury.getOrderSeq() == null ? 0 : mybury.getOrderSeq())
          .imgUrl(mybury.getImgUrl1())
          .build();
      bucketRepository.save(bucket);
    }
    return buckets.size();
  }
}
