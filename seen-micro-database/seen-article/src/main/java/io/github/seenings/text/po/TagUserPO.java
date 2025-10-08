package io.github.seenings.text.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(type = IdType.AUTO)
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
