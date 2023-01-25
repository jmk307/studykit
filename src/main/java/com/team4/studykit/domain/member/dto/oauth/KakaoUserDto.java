package com.team4.studykit.domain.member.dto.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.security.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserDto {
    @JsonProperty("id")
    private String authenticationCode;

    @JsonProperty("connected_at")
    private Timestamp connectedAt;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @JsonProperty("properties")
    private Properties properties;

    @Getter
    @ToString
    public static class KakaoAccount {
        private String email;
    }

    @Getter
    @ToString
    public static class Properties {
        private String nickname;
    }
}