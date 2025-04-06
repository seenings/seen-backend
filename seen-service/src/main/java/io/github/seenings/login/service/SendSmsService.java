package io.github.seenings.login.service;

public interface SendSmsService {

    void sendSmsCode(String phone, int smsCode);

    int generateCode(String phone);
}
