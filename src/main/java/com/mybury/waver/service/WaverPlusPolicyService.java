package com.mybury.waver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mybury.waver.common.code.PremiumStatus;
import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.domain.Bucket;
import com.mybury.waver.domain.User;
import com.mybury.waver.exception.WaverException;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WaverPlusPolicyService {

    private final BucketRepository bucketRepository;
    private final UserRepository userRepository;

    /** 처리 가능한 이미지 정책 */
    public int getBuckitImageCount(long userId){
       User user = userRepository.findById(userId).orElseThrow(()-> new WaverException(ResultCode.TOKEN_EXPIRED));
       boolean isWaverPlus = user.getPremiumStatus() == PremiumStatus.ACTIVE;

       // 웨이버 플러스인 경우 이미지는 최대 3개
       if (isWaverPlus){
        return 3;
       }

       // 웨이버 플러스 미사용 시 처음 한번은 이미지를 최대 3개 까지 업로드
      List<Bucket> userImageBuckits = bucketRepository.findByUserIdAndImgUrlIsNotNull(userId);
      boolean hasPreview = userImageBuckits.stream().anyMatch((buckit)-> buckit.getImgUrl().split(",").length >= 2);
      if (!hasPreview){
        return 3;
      }

      // 웨이버 플러스 미사용 + 처음 한번 업로드 보너스까지 사용한 경우 1개만 업로드
      return 1;
    }
}
