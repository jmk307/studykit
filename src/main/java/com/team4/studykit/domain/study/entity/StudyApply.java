package com.team4.studykit.domain.study.entity;

import com.team4.studykit.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyApply {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyApplyId;

    @ElementCollection
    private Set<String> answers = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member applicant;

    @Builder
    public StudyApply(Long studyApplyId, Set<String> answers,
                      Study study, Member applicant) {
        this.studyApplyId = studyApplyId;
        this.answers = answers;
        this.study = study;
        this.applicant = applicant;
    }
}
