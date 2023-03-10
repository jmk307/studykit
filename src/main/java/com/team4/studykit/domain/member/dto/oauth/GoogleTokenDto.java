package com.team4.studykit.domain.member.dto.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleTokenDto {
    private String access_token;
    private Integer expires_in;
    private String scope;
    private String token_type;
    private String id_token;
    private String refresh_token;
}
