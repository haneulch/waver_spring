package com.mybury.waver.event;

import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Badge;
import com.mybury.waver.event.message.BadgeCountEvent;
import com.mybury.waver.repository.BadgeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 배지용 키워드 이벤트
 */
@Component
@RequiredArgsConstructor
public class BadgeCountEventListener {

    private final BadgeRepository badgeRepository;

    @Transactional
    @EventListener
    public void handle(BadgeCountEvent event) {
        long userId = event.userId();
        List<String> keywords = event.keywords();

        List<Badge> badges = badgeRepository.findByUserIdAndBadgeType_CodeIn(userId, keywords);

        for (Badge badge : badges) {
            badge.incrementAchieveCount();
            if (badge.getAchieveCount() >= 30) {
                badge.setAchieveYn(YesNo.Y);
            }
        }
    }
}