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

@Tag(name = "유틸리티")
@RestController
@RequestMapping("waver/utility")
@RequiredArgsConstructor
public class UtilityController {

    private final UtilityService utilityService;

    @Operation(summary = "코드목록", description = "[REPORT] 신고내용목록")
    @GetMapping("codes/{groupCode}")
    public List<CodeResponse> utilityCodes(@PathVariable String groupCode) {
        CodeGroup codeGroup = CodeGroup.get(groupCode);
        if (codeGroup == null) {
            throw new WaverException(ResultCode.BAD_REQUEST);
        }
        return utilityService.getCodes(codeGroup);
    }
}
