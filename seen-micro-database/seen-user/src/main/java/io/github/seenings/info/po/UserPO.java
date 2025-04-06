package io.github.seenings.info.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户标
 *
 */
@TableName(value = "user")
@Data
@Accessors(chain = true)
public class UserPO {
    /**
     * 自增ID，用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
