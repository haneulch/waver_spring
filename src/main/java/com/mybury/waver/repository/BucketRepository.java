package com.mybury.waver.repository;

import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.vo.BucketGoalCount;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BucketRepository extends JpaRepository<Bucket, Long>, BucketRepositoryCustom {

  List<Bucket> findByUserId(Long userId);

  @Modifying
  @Transactional
  @Query(value = "UPDATE Bucket SET deleted = 'Y' WHERE id = :id AND deleted = 'N'")
  void markDeletedById(long id);

  @Modifying
  @Transactional
  @Query(value = "UPDATE Bucket SET likeCount = likeCount + :count WHERE id = :id")
  void updateLike(long id, int count);

  @Query("SELECT bucket FROM Bucket bucket LEFT JOIN FETCH bucket.category WHERE bucket.id = :id AND bucket.deleted = :deleted")
  Bucket findByIdAndDeleted(Long id, YesNo deleted);

  Bucket findByIdAndDeletedAndScrapYn(Long id, YesNo deleted, YesNo scrapYn);

  @Modifying
  @Transactional
  @Query(value = "UPDATE Bucket SET deleted = 'Y' WHERE id = :id AND userId = :userId")
  void deleteBucket(long id, long userId);

  @Modifying
  @Transactional
  @Query(value = "UPDATE Bucket SET userCount = userCount + 1 WHERE id = :id AND userId = :userId")
  void achieve(long id, long userId);

  @Modifying
  @Transactional
  @Query(value = "UPDATE Bucket SET userCount = userCount - 1, status = 'PROGRESS', completedDate = null WHERE id = :id AND userId = :userId")
  void achieveCancel(long id, long userId);

  @Modifying
  @Transactional
  @Query(value = "UPDATE Bucket SET userCount = 0, status = 'PROGRESS', completedDate = null WHERE id = :id AND userId = :userId")
  void reset(long id, long userId);

  @Modifying
  @Transactional
  @Query(value = "UPDATE Bucket SET userCount = goalCount, status = 'COMPLETE', completedDate = NOW() WHERE id = :id AND userId = :userId")
  void complete(long id, long userId);

  @Modifying
  @Transactional
  @Query(value = "UPDATE Bucket SET goalCount = :goalCount WHERE id = :id AND userId = :userId")
  void updateGoalCount(long id, long userId, int goalCount);

  Optional<BucketGoalCount> findByIdAndUserId(long id, long userId);

  int countByUserIdAndDeleted(long userId, YesNo deleted);

  @Modifying
  @Transactional
  @Query(value = "UPDATE Bucket SET deleted = 'Y' WHERE userId = :userId")
  void deleteBucketForWithdraw(long userId);
}
