package com.team4.studykit.domain.study.entity.relation;

import com.team4.studykit.domain.member.entity.Member;
import com.team4.studykit.domain.study.entity.Study;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberStudy {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberStudyId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Study studyMembers;

    @Builder
    public MemberStudy(Long memberStudyId, Member member, Study studyMembers) {
        this.memberStudyId = memberStudyId;
        this.member = member;
        this.studyMembers = studyMembers;
    }
}
