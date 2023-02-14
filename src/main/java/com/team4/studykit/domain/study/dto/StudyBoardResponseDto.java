package com.team4.studykit.domain.study.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.team4.studykit.domain.member.dto.member.MemberResponseDto;
import com.team4.studykit.domain.study.entity.StudyBoard;
import com.team4.studykit.domain.study.model.Role;
import lombok.*;

import java.util.List;

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

    private MemberResponseDto writer;

    private Role writtenBy;

    @Builder
    public StudyBoardResponseDto(Long studyBoardId, String title, String descrption,
                                 List<String> studyBoardImageUrls, boolean notice,
                                 MemberResponseDto writer, Role writtenBy) {
        this.studyBoardId = studyBoardId;
        this.title = title;
        this.descrption = descrption;
        this.studyBoardImageUrls = studyBoardImageUrls;
        this.notice = notice;
        this.writer = writer;
        this.writtenBy = writtenBy;
    }

    public static StudyBoardResponseDto of(StudyBoard studyBoard) {
        return StudyBoardResponseDto.builder()
                .studyBoardId(studyBoard.getStudyBoardId())
                .title(studyBoard.getTitle())
                .descrption(studyBoard.getDescription())
                .studyBoardImageUrls(studyBoard.getStudyBoardImageUrls())
                .notice(studyBoard.isNotice())
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
                .writer(MemberResponseDto.of(studyBoard.getWriter(),
                        studyBoard.getStudy().getFounder().getMemberId()
                                .equals(studyBoard.getWriter().getMemberId())
                                ? Role.STUDY_FOUNDER
                                : Role.STUDY_MEMBER))
                .writtenBy(writtenBy)
                .build();
    }
}
