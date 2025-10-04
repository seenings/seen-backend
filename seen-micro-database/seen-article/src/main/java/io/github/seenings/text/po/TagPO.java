package io.github.seenings.text.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * TagPO
 *
 * @author chixuehui
 * @since 2023-01-23
 */
@TableName(value = "tag")
@Data
@Accessors(chain = true)
public class TagPO {

    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 父级标签ID
     */
    private Integer parentTagId;

    /**
     * 标签名字
     */
    private String tagName;
}
