package io.github.seenings.chat.socket;

import cn.hutool.core.util.StrUtil;
import io.github.seenings.chat.http.HttpChatHistoryService;
import io.github.seenings.common.exception.SeenException;
import io.github.seenings.common.exception.SeenRuntimeException;
import io.github.seenings.core.util.StrUtils;
import io.github.seenings.extra.util.JwtUtils;
import io.github.seenings.sys.constant.PublicConstant;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import static cn.hutool.core.collection.CollUtil.newHashSet;

/**
 * @author chixuehui
 * 2021-06-13
 */
@ServerEndpoint(value = PublicConstant.WEBSOCKET_VERSION + "two-chat/{" + PublicConstant.TOKEN_NAME + "}")
@RestController
@Slf4j
@AllArgsConstructor
public class TwoChatSocket {

    public static final ConcurrentHashMap<Long, Session> TWO_CHAT_MAP = new ConcurrentHashMap<>();


    /**
     * 实现服务器主动推送
     */
    public static void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("", e);
            throw new SeenRuntimeException(e);
        }
    }

    public static Long tokenToUserId(String token, String sessionId, String sesseionUrl) {
        if (StrUtil.isBlank(token)) {
            String msg = String.format("令牌验证失败，会话ID是：%s，会话地址是：%s。", sessionId, sesseionUrl);
            log.error(msg);
            throw new SeenRuntimeException(msg);
        }
        String validateToken;
        try {
            validateToken = JwtUtils.validateToken(token);
        } catch (SeenException e) {
            throw new SeenRuntimeException(e);
        }
        Long userId = StrUtils.stringToLong(validateToken);
        if (userId == null) {
            String msg = String.format("获取不到当前登录用户id，会话ID是：%s，会话地址是：%s。", sessionId, sesseionUrl);
            log.error(msg);
            throw new SeenRuntimeException(msg);
        }
        return userId;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam(PublicConstant.TOKEN_NAME) String token, Session session) {
        Long userId =
                tokenToUserId(token, session.getId(), session.getRequestURI().toString());
        // 加入set中
        TWO_CHAT_MAP.replace(userId, session);
        String msg = String.format("用户连接成功，用户ID：%s，会话ID：%s。", userId, session.getId());
        log.warn(msg);
    }

    private HttpChatHistoryService httpChatHistoryService;

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 消息
     */
    @OnMessage
    public void onMessage(String message) {
        log.warn("报文:{}", message);
        if (StrUtil.isBlank(message)) {
            log.error("发送消息为空。");
            return;
        }
        int historyId = Integer.parseInt(message);
        Long toUserId = httpChatHistoryService.idToToUserId(newHashSet(historyId)).get(historyId);
        Session session = TWO_CHAT_MAP.get(toUserId);
        if (session != null && session.isOpen()) {
            //在线则推送消息
            sendMessage(session, String.valueOf(historyId));
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam(PublicConstant.TOKEN_NAME) String token, Session session) {
        Long userId =
                tokenToUserId(token, session.getId(), session.getRequestURI().toString());
        TWO_CHAT_MAP.remove(userId);
        log.warn("用户退出，用户ID：{}。", userId);
    }

    @OnError
    public void onError(Throwable e, @PathParam(PublicConstant.TOKEN_NAME) String token, Session session) {
        Long userId =
                tokenToUserId(token, session.getId(), session.getRequestURI().toString());
        log.error("websocket有异常，用户ID：{}。", userId, e);
    }
}
