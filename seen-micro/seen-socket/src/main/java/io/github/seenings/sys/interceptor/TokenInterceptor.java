package io.github.seenings.sys.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.jspecify.annotations.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import io.github.seenings.common.exception.SeenException;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.core.util.StrUtils;
import io.github.seenings.extra.util.JwtUtils;
import io.github.seenings.sys.constant.PublicConstant;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        // 1.通过session获取用户ID，如有则验证成功
        HttpSession session = request.getSession();
        if (session != null) {
            Long userId = (Long) session.getAttribute(PublicConstant.USER_ID);
            if (userId != null) {
                log.info("用户访问成功，用户ID：{}。", userId);
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }
        }
        // 2.通过请求头获取token，如有则校验token并获取userId，存入session会话中
        // 3.通过请求参数获取token，如有则校验token并获取userId，存入session会话中
        String token = StrUtil.blankToDefault(request.getHeader(PublicConstant.TOKEN_NAME), request.getParameter(PublicConstant.TOKEN_NAME));
        if (StrUtil.isNotBlank(token)) {
            String userIdString;
            try {
                userIdString = JwtUtils.validateToken(token);
            } catch (SeenException e) {
                writeError(request, response);
                return false;
            }
            Long userId = StrUtils.stringToLong(userIdString);
            if (userId == null) {
                writeError(request, response);
                return false;
            } else {
                if (session == null) {
                    session = request.getSession(true);
                }
                session.setAttribute(PublicConstant.USER_ID, userId);
                log.info("用户访问成功，用户ID：{}。", userId);
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }
        } else {
            writeError(request, response);
            return false;
        }
        // TODO 按场景处理，其他场景报错
        // 4.以上均获取不到，则返回401 错误
    }

    private void writeError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
        response.setContentType(ContentType.JSON.getValue());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        PrintWriter writer = response.getWriter();
        String msg = "uri=" + request.getRequestURI() + "没有登录用户，访问失败";
        log.error(msg);
        writer.print(ResUtils.error(msg));
    }
}
