package io.github.seenings.auth.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户认证
 *
 */
@TableName(value = "user_auth")
@Data
@Accessors(chain = true)
public class UserAuthPO   {
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
     * 用户认证状态
     */
    private Integer authStatus;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
