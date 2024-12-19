package com.songchi.seen.sys.model;

import lombok.Data;

/**
 * SeenMinioConfig
 */
@Data
public class SeenMinioConfig {
    /**
     * 地址
     */
    private String endpoint = "http://127.0.0.1:9000";
    /**
     * 帐号
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;
}
