package io.github.seenings.info.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户呢称
 *
 */
@TableName(value ="user_alias_name")
@Data
@Accessors(chain = true)
public class UserAliasNamePO  {
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
     * 用户呢称
     */
    private String aliasName;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
