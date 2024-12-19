package com.songchi.seen.login.controller;

import com.songchi.seen.account.http.HttpCoinAccountService;
import com.songchi.seen.coin.constant.CoinConstant;
import com.songchi.seen.coin.enumeration.TradeType;
import com.songchi.seen.common.model.R;
import com.songchi.seen.common.util.ResUtils;
import com.songchi.seen.extra.util.JwtUtils;
import com.songchi.seen.info.http.HttpUserService;
import com.songchi.seen.login.entity.SmsCode;
import com.songchi.seen.login.service.ISmsCodeService;
import com.songchi.seen.login.service.SendSmsService;
import com.songchi.seen.sys.constant.PublicConstant;
import com.songchi.seen.trade.http.HttpCoinTradeService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(PublicConstant.PUBLIC)
public class PhoneController {
    @Resource
    ISmsCodeService iSmsCodeService;

    @Resource
    SendSmsService sendSmsService;

    @PostMapping("login/logout")
    public R<String> logout(@SessionAttribute(required = false) Integer userId, HttpSession session) {
        session.invalidate();
        log.info("用户{}注销登录成功。", userId);
        return ResUtils.ok("注销登录成功。");
    }

    @Resource
    private HttpUserService httpUserService;

    @Resource
    private HttpCoinAccountService httpCoinAccountService;
    @Resource
    private HttpCoinTradeService httpCoinTradeService;

    @PostMapping("login/phone")
    public R<Integer> phone(@RequestBody SmsCode smsCode, HttpServletResponse response) {
        boolean validate = iSmsCodeService.validate(smsCode.getPhone(), smsCode.getSmsId(), smsCode.getSmsCode());
        if (!validate) {
            return ResUtils.error("验证码校验失败");
        }
        Integer userId = httpUserService.phoneNumberToUserId(Set.of(smsCode.getPhone())).get(smsCode.getPhone());
        if (userId == null) { // 如果是第一次登录，则写入用户表
            userId = httpUserService.set(smsCode.getPhone());
            // 注册时，初始化虚拟币，并赠送玫瑰花个数50
            httpCoinAccountService.initAccount(userId);
            httpCoinTradeService.simpleTradeTypeTo(userId, CoinConstant.SIGN_UP_COIN_AMOUNT, TradeType.SIGN_UP);

        }
        String token = JwtUtils.createToken(String.valueOf(userId), JwtUtils.EFFECTIVE_TIME);
        response.setHeader(PublicConstant.TOKEN_NAME, token);
        return ResUtils.ok(userId, "登录成功");
    }

    //        phone: this.phone,
    @PostMapping("login/send-code")
    public R<Integer> sendCode(@RequestParam String phone) {
        // 生成验证码id
        int smsId = sendSmsService.generateCode(phone);
        // 返回验证码id给客户端，保证验证码id和code的一一映射关系
        return ResUtils.ok(smsId, "发送验证码成功");
    }
}
