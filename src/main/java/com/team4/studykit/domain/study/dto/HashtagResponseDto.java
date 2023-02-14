package com.team4.studykit.domain.study.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class HashtagResponseDto {
    private String hashtagName;

    @Builder
    public HashtagResponseDto(String hashtagName) {
        this.hashtagName = hashtagName;
    }

    public static HashtagResponseDto of(String hashtagName) {
        return HashtagResponseDto.builder()
                .hashtagName(hashtagName)
                .build();
    }
}
