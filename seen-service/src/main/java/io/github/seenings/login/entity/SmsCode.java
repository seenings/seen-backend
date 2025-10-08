package io.github.seenings.login.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 短信验证码列表
 * </p>
 *
 * @author chixh
 * @since 2021-05-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sms_code")
@Schema(name = "SmsCode对象", description = "短信验证码列表")
public class SmsCode implements Serializable {

    @Schema(name = "ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Schema(name = "手机号码")
    @TableField("phone")
    private String phone;

    @Schema(name = "短信验证码id")
    @TableField("sms_id")

    private Integer smsId;
    @Schema(name = "短信验证码")
    @TableField("sms_code")
    private Integer smsCode;

    @Schema(name = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;


}
