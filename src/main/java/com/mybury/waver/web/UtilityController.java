package com.mybury.waver.web;

import com.mybury.waver.common.code.CodeGroup;
import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.exception.WaverException;
import com.mybury.waver.service.UtilityService;
import com.mybury.waver.web.message.v1.utility.CodeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유틸리티", description = "공통 코드 조회")
@RestController
@RequestMapping("waver/utility")
@RequiredArgsConstructor
public class UtilityController {

    private final UtilityService utilityService;

    @Operation(summary = "공통 코드 목록",
        description = "그룹 코드에 속한 코드/라벨 목록을 반환합니다. 예: REPORT(신고 사유). 없는 그룹이면 4000을 반환합니다.")
    @GetMapping("codes/{groupCode}")
    public List<CodeResponse> utilityCodes(@PathVariable String groupCode) {
        CodeGroup codeGroup = CodeGroup.get(groupCode);
        if (codeGroup == null) {
            throw new WaverException(ResultCode.BAD_REQUEST);
        }
        return utilityService.getCodes(codeGroup);
    }
}
