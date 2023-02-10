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

    @GetMapping("{studyId}")
    @ApiOperation(value = "스터디 조회")
    public ResponseEntity<CommonApiResponse<StudyResponseDto>> showStudy(
            @ApiIgnore Authentication authentication,
            @PathVariable Long studyId) {
        return ResponseEntity.ok(CommonApiResponse.of(studyService.showStudy(authentication.getName(), studyId)));
    }

    @PatchMapping("{studyId}/lang")
    @ApiOperation(value = "스터디 언어 수정(스터디장)")
    public ResponseEntity<CommonApiResponse<StudyResponseDto>> modStudyLang(
            @PathVariable Long studyId,
            @Valid @RequestBody StudyLangReqeustDto studyLangRequestDto) {
        return ResponseEntity.ok(CommonApiResponse.of(studyService.modStudyLang(studyId, studyLangRequestDto)));
    }

    @PatchMapping("{studyId}/tool")
    @ApiOperation(value = "스터디 도구 수정(스터디장)")
    public ResponseEntity<CommonApiResponse<StudyResponseDto>> modStudyTool(
            @PathVariable Long studyId,
            @Valid @RequestBody StudyToolRequestDto studyToolRequestDto) {
        return ResponseEntity.ok(CommonApiResponse.of(studyService.modStudyTool(studyId, studyToolRequestDto)));
    }

    @PostMapping("{studyId}/hashtags")
    @ApiOperation(value = "스터디 해시태그 추가(스터디장)")
    public ResponseEntity<CommonApiResponse<StudyResponseDto>> modStudyHashtags(
            @PathVariable Long studyId,
            @Valid @RequestBody HashtagRequestDto hashtagRequestDto) {
        return ResponseEntity.ok(CommonApiResponse.of(studyService.modStudyhashtags(studyId, hashtagRequestDto)));
    }

    @DeleteMapping("hashtag")
    @ApiOperation(value = "스터디 해시태그 삭제(스터디장)")
    public ResponseEntity<Void> delStudyHashtag(
            @RequestBody HashtagRequestDto hashtagRequestDto) {
        studyService.delStudyhashtag(hashtagRequestDto);
        return ResponseEntity.noContent().build();
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
    @ApiOperation(value = "스터디 지원가능 수정(스터디장)")
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
    @ApiOperation(value = "스터디 지원자 조회(스터디장)")
    public ResponseEntity<CommonApiResponse<List<StudyApplyResponseDto>>> showStudyApplicants(
            @PathVariable Long studyId) {
        return ResponseEntity.ok(CommonApiResponse.of(studyService.showStudyApplicants(studyId)));
    }

    @PostMapping("{studyId}/applies")
    @ApiOperation(value = "스터디 지원자 승인(스터디장)")
    public ResponseEntity<CommonApiResponse<String>> approveStudyApplicant(
            @PathVariable Long studyId,
            @RequestBody StudyApplyRequestDto studyApplyRequestDto) {
        return ResponseEntity.ok(CommonApiResponse.of(studyService.approveStudyApplicant(studyId, studyApplyRequestDto.getMemberId())));
    }

    @DeleteMapping("applies/{studyApplyId}")
    @ApiOperation(value = "스터디 지원자 거절(스터디장)")
    public ResponseEntity<Void> delStudyApplicant(
            @PathVariable Long studyApplyId) {
        studyService.delStudyApplicant(studyApplyId);
        return ResponseEntity.noContent().build();
    }
}
