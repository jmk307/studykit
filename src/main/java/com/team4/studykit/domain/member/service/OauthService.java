package com.team4.studykit.domain.member.service;

import com.team4.studykit.domain.member.dto.member.MemberResponseDto;
import com.team4.studykit.domain.member.dto.oauth.GoogleUserDto;
import com.team4.studykit.domain.member.dto.oauth.KakaoUserDto;
import com.team4.studykit.domain.member.entity.Member;
import com.team4.studykit.domain.member.repository.MemberRepository;
import com.team4.studykit.global.config.CommonApiResponse;
import com.team4.studykit.global.config.security.dto.TokenRequestDto;
import com.team4.studykit.global.config.security.dto.TokenResponseDto;
import com.team4.studykit.global.config.security.jwt.TokenProvider;
import com.team4.studykit.global.error.ErrorCode;
import com.team4.studykit.global.error.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OauthService {
    private final WebClient webClient;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    @Value("${kakao.rest_api}")
    private String kakaoRestApi;
    @Value("${kakao.redirect_url}")
    private String kakaoRedirect;

    @Value("${google.client_id}")
    private String googleClientId;

    @Value("${google.client_secret}")
    private String googleClientSecret;

    @Value("${google.redirect_url}")
    private String googleRedirect;


    // 소셜 로그인 & 회원가입
    @Transactional
    public ResponseEntity<CommonApiResponse<MemberResponseDto>> oauthLogin(String provider, String accessToken) {
        String mail = "";
        String email = "";
        String name = "";

        if (provider.equals("kakao")) {
            KakaoUserDto kakaoUserDto = getKakaoUser(accessToken);
            mail = kakaoUserDto.getKakaoAccount().getEmail();
            email = kakaoUserDto.getKakaoAccount().getEmail();
            name = kakaoUserDto.getProperties().getNickname();
        } else if (provider.equals("google")) {
            GoogleUserDto googleUserDto = getGoogleUser(accessToken);
            mail = googleUserDto.getEmail();
            email = googleUserDto.getEmail();
            name = googleUserDto.getName();
        }

        Optional<Member> checkMember = memberRepository.findById(
                mail.substring(0, mail.indexOf("@"))
        );

        if (checkMember.isPresent()) {
            log.info("가입된 회원");
            /* 이미 가입된 회원 */
            HttpHeaders httpHeaders = new HttpHeaders();
            TokenResponseDto tokenResponseDTO = tokenProvider.generateToken(mail.substring(0, mail.indexOf("@")));
            httpHeaders.add("Authorization", "Bearer " + tokenResponseDTO.getAccessToken());

            return new ResponseEntity<>(CommonApiResponse.of(MemberResponseDto.of(checkMember.get(), tokenResponseDTO)), httpHeaders, HttpStatus.OK);
        } else {
            /* 새로 가입할 회원 */
            Member member = Member.builder()
                    .id(email.substring(0, mail.indexOf("@")))
                    .nickname(name)
                    .password("social")
                    .joinAccepted(true)
                    .isSocial(true)
                    .build();
            memberRepository.save(member);

            log.info("새로운 회원");

            HttpHeaders httpHeaders = new HttpHeaders();
            TokenResponseDto tokenResponseDTO = tokenProvider.generateToken(mail.substring(0, mail.indexOf("@")));
            httpHeaders.add("Authorization", "Bearer " + tokenResponseDTO.getAccessToken());

            return new ResponseEntity<>(CommonApiResponse.of(MemberResponseDto.of(member, tokenResponseDTO)), httpHeaders, HttpStatus.OK);
        }
    }

    public TokenRequestDto getSocialAccessToken(String provider, String code) {
        if (provider.equals("kakao")) {
            String getTokenURL =
                    "https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id="
                            + kakaoRestApi + "&redirect_uri=" + kakaoRedirect + "&code="
                            + code;

            try {
                return webClient.post()
                        .uri(getTokenURL)
                        .retrieve()
                        .bodyToMono(TokenRequestDto.class).block();
            } catch (Exception e) {
                e.printStackTrace();
                throw new BadRequestException(ErrorCode.KAKAO_BAD_REQUEST);
            }
        } else {
            String getTokenURL =
                    "https://oauth2.googleapis.com/token"
                            + "?code=" + code
                            + "&client_id="+ googleClientId + "&client_secret=" + googleClientSecret
                            + "&redirect_uri=" + googleRedirect + "&grant_type=authorization_code";

            try {
                return webClient.post()
                        .uri(getTokenURL)
                        .retrieve()
                        .bodyToMono(TokenRequestDto.class).block();
            } catch (Exception e) {
                e.printStackTrace();
                throw new BadRequestException(ErrorCode.GOOGLE_BAD_REQUEST);
            }
        }
    }

    public KakaoUserDto getKakaoUser(String kakaoAccessToken) {
        String getUserURL = "https://kapi.kakao.com/v2/user/me";

        try {
            return webClient.post()
                            .uri(getUserURL)
                            .header("Authorization", "Bearer " + kakaoAccessToken)
                            .retrieve()
                            .bodyToMono(KakaoUserDto.class)
                            .block();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(ErrorCode.KAKAO_BAD_REQUEST);
        }
    }

    public GoogleUserDto getGoogleUser(String googleAccessToken) {
        String getUserURL = "https://www.googleapis.com/oauth2/v1/userinfo";

        try {
            return webClient.post()
                    .uri(getUserURL)
                    .header("Authorization", "Bearer " + googleAccessToken)
                    .retrieve()
                    .bodyToMono(GoogleUserDto.class)
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(ErrorCode.GOOGLE_BAD_REQUEST);
        }
    }
}