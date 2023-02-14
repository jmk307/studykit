package com.team4.studykit.domain.study.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaRequestDto {
    Set<String> answers = new LinkedHashSet<>();

    public QnaRequestDto(Set<String> qna) {
        this.answers = qna;
    }
}
