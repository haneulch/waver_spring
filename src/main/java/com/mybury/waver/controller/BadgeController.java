package com.mybury.waver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "배지")
@RestController
@RequestMapping("waver/badge")
@RequiredArgsConstructor
public class BadgeController {

    @Operation(summary = "배지 목록 조회")
    @GetMapping
    public void getBadge() {
    }
}
