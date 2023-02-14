package com.team4.studykit.domain.study.entity;

import com.team4.studykit.domain.member.entity.Member;
import com.team4.studykit.global.config.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StudyBoard extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyBoardId;

    private String title;

    private String description;

    @ElementCollection
    private List<String> studyBoardImageUrls = new ArrayList<>();

    private boolean notice;

    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    public void putStudyBoard(String title, String description, List<String> studyBoardImageUrls,
                       boolean notice) {
        this.title = title;
        this.description = description;
        this.studyBoardImageUrls = studyBoardImageUrls;
        this.notice = notice;
    }

    @Builder
    public StudyBoard(String title, String description, List<String> studyBoardImageUrls,
                      boolean notice, Study study, Member writer) {
        this.title = title;
        this.description = description;
        this.studyBoardImageUrls = studyBoardImageUrls;
        this.notice = notice;
        this.study = study;
        this.writer = writer;
    }
}
