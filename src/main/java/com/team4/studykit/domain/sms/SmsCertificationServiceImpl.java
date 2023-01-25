package com.team4.studykit.domain.sms;

import com.team4.studykit.domain.sms.dto.SmsCertificationRequestDTO;
import com.team4.studykit.global.config.redis.RedisService;
import com.team4.studykit.global.error.ErrorCode;
import com.team4.studykit.global.error.exception.BadRequestException;
import com.team4.studykit.global.error.exception.UnauthorizedException;
import com.team4.studykit.infra.sms.NaverSmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsCertificationServiceImpl implements SmsCertificationService {
    private final NaverSmsService naverSmsService;
    private final RedisService redisService;
    private static final String VERIFICATION_PREFIX = "sms:";
    private static final int VERIFICATION_TIME_LIMIT = 3 * 60;

    @Value("${spring.profiles.active}")
    private String springProfile;

    /**
     * 인증번호가 담긴 메세지를 전송한다
     * @param to 수신자
     * @return 전송 성공시 메세지
     */
    @Override
    public String sendVerificationMessage(String to) {
        //랜덤 6자리 인증번호
        final String verificationCode = generateVerificationCode();
        //3분 제한시간
        final Duration verificationTimeLimit = Duration.ofSeconds(VERIFICATION_TIME_LIMIT);

        //[local, dev] 배포 환경이 아닐때는 fake service 를 제공합니다
        if (springProfile == null || !springProfile.equals("prod")) {
            log.info("스프링 프로파일(" + springProfile + ") 따라 fake 서비스를 제공합니다");
            String message = generateMessageWithCode(verificationCode);
            log.info(message);
            redisService.setValues(VERIFICATION_PREFIX + to, verificationCode, verificationTimeLimit);
            return message;
        }

        //[prod] 실 배포 환경에서는 문자를 전송합니다
        try {
            //네이버 sms 메세지 dto
            if (naverSmsService.sendMessage(to, generateMessageWithCode(verificationCode))) {
                //전송 성공하면 redis 에 3분 제한의 인증번호 토큰 저장
                redisService.setValues(VERIFICATION_PREFIX + to, verificationCode, verificationTimeLimit);
                return "메세지 전송 성공";
            } else {
                throw new BadRequestException(ErrorCode._BAD_REQUEST, "메세지 전송에 실패하였습니다");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(ErrorCode._BAD_REQUEST, "메세지 발송에 실패하였습니다");
        }
    }

    /**
     * 인증번호를 검증한다
     * @param requestDTO {phoneNumber: 휴대폰 번호, code: 인증번호}
     * @return TokenResponseDTO { accessToken, refreshToken }
     */
    @Override
    public String verifyCode(SmsCertificationRequestDTO requestDTO) {
        String phoneNumber = requestDTO.getPhoneNumber();
        String code = requestDTO.getCode();
        String key = VERIFICATION_PREFIX + phoneNumber;

        //redis 에 해당 번호의 키가 없는 경우는 인증번호(3분) 만료로 처리
        if (!redisService.hasKey(key)) {
            throw new UnauthorizedException(ErrorCode.EXPIRED_VERIFICATION_CODE);
        }

        //redis 에 해당 번호의 키와 인증번호가 일치하지 않는 경우
        if (!redisService.getValues(key).equals(code)) {
            throw new UnauthorizedException(ErrorCode.MISMATCH_VERIFICATION_CODE);
        }

        //필터를 모두 통과, 인증이 완료되었으니 redis 에서 전화번호 삭제
        redisService.deleteValues(key);
        return "인증에 성공하였습니다";
    }

    /**
     * 랜덤 인증번호를 생성한다
     * @return 인증번호 6자리
     */
    private String generateVerificationCode() {
        Random random = new Random();
        int verificationCode = random.nextInt(888888) + 111111;
        return Integer.toString(verificationCode);
    }

    /**
     * 인증번호가 포함된 메세지를 생성한다
     * @param code 인증번호 6자리
     * @return 인증번호 6자리가 포함된 메세지
     */
    private String generateMessageWithCode(String code) {
        final String provider = "스터디키트";
        return "[" + provider + "] 인증번호 [" + code + "] 를 입력해주세요 :)";
    }
}
