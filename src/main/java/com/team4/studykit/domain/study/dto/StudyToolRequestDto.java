package com.team4.studykit.domain.study.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyToolRequestDto {
    @NotNull
    private String tool;
}
