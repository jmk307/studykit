package com.team4.studykit.domain.study.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyBoardReplyRequestDto {
    @ApiModelProperty(value = "부모 댓글 id")
    private Long studyBoardReplyId;

    @ApiModelProperty(value = "댓글내용", example = "스터디키트짱짱")
    private String content;
}
