package com.team4.studykit.domain.study.repository;

import com.team4.studykit.domain.study.entity.StudyBoardReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyBoardReplyRepository extends JpaRepository<StudyBoardReply, Long> {
    int countByStudyBoard_StudyBoardId(Long studyBoardId);
}
