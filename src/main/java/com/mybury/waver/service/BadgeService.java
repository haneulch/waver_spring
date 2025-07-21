package com.mybury.waver.service;

import com.mybury.waver.domain.Badge;
import com.mybury.waver.domain.BadgeType;
import com.mybury.waver.repository.BadgeRepository;
import com.mybury.waver.repository.BadgeTypeRepository;
import com.mybury.waver.web.message.v1.badge.BadgeResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final BadgeTypeRepository badgeTypeRepository;

    public List<BadgeResponse> getBadge(Long userId) {
        List<BadgeType> badgeTypes = badgeTypeRepository.findAll();
        List<Badge> badges = badgeRepository.findByUserId(userId);
        return BadgeResponse.of(badgeTypes, badges);
    }
}
