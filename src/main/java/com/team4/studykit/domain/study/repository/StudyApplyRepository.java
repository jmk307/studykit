package com.team4.studykit.domain.study.repository;

import com.team4.studykit.domain.study.entity.StudyApply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyApplyRepository extends JpaRepository<StudyApply, Long> {
    List<StudyApply> findAllByStudy_StudyId(Long studyId);

    void deleteByStudy_StudyIdAndApplicant_MemberId(Long studyId, Long memberId);
}
