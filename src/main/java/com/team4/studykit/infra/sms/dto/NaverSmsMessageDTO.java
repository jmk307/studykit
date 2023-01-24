package com.team4.studykit.infra.sms.dto;

import lombok.*;

/**
 * 메세지 전송을 위한 DTO
 * @author gengminy 22.09.10.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class NaverSmsMessageDTO {
    String to;
    String content;
}
