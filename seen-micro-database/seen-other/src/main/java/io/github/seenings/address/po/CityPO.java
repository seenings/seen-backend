package io.github.seenings.address.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 城市
 *
 */
@TableName(value ="city")
@Data
public class CityPO implements Serializable {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 城市代码
     */
    private String code;

    /**
     * 城市名
     */
    private String name;

    /**
     * 省份代码
     */
    private String provinceCode;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新用户
     */
    private Long updateUser;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
