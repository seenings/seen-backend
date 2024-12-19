package com.songchi.seen.zone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 父级标签
 *
 * @author chixuehui
 * @since 2022-05-03
 */
@TableName(value = "tag_parent")
@Data
public class TagParent {
    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标签名
     */
    @TableField(value = "tag_name")
    private String tagName;

}