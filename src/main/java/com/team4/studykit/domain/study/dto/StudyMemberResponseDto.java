package com.team4.studykit.domain.study.dto;

import com.team4.studykit.domain.study.entity.Study;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyMemberResponseDto {
    private String founder;

    private List<String> studyMembers;

    private boolean isEqual;

    @Builder
    public StudyMemberResponseDto(String founder, List<String> studyMembers, boolean isEqual) {
        this.founder = founder;
        this.studyMembers = studyMembers;
        this.isEqual = isEqual;
    }

    public static StudyMemberResponseDto of(Study study, boolean isEqual) {
        return StudyMemberResponseDto.builder()
                .founder(study.getFounder().getNickname())
                .studyMembers(study.getMemberStudies().stream()
                        .map(studyMember -> studyMember.getMember().getNickname())
                        .collect(Collectors.toList()))
                .isEqual(isEqual)
                .build();
    }
}
