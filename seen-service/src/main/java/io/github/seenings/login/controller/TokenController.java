package io.github.seenings.login.controller;

import cn.hutool.core.util.StrUtil;
import io.github.seenings.extra.util.JwtUtils;
import io.github.seenings.common.exception.SeenException;
import io.github.seenings.common.model.R;
import io.github.seenings.common.model.UserIdAndToken;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.sys.constant.PublicConstant;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录
 */
@Slf4j
@RestController
@RequestMapping(PublicConstant.PUBLIC + "login/token")
public class TokenController {
    /**
     * 校验令牌
     *
     * @param token    令牌
     * @param response 响应
     * @return 返回用户与令牌
     */
    @GetMapping("validate-token")
    public R<UserIdAndToken> validateToken(@RequestHeader(PublicConstant.TOKEN_NAME) String token,
                                           HttpServletResponse response) {
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
