package com.team4.studykit.domain.study.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyBoardRequestDto {
    @NotNull
    @ApiModelProperty(value = "스터디 게시글 제목", example = "하루 단어 50개 확인")
    private String title;

    @ApiModelProperty(value = "스터디 게시글 내용", example = "스터디키트스터디키트스터디키트스터디키트")
    private String description;

    @ApiModelProperty(value = "스터디 공지여부", example = "false")
    private boolean notice;
}
