package com.mybury.waver.service;

import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Badge;
import com.mybury.waver.domain.Category;
import com.mybury.waver.domain.User;
import com.mybury.waver.domain.vo.IdProjection;
import com.mybury.waver.dto.user.ProfileResponse;
import com.mybury.waver.dto.user.UserCreateRequest;
import com.mybury.waver.exception.WaverException;
import com.mybury.waver.repository.BadgeRepository;
import com.mybury.waver.repository.CategoryRepository;
import com.mybury.waver.repository.FollowRepository;
import com.mybury.waver.repository.UserRepository;
import com.mybury.waver.util.FileUploadUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.mybury.waver.domain.Category.createDefaultCategoryFor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final FileUploadUtils fileUploadUtils;
  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;
  private final BadgeRepository badgeRepository;
  private final FollowRepository followRepository;

  public Long getUserIdByEmail(String email) {
    IdProjection id = userRepository.findIdByEmail(email);
    if (id == null || id.getId() == null) {
      throw new WaverException(ResultCode.NOT_FOUND);
    }
    return id.getId();
  }

  @Transactional
  public void create(UserCreateRequest request) {
    User user = request.user();
    if (request.profileImage() != null) {
      String uploadPath = fileUploadUtils.uploadFile(request.profileImage());
      user.setImgUrl(uploadPath);
    }
    User newUser = userRepository.save(user);
    Category defaultCategory = createDefaultCategoryFor(newUser.getId());
    categoryRepository.save(defaultCategory);

    Badge badge = Badge.createDefaultBadgeFor(newUser);
    badgeRepository.save(badge);
  }

  public ProfileResponse getMyProfile(Long userId) {
    Badge badge = getUserById(userId);

    Long followCount = followRepository.countByUserId(userId);
    Long followerCount = followRepository.countByFollowUserId(userId);

    return new ProfileResponse(badge.getUser(), badge, YesNo.N, followCount, followerCount);
  }

  public ProfileResponse getUserProfile(Long userId, Long otherUserId) {
    Badge badge = getUserById(otherUserId);
    boolean isFollowed = followRepository.existsByUserIdAndFollowUserId(userId, otherUserId);

    Long followCount = followRepository.countByUserId(otherUserId);
    Long followerCount = followRepository.countByFollowUserId(otherUserId);

    return new ProfileResponse(badge.getUser(), badge, isFollowed ? YesNo.Y : YesNo.N, followCount, followerCount);
  }

  private Badge getUserById(Long userId) {
    return badgeRepository.findByUserIdAndSelectYn(userId, YesNo.Y).orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));
  }
}
