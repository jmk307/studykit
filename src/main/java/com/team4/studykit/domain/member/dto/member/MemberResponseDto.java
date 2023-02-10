package com.team4.studykit.domain.member.dto.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.team4.studykit.domain.member.entity.Member;
import com.team4.studykit.domain.member.model.Social;
import com.team4.studykit.global.config.security.dto.TokenResponseDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) //NULL 필드 가림
public class MemberResponseDto {
    private String id;

    private String nickname;

    private boolean joinAccepted;

    private Social social;

    private boolean isNew;

    private String accessToken;

    private String refreshToken;

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .joinAccepted(member.getJoinAccepted())
                .social(member.getSocial())
                .build();
    }

    public static MemberResponseDto of(Member member, TokenResponseDto tokenResponseDto) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .joinAccepted(member.getJoinAccepted())
                .social(member.getSocial())
                .accessToken(tokenResponseDto.getAccessToken())
                .refreshToken(tokenResponseDto.getRefreshToken())
                .build();
    }

    public static MemberResponseDto ofSocial(Member member, TokenResponseDto tokenResponseDto, boolean isNew) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .joinAccepted(member.getJoinAccepted())
                .social(member.getSocial())
                .accessToken(tokenResponseDto.getAccessToken())
                .refreshToken(tokenResponseDto.getRefreshToken())
                .isNew(isNew)
                .build();
    }
}
