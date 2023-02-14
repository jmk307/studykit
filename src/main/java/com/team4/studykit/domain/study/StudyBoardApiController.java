package com.team4.studykit.domain.study;

import com.team4.studykit.domain.study.dto.StudyBoardRequestDto;
import com.team4.studykit.domain.study.dto.StudyBoardResponseDto;
import com.team4.studykit.global.config.CommonApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/studies/{studyId}/boards")
@Api(tags = "스터디 게시판")
public class StudyBoardApiController {
    private final StudyBoardService studyBoardService;

    @PostMapping
    @ApiOperation(value = "스터디 게시판 생성")
    public ResponseEntity<CommonApiResponse<StudyBoardResponseDto>> makeStudyBoard(
            @ApiIgnore Authentication authentication,
            @PathVariable Long studyId,
            @Valid @RequestPart StudyBoardRequestDto studyBoardRequestDto,
            @RequestPart(required = false) List<MultipartFile> multipartFiles) {
        return ResponseEntity.ok(CommonApiResponse.of(studyBoardService.makeStudyBoard(authentication.getName(), studyId, studyBoardRequestDto, multipartFiles)));
    }

    @GetMapping
    @ApiOperation(value = "스터디 게시판 전체 조회")
    public ResponseEntity<CommonApiResponse<List<StudyBoardResponseDto>>> showStudyBoards(
            @PathVariable Long studyId) {
        return ResponseEntity.ok(CommonApiResponse.of(studyBoardService.showStudyBoards(studyId)));
    }

    @GetMapping("{studyBoardId}")
    @ApiOperation(value = "스터디 게시판 상세 조회")
    public ResponseEntity<CommonApiResponse<StudyBoardResponseDto>> showStudyBoard(
            @ApiIgnore Authentication authentication,
            @PathVariable Long studyId,
            @PathVariable Long studyBoardId) {
        return ResponseEntity.ok(CommonApiResponse.of(studyBoardService.showStudyBoard(authentication.getName(), studyBoardId)));
    }

    @PutMapping("{studyBoardId}")
    @ApiOperation(value = "스터디 게시판 수정(작성자)")
    public ResponseEntity<CommonApiResponse<StudyBoardResponseDto>> modStudyBoard(
            @ApiIgnore Authentication authentication,
            @PathVariable Long studyId,
            @PathVariable Long studyBoardId,
            @Valid @RequestPart StudyBoardRequestDto studyBoardRequestDto,
            @RequestPart(required = false) List<MultipartFile> multipartFiles) {
        return ResponseEntity.ok(CommonApiResponse.of(studyBoardService.modStudyBoard(authentication.getName(), studyBoardId, studyBoardRequestDto, multipartFiles)));
    }

    @DeleteMapping("{studyBoardId}")
    @ApiOperation(value = "스터디 게시판 삭제(작성자)")
    public ResponseEntity<Void> delStudyBoard(
            @PathVariable Long studyId,
            @PathVariable Long studyBoardId) {
        studyBoardService.delStudyBoard(studyBoardId);
        return ResponseEntity.noContent().build();
    }
}