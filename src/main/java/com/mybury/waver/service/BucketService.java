package com.mybury.waver.service;

import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.KeywordConnector;
import com.mybury.waver.dto.bucket.BucketCreateRequest;
import com.mybury.waver.dto.bucket.BucketRequest;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.KeywordConnectorRepository;
import com.mybury.waver.util.FileUploadUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BucketService {
  private final FileUploadUtils fileUploadUtils;
  private final BucketRepository bucketRepository;
  private final KeywordConnectorRepository keywordConnectorRepository;

  @Transactional
  public void create(Long userId, @Valid BucketCreateRequest request) {
    Bucket bucket = request.toBucket(userId);

    if (!ObjectUtils.isEmpty(request.images())) {
      String imageUrl = request.images().stream().map(fileUploadUtils::uploadFile).collect(Collectors.joining(","));
      bucket.setImgUrl(imageUrl);
    }
    Bucket saved = bucketRepository.save(bucket);

    if (StringUtils.hasText(request.keywordIds())) {
      List<KeywordConnector> keywordConnectors = Arrays.stream(request.keywordIds().split(","))
          .map(id -> KeywordConnector.builder().bucketId(saved.getId()).keywordId(Integer.parseInt(id)).userId(userId).build()).toList();
      keywordConnectorRepository.saveAll(keywordConnectors);
    }
  }

  public List<Bucket> bucketList(Long userId, @Valid BucketRequest request) {
    return bucketRepository.findBucket(userId, request);
  }
}
