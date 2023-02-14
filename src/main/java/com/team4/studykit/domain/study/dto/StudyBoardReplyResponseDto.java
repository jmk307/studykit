package com.team4.studykit.domain.study.dto;

import com.team4.studykit.domain.member.dto.member.MemberResponseDto;
import com.team4.studykit.domain.study.entity.StudyBoardReply;
import com.team4.studykit.domain.study.model.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyBoardReplyResponseDto {
    private Long id;

    private String content;

    private Long studyBoardReplyId;

    private LocalDateTime createdAt;

    private MemberResponseDto writer;

    private List<StudyBoardReplyResponseDto> children;

    @Builder
    public StudyBoardReplyResponseDto(Long id, String content, Long studyBoardReplyId, LocalDateTime createdAt,
                                      MemberResponseDto writer, List<StudyBoardReplyResponseDto> children) {
        this.id = id;
        this.content = content;
        this.studyBoardReplyId = studyBoardReplyId;
        this.createdAt = createdAt;
        this.writer = writer;
        this.children = children;
    }

    public static StudyBoardReplyResponseDto of(StudyBoardReply studyBoardReply) {
        List<StudyBoardReplyResponseDto> children = new ArrayList<>();
        return StudyBoardReplyResponseDto.builder()
                .id(studyBoardReply.getStudyBoardReplyId())
                .content(studyBoardReply.getContent())
                .createdAt(studyBoardReply.getCreatedAt())
                .writer(MemberResponseDto.of(studyBoardReply.getMember(),
                        studyBoardReply.getStudyBoard().getStudy().getFounder().getMemberId()
                                .equals(studyBoardReply.getMember().getMemberId())
                                ? Role.STUDY_FOUNDER
                                : Role.STUDY_MEMBER))
                .children(children)
                .build();
    }
}
