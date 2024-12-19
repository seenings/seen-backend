package com.songchi.seen.info.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户性别
 *
 * @author chixuehui
 * @since 2022-10-06
 */
@Data
@Accessors(chain = true)
@TableName(value = "user_sex")
public class UserSexPO {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer sex;

    private LocalDateTime updateTime;
}
