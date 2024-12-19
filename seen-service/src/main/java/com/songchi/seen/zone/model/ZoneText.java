package com.songchi.seen.zone.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * ZoneText
 *
 * @author chixuehui
 * @since 2022-05-03
 */
@Data
@Accessors(chain = true)
public class ZoneText {

    private Integer zoneId;
    private String text;
    private LocalDateTime publishTime;

}
