package com.team4.studykit.domain.study.entity;

import com.team4.studykit.domain.member.entity.Member;
import com.team4.studykit.global.config.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class StudyBoardReply extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyBoardReplyId;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyBoard studyBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    private StudyBoardReply parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<StudyBoardReply> children = new ArrayList<>();

    public void updateParent(StudyBoardReply parent){
        this.parent = parent;
    }
}
