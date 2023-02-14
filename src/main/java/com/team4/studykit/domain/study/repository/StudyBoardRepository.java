package com.team4.studykit.domain.study.repository;

import com.team4.studykit.domain.study.entity.StudyBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyBoardRepository extends JpaRepository<StudyBoard, Long> {
    List<StudyBoard> findAllByStudy_StudyId(Long studyId);
}
