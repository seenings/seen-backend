package io.github.seenings.sys.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpStatus;
import io.github.seenings.core.util.StrUtils;
import io.github.seenings.extra.util.JwtUtil;
import io.github.seenings.sys.constant.PublicConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;

/// 令牌认证拦截器
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    /// 认证结果
    @Accessors(chain = true)
    @Data
    public static class AuthResult {
        /// 认证
        private boolean auth;
        /// 用户ID
        private Long userId;
    }

    /// 认证
    ///
    /// @param userIdBySession  会话中的用户ID
    /// @param tokenByHeader    请求头中的token
    /// @param tokenByParameter 请求参数中的token
    /// @return 认证结果
    public static AuthResult auth(Long userIdBySession, String tokenByHeader, String tokenByParameter) {
        if (userIdBySession != null) {
            return new AuthResult().setAuth(true).setUserId(userIdBySession);
        }
        String token = StrUtil.blankToDefault(tokenByHeader, tokenByParameter);
        if (StrUtil.isNotBlank(token)) {
            String userIdString;
            try {
                userIdString = JwtUtil.validateToken(token);
                userIdBySession = StrUtils.stringToLong(userIdString);
                if (userIdBySession == null) {
                    return new AuthResult();
                } else {
                    log.info("用户认证成功，用户ID：{}。", userIdBySession);
                    return new AuthResult().setAuth(true).setUserId(userIdBySession);
                }
            } catch (Exception e) {
                log.error("", e);
                return new AuthResult();
            }
        } else {
            return new AuthResult();
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 会话，请求头，问号参数，未认证写响应体
        HttpSession httpSession = request.getSession();
        if (httpSession == null) {
            httpSession = request.getSession(true);
        }
        Long userIdBySession = (Long) httpSession.getAttribute(PublicConstant.USER_ID);
        String tokenByHeader = request.getHeader(PublicConstant.TOKEN_NAME);
        String tokenByParameter = request.getParameter(PublicConstant.TOKEN_NAME);
        String uri = request.getRequestURI();
        AuthResult authResult = auth(userIdBySession, tokenByHeader, tokenByParameter);
        if (authResult.isAuth()) {
            httpSession.setAttribute(PublicConstant.USER_ID, authResult.getUserId());
            return HandlerInterceptor.super.preHandle(request, response, handler);
        } else {
            String msg = "用户认证失败，路径：%s".formatted(uri);
            log.error(msg);
            response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
            response.setContentType(ContentType.JSON.getValue());
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().print(msg);
            return false;
        }
    }
}
