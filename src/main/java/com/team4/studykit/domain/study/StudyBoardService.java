package com.team4.studykit.domain.study;

import com.team4.studykit.domain.member.entity.Member;
import com.team4.studykit.domain.member.repository.MemberRepository;
import com.team4.studykit.domain.study.dto.StudyBoardRequestDto;
import com.team4.studykit.domain.study.dto.StudyBoardResponseDto;
import com.team4.studykit.domain.study.entity.Study;
import com.team4.studykit.domain.study.entity.StudyBoard;
import com.team4.studykit.domain.study.model.Role;
import com.team4.studykit.domain.study.repository.StudyBoardRepository;
import com.team4.studykit.domain.study.repository.StudyRepository;
import com.team4.studykit.global.config.s3.AwsS3ServiceImpl;
import com.team4.studykit.global.error.ErrorCode;
import com.team4.studykit.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudyBoardService {
    private final StudyBoardRepository studyBoardRepository;
    private final MemberRepository memberRepository;
    private final StudyRepository studyRepository;
    private final AwsS3ServiceImpl awsS3Service;

    // 스터디 게시판 작성
    @Transactional
    public StudyBoardResponseDto makeStudyBoard(String id, Long studyId, StudyBoardRequestDto studyBoardRequestDto,
                                                List<MultipartFile> multipartFiles) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.STUDY_NOT_FOUND));

        List<String> studyBoardImageUrls = (multipartFiles == null)
                ? new ArrayList<>()
                : awsS3Service.uploadImage(multipartFiles, "studyBoard");

        StudyBoard studyBoard = StudyBoard.builder()
                .title(studyBoardRequestDto.getTitle())
                .description(studyBoardRequestDto.getDescription())
                .studyBoardImageUrls(studyBoardImageUrls)
                .notice(studyBoardRequestDto.isNotice())
                .study(study)
                .writer(member)
                .build();
        studyBoardRepository.save(studyBoard);

        return StudyBoardResponseDto.of(studyBoard);
    }
    
    // 스터디 게시판 전체 조회
    @Transactional(readOnly = true)
    public List<StudyBoardResponseDto> showStudyBoards(Long studyId) {
        List<StudyBoard> studyBoards = studyBoardRepository.findAllByStudy_StudyId(studyId);

        return studyBoards.stream()
                .map(StudyBoardResponseDto::of)
                .collect(Collectors.toList());
    }

    // 스터디 게시판 상세 조회
    @Transactional(readOnly = true)
    public StudyBoardResponseDto showStudyBoard(String id, Long studyBoardId) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        StudyBoard studyBoard = studyBoardRepository.findById(studyBoardId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.STUDY_BOARD_NOT_FOUND));

        Role role = studyBoard.getWriter().getMemberId().equals(member.getMemberId())
                ? Role.STUDY_WRITER
                : Role.STUDY_MEMBER;

        return StudyBoardResponseDto.of(studyBoard, role);
    }

    // 스터디 게시판 수정(작성자)
    @Transactional(readOnly = true)
    public StudyBoardResponseDto modStudyBoard(String id, Long studyBoardId, StudyBoardRequestDto studyBoardRequestDto,
                                               List<MultipartFile> multipartFiles) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        StudyBoard studyBoard = studyBoardRepository.findById(studyBoardId)
                .orElseThrow(() -> new BadRequestException(ErrorCode.STUDY_BOARD_NOT_FOUND));

        List<String> studyBoardImageUrls = (multipartFiles == null)
                ? new ArrayList<>()
                : awsS3Service.uploadImage(multipartFiles, "studyBoard");

        studyBoard.putStudyBoard(
                studyBoardRequestDto.getTitle(),
                studyBoardRequestDto.getDescription(),
                studyBoardImageUrls,
                studyBoardRequestDto.isNotice()
        );
        studyBoardRepository.save(studyBoard);

        Role role = studyBoard.getWriter().getMemberId().equals(member.getMemberId())
                ? Role.STUDY_WRITER
                : Role.STUDY_MEMBER;

        return StudyBoardResponseDto.of(studyBoard, role);
    }

    // 스터디 게시판 삭제(작성자)
    public void delStudyBoard(Long studyBoardId) {
        studyBoardRepository.deleteById(studyBoardId);
    }
}
