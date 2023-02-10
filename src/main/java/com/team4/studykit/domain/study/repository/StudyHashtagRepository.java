package com.team4.studykit.domain.study.repository;

import com.team4.studykit.domain.study.entity.relation.StudyHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyHashtagRepository extends JpaRepository<StudyHashtag, Long> {
    boolean existsByStudy_StudyId(Long studyId);

    List<StudyHashtag> findAllByStudy_StudyId(Long studyId);
}
