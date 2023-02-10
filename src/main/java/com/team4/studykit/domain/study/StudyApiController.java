package com.team4.studykit.domain.study;

import com.team4.studykit.domain.study.dto.*;
import com.team4.studykit.global.config.CommonApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/studies")
@Api(tags = "스터디")
public class StudyApiController {
    private final StudyService studyService;

    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "스터디 개설")
    public ResponseEntity<CommonApiResponse<StudyResponseDto>> makeStudy(
            @ApiIgnore Authentication authentication,
            @Valid @RequestPart StudyRequestDto studyRequestDto,
            @RequestPart(required = false) List<MultipartFile> multipartFiles) {
        return ResponseEntity.ok(CommonApiResponse.of(studyService.makeStudy(authentication.getName(), studyRequestDto, multipartFiles)));
    }

    @GetMapping("{studyId}/qnas")
    @ApiOperation(value = "스터디 QnA 조회")
    public ResponseEntity<CommonApiResponse<QnaResponseDto>> showQna(
            @PathVariable Long studyId) {
        return ResponseEntity.ok(CommonApiResponse.of(studyService.showQna(studyId)));
    }

    @PostMapping("{studyId}")
    @ApiOperation(value = "스터디 지원")
    public ResponseEntity<CommonApiResponse<String>> makeStudyApply(
            @ApiIgnore Authentication authentication,
            @PathVariable Long studyId,
            @RequestBody QnaRequestDto qnaRequestDto) {
        return ResponseEntity.ok(CommonApiResponse.of(studyService.makeStudyApply(authentication.getName(), studyId, qnaRequestDto)));
    }

    @PatchMapping("{studyId}")
    @ApiOperation(value = "스터디 지원가능 수정")
    public ResponseEntity<CommonApiResponse<String>> modRecruiting(
            @PathVariable Long studyId) {
        return ResponseEntity.ok(CommonApiResponse.of(studyService.modRecruiting(studyId)));
    }

    @GetMapping("{studyId}/studyMembers")
    @ApiOperation(value = "스터디 멤버 조회")
    public ResponseEntity<CommonApiResponse<StudyMemberResponseDto>> showStudyMembers(
            @ApiIgnore Authentication authentication,
            @PathVariable Long studyId) {
        return ResponseEntity.ok(CommonApiResponse.of(studyService.showStudyMembers(authentication.getName(), studyId)));
    }

    @GetMapping("{studyId}/applies")
    @ApiOperation(value = "스터디 지원자 조회")
    public ResponseEntity<CommonApiResponse<List<StudyApplyResponseDto>>> showStudyApplicants(
            @PathVariable Long studyId) {
        return ResponseEntity.ok(CommonApiResponse.of(studyService.showStudyApplicants(studyId)));
    }

    @PostMapping("{studyId}/applies")
    @ApiOperation(value = "스터디 지원자 승인")
    public ResponseEntity<CommonApiResponse<String>> approveStudyApplicant(
            @PathVariable Long studyId,
            @RequestBody StudyApplyRequestDto studyApplyRequestDto) {
        return ResponseEntity.ok(CommonApiResponse.of(studyService.approveStudyApplicant(studyId, studyApplyRequestDto.getMemberId())));
    }

    @DeleteMapping("{studyId}/applies/{studyApplyId}")
    @ApiOperation(value = "스터디 지원자 거절")
    public ResponseEntity<Void> delStudyApplicant(
            @PathVariable Long studyId,
            @PathVariable Long studyApplyId) {
        studyService.delStudyApplicant(studyApplyId);
        return ResponseEntity.noContent().build();
    }
}
