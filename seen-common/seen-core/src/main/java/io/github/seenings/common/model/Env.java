package io.github.seenings.common.model;

import lombok.Getter;

/**
 * Env
 */
@Getter
public enum Env {
    /**
     * 本地docker环境
     */
    CHIXUEHUIDOCKER,
    /**
     * 本地联调环境
     */
    LOCALTEST,
    /**
     * 本地环境
     */
    CHIXUEHUI,
    /**
     * 本地环境
     */
    LOCAL,
    /**
     * 开发环境
     */
    DEV,
    /**
     * 测试环境SIT
     */
    SIT,
    /**
     * 测试环境UAT
     */
    UAT,
    /**
     * 预生产环境
     */
    PRE,
    /**
     * 生产环境
     */
    PRD,
    ;

    /**
     * 是否生产环境
     *
     * @param env 环境
     * @return 是
     */
    public static boolean isProd(Env env) {
        return env == PRD;
    }

    /**
     * 是否预发环境
     *
     * @param env 环境
     * @return 是
     */
    public static boolean isPre(Env env) {
        return env == PRE;
    }

    /**
     * 是否测试环境
     *
     * @param env 环境
     * @return 是
     */
    public static boolean isTest(Env env) {
        return env == SIT || env == UAT;
    }

    /**
     * 是否开发环境
     *
     * @param env 环境
     * @return 是
     */
    public static boolean isDev(Env env) {
        return env == LOCAL || env == DEV || env == CHIXUEHUI || env == LOCALTEST;
    }

}
