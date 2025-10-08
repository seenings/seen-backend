package io.github.seenings.extra.util;

import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSignerUtil;
import io.github.seenings.common.exception.SeenException;
import io.github.seenings.common.model.UserIdAndToken;
import io.github.seenings.core.util.StrUtils;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chixh
 * @since 2021-05-08
 */
@Slf4j
public class JwtUtils {

    /**
     * 公司
     */
    public static final String SONG_CHI = "songchi";
    /**
     * jwt标识
     */
    public static final String JWT = "JWT";

    /**
     * 有效时长，默认1天
     */
    public static final long EFFECTIVE_TIME = 24 * 60 * 60 * 1000;
    /**
     * 保密，后续要加密存储起来
     */
    public static final String TOKEN_PASSWORD = "seen2021";

    private JwtUtils() {
    }

    /**
     * 创建token
     *
     * @param userId        用户ID
     * @param effectiveTime 有效时长
     * @return token
     */
    public static String createToken(String userId, long effectiveTime) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + effectiveTime);
        Map<String, Object> payload = new HashMap<>();
        payload.put(JWTPayload.SUBJECT, userId);
        payload.put(JWTPayload.ISSUER, SONG_CHI);
        payload.put(JWTPayload.JWT_ID, JWT);
        payload.put(JWTPayload.ISSUED_AT, now);
        payload.put(JWTPayload.EXPIRES_AT, expirationDate);
        return JWTUtil.createToken(payload, TOKEN_PASSWORD.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 校验token
     *
     * @param token token值
     * @return 用户ID
     * @throws SeenException token校验失败
     */
    public static String validateToken(String token) throws SeenException {
        if (StrUtil.isBlank(token)) {
            String msg = "token不存在";
            log.error(msg);
            throw new SeenException(msg);
        }
        try {
            cn.hutool.jwt.JWT jwt = JWTUtil.parseToken(token);
            JWTValidator jwtValidator = JWTValidator.of(jwt);
            jwtValidator.validateAlgorithm(JWTSignerUtil.hs256(TOKEN_PASSWORD.getBytes(StandardCharsets.UTF_8)));
            try {
                jwtValidator.validateDate();
            } catch (ValidateException e1) {
                log.error("token已过期。", e1);
                throw new SeenException(e1);
            }
            JWTPayload payload = jwt.getPayload();
            Object userIdObject = payload.getClaim(JWTPayload.SUBJECT);
            return StrUtils.objectToString(userIdObject);
        } catch (Exception e) {
            log.error("校验token失败", e);
            throw new SeenException(e);
        }
    }

    /**
     * 刷新token
     *
     * @param token         token
     * @param effectiveTime 有效时长
     * @return 用户ID和token
     * @throws SeenException token校验或生成失败异常
     */
    public static UserIdAndToken refreshToken(String token, long effectiveTime) throws SeenException {
        String userId = validateToken(token);
        if (StrUtil.isBlank(userId)) {
            String msg = "用户ID不存在";
            log.error(msg);
            throw new SeenException(msg);
        }
        String newToken = createToken(userId, effectiveTime);
        return UserIdAndToken.builder()
                .userId(Long.parseLong(userId))
                .token(newToken)
                .build();
    }
}
