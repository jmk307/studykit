package com.team4.studykit.domain.study.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team4.studykit.domain.study.entity.StudyBoard;
import com.team4.studykit.domain.study.entity.StudyBoardReply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team4.studykit.domain.study.entity.QStudyBoardReply.studyBoardReply;

@Repository
@RequiredArgsConstructor
public class StudyBoardReplyCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    
    // 스터디 게시판 전체 댓글 가져오기
    public List<StudyBoardReply> findAllByStudyBoard(StudyBoard studyBoard) {
        return jpaQueryFactory.selectFrom(studyBoardReply)
                .leftJoin(studyBoardReply.parent)
                .fetchJoin()
                .where(studyBoardReply.studyBoard.studyBoardId.eq(studyBoard.getStudyBoardId()))
                .orderBy(studyBoardReply.parent.studyBoardReplyId.asc().nullsFirst(),
                        studyBoardReply.createdAt.asc())
                .fetch();
    }
}
