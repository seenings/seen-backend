package com.songchi.seen.login.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.songchi.seen.login.entity.SmsCode;
import com.songchi.seen.login.service.ISmsCodeService;
import com.songchi.seen.login.service.SendSmsService;
import com.songchi.seen.sys.service.SysService;

import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;

@Service
public class SendSmsServiceImpl implements SendSmsService {
    @Resource
    ISmsCodeService iSmsCodeService;
    @Resource
    SysService sysService;

    @Override
    public void sendSmsCode(String phone, int smsCode) {
        // 调用第三方短信api服务向客户手机发送验证码
        // send TODO，抛出异常
    }

    @Override
    public int generateCode(String phone) {
        // 生成验证码id
        int smsId = RandomUtil.randomInt(100000, 999999);
        // 生成验证码code
        int smsCode;
        if (!sysService.isProd()) {
            smsCode = 123456;// TODO 等上线再随机生成，现在定为123456
        } else {
            smsCode = RandomUtil.randomInt(100000, 999999);
            // 调用第三方短信api服务向客户手机发送验证码
            sendSmsCode(phone, smsCode);
        }
        // 存入数据库
        SmsCode entity = new SmsCode();
        entity.setPhone(phone);
        entity.setSmsCode(smsCode);
        entity.setSmsId(smsId);
        entity.setUpdateTime(LocalDateTime.now());
        iSmsCodeService.save(entity);
        return smsId;
    }
}
