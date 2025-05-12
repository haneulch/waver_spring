package com.mybury.waver.service;

import com.mybury.waver.common.code.FixedKeyword;
import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.vo.BucketGoalCount;
import com.mybury.waver.dto.bucket.BucketCreateRequest;
import com.mybury.waver.dto.bucket.BucketDetailResponse;
import com.mybury.waver.dto.bucket.BucketRequest;
import com.mybury.waver.dto.bucket.BucketUpdateRequest;
import com.mybury.waver.dto.bucket.KeywordElement;
import com.mybury.waver.exception.WaverException;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.util.FileUploadUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BucketService {
  private final FileUploadUtils fileUploadUtils;
  private final BucketRepository bucketRepository;

  @Transactional
  public BucketDetailResponse create(long userId, BucketCreateRequest request) {
    Bucket bucket = request.toBucket(userId);

    if (!ObjectUtils.isEmpty(request.images())) {
      String imageUrl = request.images().stream().map(fileUploadUtils::uploadFile).collect(Collectors.joining(","));
      bucket.setImgUrl(imageUrl);
    }
    Bucket saved = bucketRepository.save(bucket);
    bucketRepository.commit();
    return bucketDetail(saved.getId(), userId);
  }

  @Transactional
  public BucketDetailResponse update(long id, long userId, BucketUpdateRequest request) {
    Bucket bucket = request.toBucket(userId);
    bucket.setId(id);

    if (!ObjectUtils.isEmpty(request.images())) {
      String imageUrl = request.images().stream().map(fileUploadUtils::uploadFile).collect(Collectors.joining(","));
      bucket.setImgUrl(imageUrl);
    }
    bucketRepository.save(bucket);
    bucketRepository.commit();
    return bucketDetail(id, userId);
  }

  public List<Bucket> bucketList(long userId, @Valid BucketRequest request) {
    return bucketRepository.findBucket(userId, request);
  }

  public BucketDetailResponse bucketDetail(long id, long userId) {
    Bucket bucket = bucketRepository.findByIdAndDeleted(id, YesNo.N);
    List<KeywordElement> keywords = new ArrayList<>();
    if (bucket.getKeywords() != null) {
      String[] selectedKeyword = bucket.getKeywords().split(",");
      Arrays.stream(selectedKeyword)
          .forEach(item -> keywords.add(new KeywordElement(FixedKeyword.get(item))));
    }
    // TODO: 함께하기 친구
    return BucketDetailResponse.of(bucket, userId, keywords, List.of());
  }

  public void delete(long id, long userId) {
    bucketRepository.deleteBucket(id, userId);
  }

  public void achieve(long id, long userId) {
    BucketGoalCount goalCount = bucketRepository.findByIdAndUserId(id, userId)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));

    if (goalCount.getGoalCount() == goalCount.getUserCount() + 1) {
      bucketRepository.complete(id, userId);
    } else {
      bucketRepository.achieve(id, userId);
    }
  }

  public void achieveCancel(long id, long userId) {
    bucketRepository.achieveCancel(id, userId);
  }

  public void reset(long id, long userId) {
    bucketRepository.reset(id, userId);
  }

  public void patchGoalCount(long id, long userId, int goalCount) {
    bucketRepository.updateGoalCount(id, userId, goalCount);
  }
}
