package com.team4.studykit.domain.member;

import com.team4.studykit.domain.member.dto.*;
import com.team4.studykit.global.config.CommonApiResponse;
import com.team4.studykit.global.config.security.dto.TokenResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/members")
@Api(tags = "일반 회원가입, 로그인")
public class MemberApiController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("signup")
    @ApiOperation(value = "일반 회원가입")
    public ResponseEntity<CommonApiResponse<MemberResponseDto>> makeMember(
            @Valid @RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(CommonApiResponse.of(memberService.makeMember(memberRequestDto)));
    }

    @PostMapping("login")
    @ApiOperation(value = "일반 로그인")
    public ResponseEntity<CommonApiResponse<MemberResponseDto>> loginMember(
            @Valid @RequestBody LoginDto loginDto) {
        return memberService.loginMember(loginDto);
    }

    @PostMapping("reissue")
    @ApiOperation(value = "토큰 재발급")
    public ResponseEntity<CommonApiResponse<TokenResponseDto>> reissue(
            @RequestHeader("Authorization") String accessToken,
            @RequestHeader("Refresh") String refreshToken) {
        return memberService.reissue(accessToken, refreshToken);
    }

    @PostMapping("id")
    @ApiOperation(value = "아이디 중복 검사")
    public ResponseEntity<CommonApiResponse<String>> duplicateId(
            @Valid @RequestBody DuplicateId duplicateId) {
        String result = (memberRepository.existsById(duplicateId.getId()))
                ? "이미 사용중인 아이디입니다."
                : "이 아이디는 사용 가능합니다!";
        return ResponseEntity.ok(CommonApiResponse.of(result));
    }

    @PostMapping("nickname")
    @ApiOperation(value = "닉네임 중복 검사")
    public ResponseEntity<CommonApiResponse<String>> duplicateNickname(
            @Valid @RequestBody DuplicateNickname duplicateNickname) {
        String result = (memberRepository.existsByNickname(duplicateNickname.getNickname()))
                ? "이미 사용중인 닉네임입니다."
                : "이 닉네님은 사용 가능합니다!";
        return ResponseEntity.ok(CommonApiResponse.of(result));
    }

    @GetMapping
    public ResponseEntity<String> checkMember(
            @ApiIgnore Authentication authentication) {
        return ResponseEntity.ok(authentication.getName());
    }
}
