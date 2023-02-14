package com.team4.studykit.domain.study.dto;

import com.team4.studykit.domain.member.entity.Member;
import com.team4.studykit.domain.study.entity.Study;
import com.team4.studykit.domain.study.model.Face;
import com.team4.studykit.domain.study.model.Template;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class StudyRequestDto {
    @ApiModelProperty(value = "스터디 제목", example = "3개월 만에 HSK 4급 따기!")
    @NotNull
    private String title;

    @ApiModelProperty(value = "기한", example = "2023-01-02~2023-03-02")
    private String deadline;

    @ApiModelProperty(value = "최대 인원", example = "6")
    @NotNull
    private int max;

    @ApiModelProperty(value = "사용 인원", example = "중국어")
    @NotNull
    private String lang;

    @ApiModelProperty(value = "사용 도구", example = "기타")
    @NotNull
    private String tool;

    @ApiModelProperty(value = "해시태그", example = "#HSK #비대면 #미라클모닝")
    private String hashtags;

    @ApiModelProperty(value = "템플릿 종류", example = "LANG")
    @NotNull
    private Template template;

    @ApiModelProperty(value = "대면 여부", example = "TACT")
    @NotNull
    private Face face;

    @ApiModelProperty(value = "스터디 설명")
    private String description;

    @ApiModelProperty(value = "스터디 QnA")
    private Set<String> qna;

    @ApiModelProperty(hidden = true)
    private Member founder;

    public static Study toStudy(StudyRequestDto studyRequestDto) {
        return Study.builder()
                .title(studyRequestDto.getTitle())
                .deadline(studyRequestDto.getDeadline())
                .max(studyRequestDto.getMax())
                .lang(studyRequestDto.getLang())
                .tool(studyRequestDto.getTool())
                .face(studyRequestDto.getFace())
                .description(studyRequestDto.getDescription())
                .qna(studyRequestDto.getQna())
                .founder(studyRequestDto.getFounder())
                .build();
    }
}
