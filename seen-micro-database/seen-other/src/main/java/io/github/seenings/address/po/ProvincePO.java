package io.github.seenings.address.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import lombok.Data;

/**
 * 省份
 *
 */
@TableName(value ="province")
@Data
public class ProvincePO  {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 省份代码
     */
    private String code;

    /**
     * 省份名称
     */
    private String name;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新用户
     */
    private Long updateUser;
}
