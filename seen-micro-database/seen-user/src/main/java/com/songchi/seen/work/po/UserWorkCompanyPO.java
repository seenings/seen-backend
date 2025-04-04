package com.songchi.seen.work.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户的工作公司
 */
@TableName(value = "user_work_company")
@Data
@Accessors(chain = true)
public class UserWorkCompanyPO {
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
     * 公司名
     */
    private String companyName;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
