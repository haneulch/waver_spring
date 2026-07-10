package com.mybury.waver.event;

import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Badge;
import com.mybury.waver.domain.BadgeType;
import com.mybury.waver.event.message.BadgeCountEvent;
import com.mybury.waver.repository.BadgeRepository;
import com.mybury.waver.repository.BadgeTypeRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 배지용 키워드 이벤트
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class BadgeCountEventListener {

  private static final int ACHIEVE_COUNT_THRESHOLD = 30;

  private final BadgeRepository badgeRepository;
  private final BadgeTypeRepository badgeTypeRepository;

  @Transactional
  @EventListener
  public void handle(BadgeCountEvent event) {

    long userId = event.userId();
    List<String> keywords = event.keywords();

    if (CollectionUtils.isEmpty(keywords)) {
      return;
    }

    List<String> codes = keywords.stream().distinct().toList();

    List<Badge> badges = badgeRepository.findByUserIdAndBadgeType_CodeIn(userId, codes);
    List<BadgeType> badgeTypes = badgeTypeRepository.findByCodeIn(codes);

    Map<Long, Badge> badgeByTypeId =
        badges.stream().collect(Collectors.toMap(Badge::getBadgeTypeId, Function.identity()));

    List<Badge> toSave = new ArrayList<>();
    for (BadgeType badgeType : badgeTypes) {

      long typeId = badgeType.getId();
      Badge badge = badgeByTypeId.get(typeId);

      if (badge != null) {
        if (badge.getAchieveYn() == YesNo.Y) {
          continue;
        }

        badge.incrementAchieveCount();

        if (badge.getAchieveCount() >= ACHIEVE_COUNT_THRESHOLD) {
          badge.setAchieveYn(YesNo.Y);
        }
      } else {
        Badge newBadge = Badge.builder()
            .userId(userId)
            .badgeTypeId(typeId)
            .achieveCount(1)
            .achieveYn(YesNo.N)
            .build();

        toSave.add(newBadge);
        badgeByTypeId.put(typeId, newBadge);
      }
    }

    if (!toSave.isEmpty()) {
      badgeRepository.saveAll(toSave);
    }
  }
}