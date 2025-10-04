package io.github.seenings.text.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 文本
 * 表 text
 */
@TableName(value = "text")
@Data
@Accessors(chain = true)
public class TextPO {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 文本内容
     */
    private String text;

    /**
     * 是否删除：0=不删除
     */
    private Integer deleted;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新用户
     */
    private Long updateUser;
}
