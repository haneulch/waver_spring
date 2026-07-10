package com.mybury.waver.repository;

import com.mybury.waver.common.code.ContentType;
import com.mybury.waver.common.code.ExposureStatus;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.Follow;
import com.mybury.waver.web.message.v1.bucket.BucketRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BucketRepositoryImpl implements BucketRepositoryCustom {

  private static final int FEED_PAGE_SIZE = 21;

  @PersistenceContext
  private EntityManager em;

  @Override
  public List<Bucket> findBucket(Long userId, BucketRequest request) {
    return findBucketInternal(userId, request, null);
  }

  @Override
  public List<Bucket> findBucketExcludingIds(Long userId, BucketRequest request, List<Long> excludedBucketIds) {
    return findBucketInternal(userId, request, excludedBucketIds);
  }

  private List<Bucket> findBucketInternal(Long userId, BucketRequest request, List<Long> excludedBucketIds) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Bucket> query = cb.createQuery(Bucket.class);
    Root<Bucket> root = query.from(Bucket.class);

    List<Predicate> predicates = new ArrayList<>();
    predicates.add(cb.equal(root.get("deleted"), YesNo.N));

    if (userId != null) {
      // 내가 만든 버킷 + 내가 함께하기 친구로 등록된 TOGETHER 버킷
      Predicate isOwner = cb.equal(root.get("userId"), userId);
      Predicate isTogetherFriend = cb.and(
          cb.equal(root.get("type"), ContentType.TOGETHER),
          friendUserIdsContains(cb, root, userId));
      predicates.add(cb.or(isOwner, isTogetherFriend));
    }

    if (excludedBucketIds != null && !excludedBucketIds.isEmpty()) {
      predicates.add(cb.not(root.get("id").in(excludedBucketIds)));
    }

    if (request.dDayBucketOnly() == YesNo.Y) {
      Path<LocalDate> targetDate = root.get("targetDate");
      predicates.add(cb.isNotNull(targetDate));
    }

    if (request.isPassed() == YesNo.Y) {
      Path<LocalDate> targetDate = root.get("targetDate");
      predicates.add(cb.isNotNull(targetDate));
      predicates.add(cb.lessThan(targetDate, LocalDate.now()));
    }

    if (request.status() != null) {
      predicates.add(cb.equal(root.get("status"), request.status()));
    }

    if (StringUtils.hasText(request.query())) {
      String likePattern = "%" + request.query() + "%";
      predicates.add(cb.like(root.get("title"), likePattern));
    }

    if (request.categoryId() != null) {
      predicates.add(cb.equal(root.get("categoryId"), request.categoryId()));
    }

    if (request.hasImage() != null && request.hasImage() == YesNo.Y) {
      predicates.add(cb.isNotNull(root.get("imgUrl")));
    }

    if (request.createdFrom() != null) {
      LocalDateTime from = request.createdFrom().atStartOfDay();
      predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), from));
    }

    if (request.createdTo() != null) {
      LocalDateTime to = request.createdTo().plusDays(1).atStartOfDay();
      predicates.add(cb.lessThan(root.get("createdAt"), to));
    }

    query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

    List<Order> orders = new ArrayList<>();
    if (request.sort() == null) {
      orders.add(cb.desc(root.get("updatedAt")));
    } else {
      switch (request.sort()) {
        case CREATED -> orders.add(cb.asc(root.get("createdAt")));
        case UPDATED -> orders.add(cb.asc(root.get("updatedAt")));
        case CREATED_DESC -> orders.add(cb.desc(root.get("createdAt")));
        case UPDATED_DESC -> orders.add(cb.desc(root.get("updatedAt")));
        case LIKE_COUNT_DESC -> orders.add(cb.desc(root.get("likeCount")));
      }
    }

    query.orderBy(orders);
    return em.createQuery(query).setMaxResults(request.limit()).getResultList();
  }

  // friendUserIds는 ','로 구분된 사용자 ID 문자열 - 단독/맨앞/맨뒤/중간 위치를 모두 매칭한다
  private Predicate friendUserIdsContains(CriteriaBuilder cb, Root<Bucket> root, Long userId) {
    Path<String> friendUserIds = root.get("friendUserIds");
    String id = String.valueOf(userId);
    return cb.or(
        cb.equal(friendUserIds, id),
        cb.like(friendUserIds, id + ",%"),
        cb.like(friendUserIds, "%," + id),
        cb.like(friendUserIds, "%," + id + ",%"));
  }

  @Override
  public List<Bucket> findFeed(List<String> keywords, Long myUserId, Long nextKey, List<Long> excludedBucketIds) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Bucket> query = cb.createQuery(Bucket.class);
    Root<Bucket> root = query.from(Bucket.class);

    List<Predicate> predicates = new ArrayList<>();
    predicates.add(cb.notEqual(root.get("userId"), myUserId));
    predicates.add(cb.equal(root.get("deleted"), YesNo.N));

    if (excludedBucketIds != null && !excludedBucketIds.isEmpty()) {
      predicates.add(cb.not(root.get("id").in(excludedBucketIds)));
    }

    if (nextKey != null) {
      predicates.add(cb.lessThanOrEqualTo(root.get("id"), nextKey));
    }

    // 맞팔인 사용자의 FOLLOWER 공개 버킷도 포함
    // mutual follow: I follow them AND they follow me
    Subquery<Long> iFollowThem = query.subquery(Long.class);
    Root<Follow> iFollowThemRoot = iFollowThem.from(Follow.class);
    iFollowThem.select(iFollowThemRoot.get("followUserId"))
        .where(cb.equal(iFollowThemRoot.get("userId"), myUserId));

    Subquery<Long> theyFollowMe = query.subquery(Long.class);
    Root<Follow> theyFollowMeRoot = theyFollowMe.from(Follow.class);
    theyFollowMe.select(theyFollowMeRoot.get("userId"))
        .where(cb.equal(theyFollowMeRoot.get("followUserId"), myUserId));

    Predicate isPublic = cb.equal(root.get("exposureStatus"), ExposureStatus.PUBLIC);
    Predicate isMutualFollowerBucket = cb.and(
        cb.equal(root.get("exposureStatus"), ExposureStatus.FOLLOWER),
        root.get("userId").in(iFollowThem),
        root.get("userId").in(theyFollowMe)
    );
    predicates.add(cb.or(isPublic, isMutualFollowerBucket));

    query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

    List<Order> orders = new ArrayList<>();
    orders.add(cb.desc(root.get("id")));
    query.orderBy(orders);

    return em.createQuery(query).setMaxResults(FEED_PAGE_SIZE).getResultList();
  }

  @Override
  public List<Bucket> search(String text, List<Long> excludedBucketIds) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Bucket> query = cb.createQuery(Bucket.class);
    Root<Bucket> root = query.from(Bucket.class);

    List<Predicate> predicates = new ArrayList<>();

    String likePattern = "%" + text + "%";
    predicates.add(cb.like(root.get("title"), likePattern));
    predicates.add(cb.equal(root.get("deleted"), YesNo.N));
    predicates.add(cb.equal(root.get("exposureStatus"), ExposureStatus.PUBLIC));

    if (excludedBucketIds != null && !excludedBucketIds.isEmpty()) {
      predicates.add(cb.not(root.get("id").in(excludedBucketIds)));
    }

    query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

    List<Order> orders = new ArrayList<>();
    orders.add(cb.desc(root.get("createdAt")));
    query.orderBy(orders);

    return em.createQuery(query).getResultList();
  }

  @Override
  public void commit() {
    em.flush();
    em.clear();
  }
}
