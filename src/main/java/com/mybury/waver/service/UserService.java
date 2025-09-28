package com.mybury.waver.service;

import com.mybury.waver.common.code.PremiumStatus;
import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Badge;
import com.mybury.waver.domain.Category;
import com.mybury.waver.domain.FreeTier;
import com.mybury.waver.domain.User;
import com.mybury.waver.domain.vo.LoginProjection;
import com.mybury.waver.exception.WaverException;
import com.mybury.waver.repository.*;
import com.mybury.waver.util.FileUploadUtils;
import com.mybury.waver.web.message.v1.user.ProfileResponse;
import com.mybury.waver.web.message.v1.user.UserCreateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static com.mybury.waver.domain.Category.createDefaultCategoryFor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final FileUploadUtils fileUploadUtils;
  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;
  private final BadgeRepository badgeRepository;
  private final FollowRepository followRepository;
  private final BucketRepository bucketRepository;
  private final FreeTierRepository freeTierRepository;

  public LoginProjection getUserIdByUid(String uid) {
    LoginProjection login = userRepository.findIdByUidAndDeleteYn(uid, YesNo.N);
    if (login == null || login.getId() == null) {
      throw new WaverException(ResultCode.NOT_FOUND);
    }
    return login;
  }

  @Transactional
  public long create(UserCreateRequest request, Locale locale) {
    boolean exists = userRepository.existsByEmailOrName(request.email(), request.name());
    if (exists) {
      throw new WaverException(ResultCode.EMAIL_OR_NAME_CANNOT_DUPLICATE);
    }

    User user = request.user(locale);
    if (request.profileImage() != null) {
      String uploadPath = fileUploadUtils.uploadFile(request.profileImage());
      user.setImgUrl(uploadPath);
    }
    User newUser = userRepository.save(user);
    Category defaultCategory = createDefaultCategoryFor(newUser.getId());
    categoryRepository.save(defaultCategory);

    Badge badge = Badge.createDefaultBadgeFor(newUser);
    badgeRepository.save(badge);

    FreeTier freeTier = FreeTier.createDefaultFreeTier(user.getId());
    freeTierRepository.save(freeTier);
    return user.getId();
  }

  public ProfileResponse getMyProfile(Long userId) {
    Badge badge = getUserWithBadgeById(userId);

    Long followCount = followRepository.countByUserId(userId);
    Long followerCount = followRepository.countByFollowUserId(userId);

    return new ProfileResponse(badge.getUser(), badge, YesNo.N, followCount, followerCount);
  }

  public ProfileResponse getUserProfile(Long userId, Long otherUserId) {
    Badge badge = getUserWithBadgeById(otherUserId);
    boolean isFollowed = followRepository.existsByUserIdAndFollowUserId(userId, otherUserId);

    Long followCount = followRepository.countByUserId(otherUserId);
    Long followerCount = followRepository.countByFollowUserId(otherUserId);

    return new ProfileResponse(badge.getUser(), badge, isFollowed ? YesNo.Y : YesNo.N, followCount, followerCount);
  }

  private Badge getUserWithBadgeById(Long userId) {
    return badgeRepository.findByUserIdAndSelectYn(userId, YesNo.Y)
      .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));
  }

  public void checkUsernameAvailability(String name) {
    boolean exists = userRepository.existsByName(name);
    if (exists) {
      throw new WaverException(ResultCode.EMAIL_OR_NAME_CANNOT_DUPLICATE);
    }
  }

  public PremiumStatus checkWaverPlusLimit(long userId) {
    return null;
  }

  public User getUserOnlyById(Long userId) {
    return userRepository.findById(userId).orElse(null);
  }

  @Transactional
  public void withdraw(long userId) {
    userRepository.withdraw(userId);
    bucketRepository.deleteBucketForWithdraw(userId);
  }
}
