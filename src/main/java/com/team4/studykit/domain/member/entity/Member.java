package com.team4.studykit.domain.member.entity;

import com.team4.studykit.domain.member.model.Social;
import com.team4.studykit.domain.study.entity.StudyApply;
import com.team4.studykit.domain.study.entity.relation.MemberStudy;
import com.team4.studykit.domain.study.entity.Study;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    // 아이디
    private String id;

    // 패스워드
    private String password;

    // 닉네임
    private String nickname;

    // 약관동의여부
    private Boolean joinAccepted;

    // 소셜로그인여부
    @Enumerated(EnumType.STRING)
    private Social social;

    // 스터디 개설자
    @OneToMany(mappedBy = "founder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Study> founders = new ArrayList<>();

    // 스터디 참여자(다대다)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MemberStudy> memberStudies = new ArrayList<>();

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudyApply> studyApplies = new ArrayList<>();
    
    @Builder
    public Member(Long memberId, String id, String password, String nickname,
                  Boolean joinAccepted, Social social, List<Study> founders,
                  List<MemberStudy> memberStudies) {
        this.memberId = memberId;
        this.id = id;
        this.password = password;
        this.nickname = nickname;
        this.joinAccepted = joinAccepted;
        this.social = social;
        this.founders = founders;
        this.memberStudies = memberStudies;
    }
}
