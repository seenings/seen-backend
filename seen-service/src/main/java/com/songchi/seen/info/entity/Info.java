package com.songchi.seen.info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author chixh
 * @since 2021-05-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("user_info")
@Schema(name = "Info对象", description = "用户信息")
public class Info implements Serializable {


    public static final String USER_ID = "USER_ID";
    public static final String CREATE_TIME = "CREATE_TIME";

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Long userId;
    private String name;
    @Schema(description = "1男2女")
    private Integer sex;
    private Integer profilePhotoId;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;


}
