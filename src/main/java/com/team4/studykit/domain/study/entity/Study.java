package com.team4.studykit.domain.study.entity;

import com.team4.studykit.domain.member.entity.Member;
import com.team4.studykit.domain.study.entity.relation.MemberStudy;
import com.team4.studykit.domain.study.entity.relation.StudyHashtag;
import com.team4.studykit.domain.study.model.Face;
import com.team4.studykit.domain.study.model.Template;
import com.team4.studykit.global.config.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Study extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyId;

    // 스터디 제목
    private String title;

    // 스터디 설명
    private String description;

    // 스터디 기한
    private String deadline;

    // 스터디 대표 이미지
    @ElementCollection
    private List<String> studyImageUrl;

    // 스터디 최대 인원
    private int max;

    // 스터디 사용 언어
    private String lang;

    // 스터디 사용 툴
    private String tool;

    // 스터디 템플릿(언어, 프로그래밍, 등....)
    @Enumerated(EnumType.STRING)
    private Template template;

    // 스터디 대면 여부
    @Enumerated(EnumType.STRING)
    private Face face;

    // 스터디 qna
    @ElementCollection
    Set<String> qna = new LinkedHashSet<>();

    // 현재 모집여부
    private boolean recruiting;

    // 스터디장
    @ManyToOne(fetch = FetchType.LAZY)
    private Member founder;

    // 해당 스터디 해시태그
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "study", cascade = CascadeType.ALL)
    private List<StudyHashtag> hashtags = new ArrayList<>();

    // 스터디원
    @OneToMany(mappedBy = "studyMembers", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MemberStudy> memberStudies = new ArrayList<>();

    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudyApply> studyApplies = new ArrayList<>();

    public void putStudyHashtag(StudyHashtag studyHashtag) {
        this.hashtags.add(studyHashtag);
    }

    public void putRecruiting(boolean recruiting) {
        this.recruiting = recruiting;
    }

    @Builder
    public Study(Long studyId, String title, String description, String deadline,
                 List<String> studyImageUrl, int max, String lang, String tool,
                 Template template, Face face, Set<String> qna, boolean recruiting,
                 Member founder, List<StudyHashtag> hashtags, List<MemberStudy> memberStudies) {
        this.studyId = studyId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.studyImageUrl = studyImageUrl;
        this.max = max;
        this.lang = lang;
        this.tool = tool;
        this.template = template;
        this.face = face;
        this.qna = qna;
        this.recruiting = recruiting;
        this.founder = founder;
        this.hashtags = hashtags;
        this.memberStudies = memberStudies;
    }
}
