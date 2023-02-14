package com.team4.studykit.domain.study.entity;

import com.team4.studykit.domain.study.entity.relation.StudyHashtag;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Hashtag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashtagId;

    private String hashtagName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hashtag", cascade = CascadeType.ALL)
    private List<StudyHashtag> studyList = new ArrayList<>();

    public void putStudyHashtag(StudyHashtag studyHashtag) {
        this.getStudyList().add(studyHashtag);
    }

    @Builder
    public Hashtag(Long hashtagId, String hashtagName, List<StudyHashtag> studyList) {
        this.hashtagId = hashtagId;
        this.hashtagName = hashtagName;
        this.studyList = studyList;
    }
}
