package io.github.seenings.zone.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 用户回复
 *
 * @author chixuehui
 * @since 2022-05-15
 */
@Data
@Accessors(chain = true)
public class ZoneReply {
    private Integer id;
    private String userName;
    private String content;
    private Integer replyId;
    private List<ZoneReply> replies;
}
