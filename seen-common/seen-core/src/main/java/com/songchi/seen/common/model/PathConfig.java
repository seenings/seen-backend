package com.songchi.seen.common.model;

import lombok.Data;

/**
 * PathConfig
 *
 * @author chixuehui
 * @since 2022-05-04
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
