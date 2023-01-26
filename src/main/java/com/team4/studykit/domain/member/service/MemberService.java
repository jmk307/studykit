package com.team4.studykit.domain.member.service;

import com.team4.studykit.domain.member.dto.LoginDto;
import com.team4.studykit.domain.member.dto.member.MemberRequestDto;
import com.team4.studykit.domain.member.dto.member.MemberResponseDto;
import com.team4.studykit.domain.member.entity.Member;
import com.team4.studykit.domain.member.model.Social;
import com.team4.studykit.domain.member.repository.MemberRepository;
import com.team4.studykit.global.config.CommonApiResponse;
import com.team4.studykit.global.config.security.dto.TokenResponseDto;
import com.team4.studykit.global.config.security.jwt.TokenProvider;
import com.team4.studykit.global.error.ErrorCode;
import com.team4.studykit.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    // 일반 회원가입
    @Transactional
    public MemberResponseDto makeMember(MemberRequestDto memberRequestDto) {
        Optional<Member> checkMember = memberRepository.findById(memberRequestDto.getId());
        if (checkMember.isPresent()) {
            throw new BadRequestException(ErrorCode.MEMBER_ALREADY_EXIST);
        }

        if (!memberRequestDto.getJoinAccepted()) {
            throw new BadRequestException(ErrorCode.JOIN_NOT_ACCEPTED);
        }

        Member member = Member.builder()
                .id(memberRequestDto.getId())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .nickname(memberRequestDto.getNickname())
                .joinAccepted(memberRequestDto.getJoinAccepted())
                .social(Social.COMMON)
                .build();
        memberRepository.save(member);

        return MemberResponseDto.of(member);
    }
    
    // 일반 로그인
    @Transactional
    public ResponseEntity<CommonApiResponse<MemberResponseDto>> loginMember(LoginDto loginDto) {
        Optional<Member> checkMember = memberRepository.findById(loginDto.getId());
        if (checkMember.isEmpty()) {
            throw new BadRequestException(ErrorCode.MEMBER_NOT_FOUND);
        } else if (!checkMember.get().getSocial().equals(Social.COMMON)
                && memberRepository.existsById(checkMember.get().getId())) {
            throw new BadRequestException(ErrorCode.SOCIAL_ALREADY_EXIST);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        TokenResponseDto tokenResponseDTO = tokenProvider.generateToken(loginDto.getId());

        Member member = memberRepository.findById(loginDto.getId())
                .orElseThrow(() -> new BadRequestException(ErrorCode.MEMBER_NOT_FOUND));
        httpHeaders.add("Authorization", "Bearer " + tokenResponseDTO.getAccessToken());

        return new ResponseEntity<>(CommonApiResponse.of(MemberResponseDto.of(member, tokenResponseDTO)), httpHeaders, HttpStatus.OK);
    }

    // 토큰 재발급
    @Transactional
    public ResponseEntity<CommonApiResponse<TokenResponseDto>> reissue(String accessToken, String refreshToken) {
        String email;

        if (!tokenProvider.validateTokenExceptExpiration(accessToken)){
            throw new BadRequestException(ErrorCode.INVALID_ACCESS_TOKEN);
        }

        try {
            email = tokenProvider.parseClaims(accessToken).getSubject();
        } catch (Exception e) {
            throw new BadRequestException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        tokenProvider.validateRefreshToken(email, refreshToken);

        TokenResponseDto tokenResponseDto = tokenProvider.generateToken(email);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + tokenResponseDto.getAccessToken());

        return new ResponseEntity<>(CommonApiResponse.of(tokenResponseDto), httpHeaders, HttpStatus.OK);
    }
}
