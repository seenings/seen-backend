package io.github.seenings.zone.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ZoneRecord
 *
 * @author chixuehui
 * @since 2022-05-15
 */
@Data
public class ZoneRecord {

    private Integer id;
    private Long userId;
    private String userName;
    private LocalDateTime sendDateTime;
    private String content;
    private String headUrl;
    private List<String> photoUrls;
    private List<ZoneReply> zoneReplys;
}
