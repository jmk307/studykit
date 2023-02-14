package com.team4.studykit.domain.study.dto;

import com.team4.studykit.domain.member.dto.member.MemberResponseDto;
import com.team4.studykit.domain.member.entity.Member;
import com.team4.studykit.domain.study.entity.StudyApply;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyApplyResponseDto {
    private Long studyApplyId;

    private Set<String> answers = new LinkedHashSet<>();

    private MemberResponseDto applicant;

    @Builder
    public StudyApplyResponseDto(Long studyApplyId, Set<String> answers, MemberResponseDto applicant) {
        this.studyApplyId = studyApplyId;
        this.answers = answers;
        this.applicant = applicant;
    }

    public static StudyApplyResponseDto of(StudyApply studyApply) {
        return StudyApplyResponseDto.builder()
                .studyApplyId(studyApply.getStudyApplyId())
                .answers(studyApply.getAnswers())
                .applicant(MemberResponseDto.of(studyApply.getApplicant()))
                .build();
    }
}
