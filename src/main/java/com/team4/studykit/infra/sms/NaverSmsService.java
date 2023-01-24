package com.team4.studykit.infra.sms;

public interface NaverSmsService {
    boolean sendMessage(String to, String message);
}
