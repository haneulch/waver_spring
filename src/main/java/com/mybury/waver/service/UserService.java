package com.mybury.waver.service;

import com.mybury.waver.common.code.MigrationStatus;
import com.mybury.waver.common.code.PremiumStatus;
import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Badge;
import com.mybury.waver.domain.Category;
import com.mybury.waver.domain.FreeTier;
import com.mybury.waver.domain.MigrationInfo;
import com.mybury.waver.domain.User;
import com.mybury.waver.domain.vo.LoginProjection;
import com.mybury.waver.exception.WaverException;
import com.mybury.waver.repository.BadgeRepository;
import com.mybury.waver.repository.BucketRepository;
import com.mybury.waver.repository.CategoryRepository;
import com.mybury.waver.repository.FollowRepository;
import com.mybury.waver.repository.FreeTierRepository;
import com.mybury.waver.repository.MigrationInfoRepository;
import com.mybury.waver.repository.UserRepository;
import com.mybury.waver.util.FileUploadUtils;
import com.mybury.waver.web.message.v1.user.ProfileResponse;
import com.mybury.waver.web.message.v1.user.UserCreateRequest;
import com.mybury.waver.web.message.v1.user.UserStatusResponse;
import com.mybury.waver.web.message.v1.user.WaverPlusResponse;
import jakarta.transaction.Transactional;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
  private final MigrationInfoRepository migrationInfoRepository;

  @Transactional
  public LoginProjection getUserIdByUid(String uid) {
    LoginProjection login = userRepository.findIdByUid(uid);
    if (login == null) {
      throw new WaverException(ResultCode.NOT_FOUND);
    }
    if (login.getDeleteYn() == YesNo.Y) {
      throw new WaverException(ResultCode.WITHDRAWAL_USER);
    }

    userRepository.updateLastLoginAt(login.getId());

    return login;
  }

  @Transactional
  public User create(UserCreateRequest request, Locale locale) {
    boolean exists = userRepository.existsByEmailOrName(request.email(), request.name());
    if (exists) {
      throw new WaverException(ResultCode.EMAIL_OR_NAME_CANNOT_DUPLICATE);
    }

    User user = request.user(locale);
    // TODO: mybury DB 연동 후 이메일로 기존 mybury 회원 여부 조회해서 myburyYn 세팅
    //  ex) user.setMyburyYn(myburyUserRepository.existsByEmail(request.email()) ? YesNo.Y : YesNo.N);
    //  연동 전까지는 N 고정 (엔티티 기본값)
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
    return newUser;
  }

  // mybury 기존 회원(myburyYn=Y)의 데이터 이관 요청.
  // MigrationInfo에 REQUESTED로 인서트하고, 스케줄러가 이 테이블만 조회해 이관 처리한다.
  @Transactional
  public void requestMigration(long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));

    if (user.getMyburyYn() != YesNo.Y) {
      throw new WaverException(ResultCode.MIGRATION_NOT_ALLOWED);
    }

    migrationInfoRepository.findByUserId(userId).ifPresent(info -> {
      throw new WaverException(info.getStatus() == MigrationStatus.COMPLETED
          ? ResultCode.MIGRATION_ALREADY_COMPLETED
          : ResultCode.MIGRATION_ALREADY_REQUESTED);
    });

    migrationInfoRepository.save(MigrationInfo.request(userId));
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

  @Transactional
  public WaverPlusResponse checkWaverPlusLimit(long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));

    FreeTier freeTier = freeTierRepository.findByUserId(userId)
        .orElseGet(() -> freeTierRepository.save(FreeTier.createDefaultFreeTier(userId)));

    return new WaverPlusResponse(user.getPremiumStatus(),
        freeTier.getImageLimit(), freeTier.getImageUsed(),
        freeTier.getTogetherLimit(), freeTier.getTogetherUsed());
  }

  public User getUserOnlyById(Long userId) {
    return userRepository.findById(userId).orElse(null);
  }

  public String getUserNameById(Long userId) {
    return userRepository.findById(userId).map(User::getName).orElse(null);
  }

  @Transactional
  public void withdraw(long userId) {
    userRepository.withdraw(userId);
    bucketRepository.deleteBucketForWithdraw(userId);
  }

  @Transactional
  public void modify(long userId, MultipartFile profileImg, String name, String bio) {
    User user = userRepository.findById(userId).orElse(null);
    if (user == null) {
      throw new WaverException(ResultCode.NOT_FOUND);
    }

    if (profileImg != null && !profileImg.isEmpty()) {
      String uploadPath = fileUploadUtils.uploadFile(profileImg);
      user.setImgUrl(uploadPath);
    }
    if (StringUtils.hasText(name)) {
      user.setName(name);
    }
    if (bio != null) {
      user.setBio(bio);
    }
  }

  public UserStatusResponse status(String email, String uid) {
    User user = userRepository.findByEmailAndUid(email, uid)
        .orElseThrow(() -> new WaverException(ResultCode.NOT_FOUND));

    return new UserStatusResponse(user.getStatus(), user.getLastLoginAt(), user.getWithdrawnAt());
  }

  @Transactional
  public void updateFcmToken(Long userId, String fcmToken) {
    userRepository.updateFcmToken(userId, fcmToken);
  }
}
