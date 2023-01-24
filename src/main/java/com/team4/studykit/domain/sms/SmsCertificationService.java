package com.team4.studykit.domain.sms;

import com.team4.studykit.domain.sms.dto.SmsCertificationRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface SmsCertificationService {
    String sendVerificationMessage(String to);
    String verifyCode(SmsCertificationRequestDTO smsCertificationRequestDto);
}
