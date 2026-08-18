package com.mybury.waver.service;

import com.mybury.waver.common.code.*;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.BucketMember;
import com.mybury.waver.domain.FreeTier;
import com.mybury.waver.domain.User;
import com.mybury.waver.event.message.AlarmMessageEvent;
import com.mybury.waver.event.message.BadgeCountEvent;
import com.mybury.waver.exception.WaverException;
import com.mybury.waver.repository.BucketMemberRepository;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.CategoryRepository;
import com.mybury.waver.repository.FreeTierRepository;
import com.mybury.waver.repository.LikeBucketRepository;
import com.mybury.waver.repository.ReportRepository;
import com.mybury.waver.repository.UserRepository;
import com.mybury.waver.util.FileUploadUtils;
import com.mybury.waver.web.message.v1.bucket.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BucketService {

  private static final int POPULAR_BUCKET_LIMIT = 4;
  private static final int MAX_IMAGE_COUNT = 3;

  private final FileUploadUtils fileUploadUtils;
  private final BucketRepository bucketRepository;
  private final LikeBucketRepository likeBucketRepository;
  private final ReportRepository reportRepository;
  private final UserRepository userRepository;
  private final FreeTierRepository freeTierRepository;
  private final BucketMemberRepository bucketMemberRepository;
  private final CategoryRepository categoryRepository;
  private final ApplicationEventPublisher publisher;

  @Transactional
  public BucketDetailResponse create(long userId, BucketCreateRequest request) {
    int imageCount = ObjectUtils.isEmpty(request.images()) ? 0 : request.images().size();
    checkSaveLimits(userId, imageCount,
        imageCount > 1,
        StringUtils.hasText(request.friendUserIds()));

    Bucket bucket = request.toBucket(userId);

    if (!ObjectUtils.isEmpty(request.images())) {
      String imageUrl = request.images().stream().map(fileUploadUtils::uploadFile)
          .collect(Collectors.joining(","));
      bucket.setImgUrl(imageUrl);
    }
    Bucket saved = bucketRepository.save(bucket);
    syncBucketMembers(saved);
    bucketRepository.commit();

    processBadgeCount(userId, request.keywords());

    return bucketDetail(saved.getId(), userId);
  }

  /**
   * 함께하기(TOGETHER) 참여자 동기화.
   * 소유자 + friendUserIds 전원의 BucketMember row를 유지한다.
   * 카테고리 매핑: 소유자 = 버킷에 지정한 카테고리, 친구 = 각자의 기본(default) 카테고리.
   * 참여자에서 빠진 사용자 row는 삭제하고, 기존 참여자의 진행도는 유지한다.
   */
  private void syncBucketMembers(Bucket bucket) {
    List<BucketMember> existing = bucketMemberRepository.findByBucketId(bucket.getId());

    boolean together = bucket.getType() == ContentType.TOGETHER
        && StringUtils.hasText(bucket.getFriendUserIds());
    if (!together) {
      if (!existing.isEmpty()) {
        bucketMemberRepository.deleteAll(existing);
      }
      return;
    }

    Set<Long> memberUserIds = new LinkedHashSet<>();
    memberUserIds.add(bucket.getUserId());
    Arrays.stream(bucket.getFriendUserIds().split(","))
        .map(String::trim)
        .filter(id -> id.matches("\\d+"))
        .map(Long::parseLong)
        .forEach(memberUserIds::add);

    Map<Long, BucketMember> existingByUserId = existing.stream()
        .collect(Collectors.toMap(BucketMember::getUserId, Function.identity()));

    List<BucketMember> removed = existing.stream()
        .filter(member -> !memberUserIds.contains(member.getUserId()))
        .toList();
    if (!removed.isEmpty()) {
      bucketMemberRepository.deleteAll(removed);
    }

    for (Long memberUserId : memberUserIds) {
      boolean isOwner = memberUserId.equals(bucket.getUserId());
      BucketMember member = existingByUserId.get(memberUserId);
      if (member == null) {
        Long categoryId = isOwner
            ? bucket.getCategoryId()
            : categoryRepository.findIdByUserIdAndDefaultYn(memberUserId, YesNo.Y);
        bucketMemberRepository.save(BucketMember.of(bucket.getId(), memberUserId, categoryId));
        if (!isOwner) {
          // 새로 추가된 친구에게 초대 알림 (트랜잭션 커밋 후 발송)
          publisher.publishEvent(AlarmMessageEvent.togetherInvite(
              memberUserId, bucket.getUserId(), bucket.getId(), bucket.getTitle()));
        }
      } else if (isOwner) {
        member.setCategoryId(bucket.getCategoryId()); // 소유자가 버킷 카테고리를 바꾼 경우 따라간다
      }
    }
  }

  /**
   * 저장 제한 검사 및 무료 사용 횟수 차감.
   * <p>
   * - 이미지: 구독 여부와 무관하게 최대 {@value MAX_IMAGE_COUNT}개
   * - 무료 사용자: 이미지 2개 이상 저장은 제공된 횟수(기본 1회)만, 함께하기는 제공된 횟수(기본 3회)만 가능
   * - 구독(ACTIVE) 사용자: 이미지 하드캡 외 제한 없음
   */
  private void checkSaveLimits(long userId, int imageCount, boolean usesMultiImage, boolean usesTogether) {
    if (imageCount > MAX_IMAGE_COUNT) {
      throw new WaverException(ResultCode.IMAGE_LIMIT_EXCEEDED);
    }

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));
    if (user.getPremiumStatus() == PremiumStatus.ACTIVE) {
      return;
    }

    if (!usesMultiImage && !usesTogether) {
      return;
    }

    FreeTier freeTier = freeTierRepository.findByUserId(userId)
        .orElseGet(() -> freeTierRepository.save(FreeTier.createDefaultFreeTier(userId)));

    if (usesMultiImage) {
      if (!freeTier.canUseMultiImage()) {
        throw new WaverException(ResultCode.IMAGE_LIMIT_EXCEEDED);
      }
      freeTier.useMultiImage();
    }

    if (usesTogether) {
      if (!freeTier.canUseTogether()) {
        throw new WaverException(ResultCode.TOGETHER_LIMIT_EXCEEDED);
      }
      freeTier.useTogether();
    }
  }

  private int countImages(String imgUrl) {
    if (!StringUtils.hasText(imgUrl)) {
      return 0;
    }
    return imgUrl.split(",").length;
  }

  private void processBadgeCount(long userId, String joinedKeywords) {
    if (!StringUtils.hasText(joinedKeywords)) {
      return;
    }
    List<String> keywords = Arrays.stream(joinedKeywords.split(","))
        .map(String::trim)
        .filter(StringUtils::hasText).toList();

    publisher.publishEvent(new BadgeCountEvent(userId, keywords));
  }

  @Transactional
  public BucketDetailResponse update(long id, long userId, BucketUpdateRequest request) {
    Bucket bucket = bucketRepository.findByIdAndDeleted(id, YesNo.N)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));
    if (!bucket.getUserId().equals(userId)) {
      throw new WaverException(ResultCode.FORBIDDEN);
    }

    // 무료 사용 횟수는 새로 추가되는 경우에만 차감한다 (기존 상태 유지 수정은 무료)
    int imageCount = ObjectUtils.isEmpty(request.images()) ? 0 : request.images().size();
    boolean addsMultiImage = imageCount > 1 && countImages(bucket.getImgUrl()) <= 1;
    boolean addsTogether = StringUtils.hasText(request.friendUserIds())
        && !StringUtils.hasText(bucket.getFriendUserIds());
    checkSaveLimits(userId, imageCount, addsMultiImage, addsTogether);

    // 관리 중인 엔티티에 요청 필드만 반영한다.
    // (새 객체를 merge 하면 요청에 없는 type/likeCount/status 등이 빌더 기본값으로 덮여 초기화된다)
    bucket.setTitle(request.title());
    bucket.setMemo(request.memo());
    bucket.setCategoryId(request.categoryId());
    bucket.setExposureStatus(request.exposureStatus());
    bucket.setTargetDate(request.targetDate());
    bucket.setGoalCount(request.goalCount());
    bucket.setKeywords(request.keywords());
    bucket.setFriendUserIds(request.friendUserIds());
    if (request.bucketType() != null) {
      bucket.setType(request.bucketType());
    }
    if (request.scrapYn() != null) {
      bucket.setScrapYn(request.scrapYn());
    }
    if (!ObjectUtils.isEmpty(request.images())) {
      String imageUrl = request.images().stream().map(fileUploadUtils::uploadFile)
          .collect(Collectors.joining(","));
      bucket.setImgUrl(imageUrl);
    }
    syncBucketMembers(bucket);
    bucketRepository.commit();
    return bucketDetail(id, userId);
  }

  public BucketResponse bucketList(long userId, @Valid BucketRequest request) {
    Long targetUserId = null;
    boolean hasMyBucket = request.hasMyBucket() == YesNo.Y;
    if (hasMyBucket) {
      targetUserId = userId;
    }

    List<Long> reportedBucketIds =
        reportRepository.findBucketlistIdsByReportUserIdAndReportType(userId, ReportType.BUCKET);

    List<Bucket> buckets = findBuckets(targetUserId, request, reportedBucketIds);

    // 함께하기 버킷은 조회자 본인의 참여자 진행도로 표시한다
    List<Long> togetherBucketIds = buckets.stream()
        .filter(bucket -> bucket.getType() == ContentType.TOGETHER)
        .map(Bucket::getId)
        .toList();
    Map<Long, BucketMember> memberByBucketId = togetherBucketIds.isEmpty()
        ? Map.of()
        : bucketMemberRepository.findByUserIdAndBucketIdIn(userId, togetherBucketIds).stream()
            .collect(Collectors.toMap(BucketMember::getBucketId, Function.identity()));

    return BucketResponse.of(buckets, memberByBucketId);
  }

  private List<Bucket> findBuckets(Long targetUserId, BucketRequest request, List<Long> reportedBucketIds) {
    if (reportedBucketIds == null || reportedBucketIds.isEmpty()) {
      return bucketRepository.findBucket(targetUserId, request);
    }

    return bucketRepository.findBucketExcludingIds(targetUserId, request, reportedBucketIds);
  }

  public GetPopularBucketResponse popularBucket(Long userId) {
    //  bucketRepository.findBucket 를 두번 요청
    // popularElements 의 경우 추천순으로 최근 한달 것 조회
    // recommendElements 의 경우 최신순으로 최근 한달 것 조회
    LocalDate now = LocalDate.now();
    LocalDate from = now.minusMonths(1);

    BucketRequest popularRequest = new BucketRequest(
        null,
        null,
        null,
        SortType.LIKE_COUNT_DESC,
        null,
        null,
        YesNo.N,
        from,
        now,
        POPULAR_BUCKET_LIMIT,
        null,
        ExposureStatus.PUBLIC
    );

    BucketRequest recommendRequest = new BucketRequest(
        null,
        null,
        null,
        SortType.CREATED_DESC,
        null,
        null,
        YesNo.N,
        from,
        now,
        POPULAR_BUCKET_LIMIT,
        null,
        ExposureStatus.PUBLIC
    );

    List<Long> reportedBucketIds = null;
    if (userId != null) {
      reportedBucketIds = reportRepository.findBucketlistIdsByReportUserIdAndReportType(userId, ReportType.BUCKET);
    }

    List<Bucket> popularBucketList = findBuckets(null, popularRequest, reportedBucketIds);
    List<Bucket> recommendBucketList = findBuckets(null, recommendRequest, reportedBucketIds);

    return GetPopularBucketResponse.of(popularBucketList, recommendBucketList);
  }

  public BucketDetailResponse bucketDetail(long id, long userId) {
    Bucket bucket = bucketRepository.findByIdAndDeleted(id, YesNo.N)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));
    
    List<KeywordElement> keywords = new ArrayList<>();
    if (StringUtils.hasText(bucket.getKeywords())) {
      String[] selectedKeyword = bucket.getKeywords().split(",");
      Arrays.stream(selectedKeyword)
          .forEach(item -> keywords.add(new KeywordElement(FixedKeyword.get(item))));
    }
    boolean isLike = likeBucketRepository.existsByUserIdAndBucketId(userId, id);

    // 함께하기: 참여자(소유자 포함) 기준으로 조회. friendUsers에는 조회자 본인을 제외한 전원이 담긴다
    Map<Long, BucketMember> memberByUserId = bucket.getType() == ContentType.TOGETHER
        ? bucketMemberRepository.findByBucketId(id).stream()
            .collect(Collectors.toMap(BucketMember::getUserId, Function.identity()))
        : Map.of();

    List<User> friendUserList = new ArrayList<>();
    if (!memberByUserId.isEmpty()) {
      List<Long> otherMemberIds = memberByUserId.keySet().stream()
          .filter(memberUserId -> memberUserId != userId)
          .toList();
      friendUserList = userRepository.findAllById(otherMemberIds);
    } else if (StringUtils.hasText(bucket.getFriendUserIds())) {
      // 참여자 row가 없는 레거시 데이터 fallback
      List<Long> friendIds = Arrays.stream(bucket.getFriendUserIds().split(","))
          .map(String::trim)
          .filter(StringUtils::hasText)
          .map(Long::parseLong)
          .toList();
      friendUserList = userRepository.findAllById(friendIds);
    }

    List<Long> reportedCommentIds =
        reportRepository.findCommentIdsByReportUserIdAndReportType(userId, ReportType.COMMENT);

    return BucketDetailResponse.of(bucket, userId, keywords, friendUserList, isLike, reportedCommentIds,
        memberByUserId);
  }

  public void delete(long id, long userId) {
    bucketRepository.deleteBucket(id, userId);
  }

  @Transactional
  public void achieve(long id, long userId) {
    Bucket bucket = getBucketForCount(id, userId);

    // 함께하기는 호출자 본인의 참여자 row만 증가시킨다 (다른 참여자 진행도에 영향 없음)
    if (isTogether(bucket)) {
      BucketMember member = getOrCreateMember(bucket, userId);
      member.setUserCount(member.getUserCount() + 1);
      boolean completedNow = member.getStatus() != BucketStatus.COMPLETE
          && member.getUserCount() >= bucket.getGoalCount();
      if (completedNow) {
        member.setStatus(BucketStatus.COMPLETE);
        member.setCompletedDate(LocalDateTime.now());
        notifyTogetherComplete(bucket, userId);
      }
      return;
    }

    if (bucket.getGoalCount() == bucket.getUserCount() + 1) {
      bucketRepository.complete(id, bucket.getUserId());
    } else {
      bucketRepository.achieve(id, bucket.getUserId());
    }
  }

  @Transactional
  public void achieveCancel(long id, long userId) {
    Bucket bucket = getBucketForCount(id, userId);

    if (isTogether(bucket)) {
      BucketMember member = getOrCreateMember(bucket, userId);
      member.setUserCount(Math.max(0, member.getUserCount() - 1));
      member.setStatus(BucketStatus.PROGRESS);
      member.setCompletedDate(null);
      return;
    }

    bucketRepository.achieveCancel(id, bucket.getUserId());
  }

  // 달성 횟수는 소유자 또는 함께하기(TOGETHER) 친구만 변경할 수 있다
  private Bucket getBucketForCount(long id, long userId) {
    Bucket bucket = bucketRepository.findByIdAndDeleted(id, YesNo.N)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));

    boolean isOwner = bucket.getUserId() != null && bucket.getUserId() == userId;
    if (!isOwner && !isTogetherFriend(bucket, userId)) {
      throw new WaverException(ResultCode.FORBIDDEN);
    }
    return bucket;
  }

  private boolean isTogetherFriend(Bucket bucket, long userId) {
    if (bucket.getType() != ContentType.TOGETHER || !StringUtils.hasText(bucket.getFriendUserIds())) {
      return false;
    }
    String target = String.valueOf(userId);
    return Arrays.stream(bucket.getFriendUserIds().split(","))
        .map(String::trim)
        .anyMatch(target::equals);
  }

  @Transactional
  public void reset(long id, long userId) {
    Bucket bucket = getBucketForCount(id, userId);

    if (isTogether(bucket)) {
      BucketMember member = getOrCreateMember(bucket, userId);
      member.setUserCount(0);
      member.setStatus(BucketStatus.PROGRESS);
      member.setCompletedDate(null);
      return;
    }

    bucketRepository.reset(id, userId);
  }

  private boolean isTogether(Bucket bucket) {
    return bucket.getType() == ContentType.TOGETHER && StringUtils.hasText(bucket.getFriendUserIds());
  }

  // 마이그레이션 전 레거시 버킷 안전망: 참여자 row가 없으면 만들어서 진행한다
  private BucketMember getOrCreateMember(Bucket bucket, long userId) {
    return bucketMemberRepository.findByBucketIdAndUserId(bucket.getId(), userId)
        .orElseGet(() -> {
          Long categoryId = bucket.getUserId() == userId
              ? bucket.getCategoryId()
              : categoryRepository.findIdByUserIdAndDefaultYn(userId, YesNo.Y);
          return bucketMemberRepository.save(BucketMember.of(bucket.getId(), userId, categoryId));
        });
  }

  // 함께하는 버킷 완성 알림 - 달성자를 제외한 참여자 전원에게 발송
  private void notifyTogetherComplete(Bucket bucket, long completedUserId) {
    bucketMemberRepository.findByBucketId(bucket.getId()).stream()
        .map(BucketMember::getUserId)
        .filter(memberUserId -> memberUserId != completedUserId)
        .forEach(memberUserId -> publisher.publishEvent(
            AlarmMessageEvent.together(memberUserId, completedUserId, bucket.getId(), bucket.getTitle())));
  }

  public void patchGoalCount(long id, long userId, int goalCount) {
    bucketRepository.updateGoalCount(id, userId, goalCount);
  }
}
