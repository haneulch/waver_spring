package com.mybury.waver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유틸리티")
@RestController
@RequestMapping("waver/utility")
@RequiredArgsConstructor
public class UtilityController {

    @Operation(summary = "코드목록", description = "[REPORT] 신고내용목록")
    @GetMapping("codes/{groupCode}")
    public void utilityCodes(@PathVariable String groupCode) {
    }
}
