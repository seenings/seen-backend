package io.github.seenings.sys.constant;

/**
 * 公共常量
 *
 * @author chixuehui
 * @since 2022-02-19
 */
public class PublicConstant {

    /**
     * 小写的seen
     */
    public static final String SMALL_SEEN = "seen";
    /**
     * 路径前缀
     */
    public static final String SEEN = "/seen";
    /**
     * token名
     */
    public static final String TOKEN_NAME = "seen-token";
    /**
     * 用户ID名
     */
    public static final String USER_ID = "userId";
    /**
     * 无权限url开头
     */
    public static final String PUBLIC = SEEN + "/public-v1/";
    /**
     * websocket通信url开头
     */
    public static final String WEBSOCKET_VERSION = SEEN + "/websocket-v1/";
    /**
     * sse通信url开头
     */
    public static final String SSE_VERSION = SEEN + "/sse-v1/";

    /**
     * 权限url开头
     */
    public static final String REST = SEEN + "/rest-v1/";
    /**
     * 照片url没有seen开头
     */
    public static final String PHOTO_NO_HEADER_VERSION = "/photo-v1/";
    /**
     * 语音url没有seen开头
     */
    public static final String VOICE_NO_HEADER_VERSION = "/voice-v1/";
    /**
     * 照片url开头
     */
    public static final String PHOTO_VERSION = SEEN + PHOTO_NO_HEADER_VERSION;
    /**
     * 语音url开头
     */
    public static final String VOICE_VERSION = SEEN + VOICE_NO_HEADER_VERSION;

    private PublicConstant() {
    }
}
