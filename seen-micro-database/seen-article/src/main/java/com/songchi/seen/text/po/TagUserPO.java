package com.songchi.seen.text.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * TagUserPO
 *
 * @author chixuehui
 * @since 2023-01-23
 */
@TableName(value = "tag_user")
@Data
@Accessors(chain = true)
public class TagUserPO {

    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户的标签ID
     */
    private Integer tagId;
}
