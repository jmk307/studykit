package com.team4.studykit.infra.sms.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class NaverSmsRequestDTO {
    String type;
    String contentType;
    String countryCode;
    String from;
    String content;
    List<NaverSmsMessageDTO> messages;

    @Builder
    public NaverSmsRequestDTO(String type, String contentType, String countryCode, String from,
                              String content, List<NaverSmsMessageDTO> messages) {
        this.type = type;
        this.contentType = contentType;
        this.countryCode = countryCode;
        this.from = from;
        this.content = content;
        this.messages = messages;
    }
}
