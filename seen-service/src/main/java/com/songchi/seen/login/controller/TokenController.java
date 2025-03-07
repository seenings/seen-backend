package com.songchi.seen.login.controller;

import cn.hutool.core.util.StrUtil;
import com.songchi.seen.extra.util.JwtUtils;
import com.songchi.seen.common.exception.SeenException;
import com.songchi.seen.common.model.R;
import com.songchi.seen.common.model.UserIdAndToken;
import com.songchi.seen.common.util.ResUtils;
import com.songchi.seen.sys.constant.PublicConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录
 */
@Slf4j
@RestController
@RequestMapping(PublicConstant.PUBLIC + "login/token")
public class TokenController {

    @GetMapping("validate-token")
    public R<UserIdAndToken> validateToken(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(PublicConstant.TOKEN_NAME);
        if (StrUtil.containsAny(token, "undefined", "null") || StrUtil.isBlank(token)) {
            return ResUtils.error("请登录！");
        }
        UserIdAndToken userIdAndToken;
        try {
            userIdAndToken = JwtUtils.refreshToken(token, JwtUtils.EFFECTIVE_TIME);
        } catch (SeenException e) {
            log.error("", e);
            return ResUtils.error("验证令牌失效，请重新登录！");
        }
        response.setHeader(PublicConstant.TOKEN_NAME, userIdAndToken.getToken());
        return ResUtils.ok(userIdAndToken);
    }
}
