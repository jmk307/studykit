package com.team4.studykit.domain.sms;

import com.team4.studykit.domain.sms.dto.SmsCertificationRequestDTO;
import com.team4.studykit.domain.sms.dto.SmsVerificationCodeRequestDTO;
import com.team4.studykit.global.config.CommonApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/sms")
@RequiredArgsConstructor
@Api(tags = "문자 인증")
@ApiIgnore
public class SmsCertificationController {
    private final SmsCertificationService smsCertificationService;

    @PostMapping("/send")
    @ApiOperation(value = "인증번호가 담긴 문자를 보낸다")
    public CommonApiResponse<String> sendMessageWithVerificationCode(@Valid @RequestBody SmsVerificationCodeRequestDTO dto) {
        String result = smsCertificationService.sendVerificationMessage(dto.getPhoneNumber());
        return CommonApiResponse.of(result);
    }

    @PostMapping("/verify")
    @ApiOperation(value = "인증번호를 입력하여 인증한다")
    public CommonApiResponse<String> verifyCode(@RequestBody @Valid SmsCertificationRequestDTO dto) {
        String responseDTO = smsCertificationService.verifyCode(dto);
        return CommonApiResponse.of(responseDTO);
    }
}
