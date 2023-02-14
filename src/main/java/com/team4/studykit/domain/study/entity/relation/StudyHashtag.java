package com.team4.studykit.domain.study.entity.relation;

import com.team4.studykit.domain.study.entity.Hashtag;
import com.team4.studykit.domain.study.entity.Study;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class StudyHashtag {
    @Id @GeneratedValue
    private Long studyHashtagId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Study study;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hashtag hashtag;

    @Builder
    public StudyHashtag(Study study, Hashtag hashtag) {
        this.study = study;
        this.hashtag = hashtag;
    }
}
