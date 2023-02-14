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
public class HashtagRequestDto {
    @ApiModelProperty(value = "해시태그", example = "#하하 #호호 #히히")
    @NotNull
    private String hashtagName;
}
