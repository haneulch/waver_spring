package com.mybury.waver.web.message.v1.bucket;

import com.mybury.waver.common.code.ContentType;
import com.mybury.waver.common.code.ExposureStatus;
import com.mybury.waver.common.code.YesNo;
import com.mybury.waver.domain.Bucket;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record BucketCreateRequest(
    @NotNull
    @Schema(description = "종류", implementation = ContentType.class)
    ContentType bucketType,

    @NotNull
    @Schema(description = "공개여부", implementation = ExposureStatus.class)
    ExposureStatus exposureStatus,

    @NotBlank
    @Schema(description = "제목")
    String title,

    @Schema(description = "메모")
    String memo,

    @Schema(description = "키워드 목록(최대 5) ','로 구분", example = "beauty,career")
    String keywords,

    @Schema(description = "함께할 친구 ID / ','로 구분 - 함께하기인 경우 최소 1명 필수(최대 5))")
    String friendUserIds,

    @Schema(description = "스크랩 가능 여부", implementation = YesNo.class)
    YesNo scrapYn,

    @Schema(description = "목표완료일(yyyy-MM-dd)")
    LocalDate targetDate,

    @Min(1)
    @NotNull
    @Schema(description = "목표 횟수")
    Integer goalCount,

    @Min(1)
    @NotNull
    @Schema(description = "카테고리 ID")
    Long categoryId,

    @ArraySchema(items = @Schema(description = "이미지 목록(최대 3)", type = "string", format = "binary"))
    List<MultipartFile> images
) {

  public Bucket toBucket(long userId) {
    return Bucket.builder()
        .title(title)
        .memo(memo)
        .userId(userId)
        .categoryId(categoryId)
        .type(bucketType)
        .exposureStatus(exposureStatus)
        .scrapYn(scrapYn == null ? YesNo.N : scrapYn)
        .keywords(keywords)
        .targetDate(targetDate)
        .goalCount(goalCount)
        .build();
  }
}
