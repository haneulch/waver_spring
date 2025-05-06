package com.mybury.waver.repository;

import com.mybury.waver.common.code.ExposureStatus;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.dto.bucket.BucketRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BucketRepositoryImpl implements BucketRepositoryCustom {

  @PersistenceContext
  private EntityManager em;

  @Override
  public List<Bucket> findBucket(long userId, BucketRequest request) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Bucket> query = cb.createQuery(Bucket.class);
    Root<Bucket> root = query.from(Bucket.class);

    List<Predicate> predicates = new ArrayList<>();
    predicates.add(cb.equal(root.get("userId"), userId));
    predicates.add(cb.equal(root.get("deleted"), YesNo.N));

    if (request.dDayBucketOnly() != null) {
      Path<LocalDate> targetDate = root.get("targetDate");
      predicates.add(cb.isNotNull(targetDate));
    }

    if (request.isPassed() != null) {
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
      }
    }

    query.orderBy(orders);
    return em.createQuery(query).getResultList();
  }

  @Override
  public List<Bucket> findFeed() {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Bucket> query = cb.createQuery(Bucket.class);
    Root<Bucket> root = query.from(Bucket.class);

    List<Predicate> predicates = new ArrayList<>();
    predicates.add(cb.equal(root.get("deleted"), YesNo.N));
    predicates.add(cb.equal(root.get("exposureStatus"), ExposureStatus.PUBLIC));

    query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

    List<Order> orders = new ArrayList<>();
    orders.add(cb.desc(root.get("createdAt")));
    query.orderBy(orders);

    return em.createQuery(query).getResultList();
  }

  @Override
  public List<Bucket> search(String text) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Bucket> query = cb.createQuery(Bucket.class);
    Root<Bucket> root = query.from(Bucket.class);

    List<Predicate> predicates = new ArrayList<>();


    String likePattern = "%" + text + "%";
    predicates.add(cb.like(root.get("title"), likePattern));
    predicates.add(cb.equal(root.get("deleted"), YesNo.N));
    predicates.add(cb.equal(root.get("exposureStatus"), ExposureStatus.PUBLIC));

    query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));

    List<Order> orders = new ArrayList<>();
    orders.add(cb.desc(root.get("createdAt")));
    query.orderBy(orders);

    return em.createQuery(query).getResultList();
  }
}
