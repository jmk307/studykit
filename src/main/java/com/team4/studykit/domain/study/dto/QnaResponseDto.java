package com.team4.studykit.domain.study.dto;

import com.team4.studykit.domain.study.entity.Study;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaResponseDto {
    Set<String> qna = new LinkedHashSet<>();

    @Builder
    public QnaResponseDto(Set<String> qna) {
        this.qna = qna;
    }

    public static QnaResponseDto of(Study study) {
        return QnaResponseDto.builder()
                .qna(study.getQna())
                .build();
    }
}
