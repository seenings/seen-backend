package com.songchi.seen.info.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 婚姻状况
 *
 * @author chixuehui
 * @since 2023-02-11
 */
@Data
@Accessors(chain = true)
@TableName(value = "user_marital")
public class UserMaritalPO {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Long userId;

    private Integer maritalStatus;

    private LocalDateTime updateTime;
}
