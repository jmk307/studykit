package com.team4.studykit.domain.study.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.team4.studykit.domain.member.dto.member.MemberResponseDto;
import com.team4.studykit.domain.study.entity.StudyBoard;
import com.team4.studykit.domain.study.model.Role;
import lombok.*;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL) //NULL 필드 가림
public class StudyBoardResponseDto {
    private Long studyBoardId;

    private String title;

    private String descrption;

    private List<String> studyBoardImageUrls;

    private boolean notice;

    private int count;

    private int replyCount;

    private LocalDateTime createdAt;

    private List<StudyBoardReplyResponseDto> studyBoardReplyResponseDtos;

    private MemberResponseDto writer;

    private Role writtenBy;

    @Builder
    public StudyBoardResponseDto(Long studyBoardId, String title, String descrption,
                                 List<String> studyBoardImageUrls, boolean notice,
                                 int count, int replyCount, LocalDateTime createdAt,
                                 List<StudyBoardReplyResponseDto> studyBoardReplyResponseDtos,
                                 MemberResponseDto writer, Role writtenBy) {
        this.studyBoardId = studyBoardId;
        this.title = title;
        this.descrption = descrption;
        this.studyBoardImageUrls = studyBoardImageUrls;
        this.notice = notice;
        this.count = count;
        this.replyCount = replyCount;
        this.createdAt = createdAt;
        this.studyBoardReplyResponseDtos = studyBoardReplyResponseDtos;
        this.writer = writer;
        this.writtenBy = writtenBy;
    }

    public static StudyBoardResponseDto ofAll(StudyBoard studyBoard) {
        return StudyBoardResponseDto.builder()
                .studyBoardId(studyBoard.getStudyBoardId())
                .title(studyBoard.getTitle())
                .descrption(studyBoard.getDescription())
                .studyBoardImageUrls(studyBoard.getStudyBoardImageUrls())
                .notice(studyBoard.isNotice())
                .createdAt(studyBoard.getCreatedAt())
                .studyBoardReplyResponseDtos(null)
                .writer(MemberResponseDto.of(studyBoard.getWriter(),
                        studyBoard.getStudy().getFounder().getMemberId()
                                .equals(studyBoard.getWriter().getMemberId())
                                ? Role.STUDY_FOUNDER
                                : Role.STUDY_MEMBER))
                .build();
    }

    public static StudyBoardResponseDto of(StudyBoard studyBoard) {
        return StudyBoardResponseDto.builder()
                .studyBoardId(studyBoard.getStudyBoardId())
                .title(studyBoard.getTitle())
                .descrption(studyBoard.getDescription())
                .studyBoardImageUrls(studyBoard.getStudyBoardImageUrls())
                .notice(studyBoard.isNotice())
                .createdAt(studyBoard.getCreatedAt())
                .studyBoardReplyResponseDtos(studyBoard.getStudyBoardReplies().stream()
                        .map(StudyBoardReplyResponseDto::of)
                        .collect(Collectors.toList()))
                .writer(MemberResponseDto.of(studyBoard.getWriter(),
                        studyBoard.getStudy().getFounder().getMemberId()
                        .equals(studyBoard.getWriter().getMemberId())
                        ? Role.STUDY_FOUNDER
                        : Role.STUDY_MEMBER))
                .build();
    }

    public static StudyBoardResponseDto of(StudyBoard studyBoard, Role writtenBy) {
        return StudyBoardResponseDto.builder()
                .studyBoardId(studyBoard.getStudyBoardId())
                .title(studyBoard.getTitle())
                .descrption(studyBoard.getDescription())
                .studyBoardImageUrls(studyBoard.getStudyBoardImageUrls())
                .notice(studyBoard.isNotice())
                .createdAt(studyBoard.getCreatedAt())
                .studyBoardReplyResponseDtos(studyBoard.getStudyBoardReplies().stream()
                        .map(StudyBoardReplyResponseDto::of)
                        .collect(Collectors.toList()))
                .writer(MemberResponseDto.of(studyBoard.getWriter(),
                        studyBoard.getStudy().getFounder().getMemberId()
                                .equals(studyBoard.getWriter().getMemberId())
                                ? Role.STUDY_FOUNDER
                                : Role.STUDY_MEMBER))
                .writtenBy(writtenBy)
                .build();
    }
}
