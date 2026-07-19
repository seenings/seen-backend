package io.github.seenings.common.model;

import lombok.Data;

/**
 * 路径配置
 */
@Data
public class PathConfig {

    /**
     * 照片路径
     */
    private String photoPath = "/Users/chixuehui/seen/photo/";

    /**
     * 语音路径
     */
    private String voicePath = "/Users/chixuehui/seen/voice/";
}
