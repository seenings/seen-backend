package com.songchi.seen.zone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 空间
 * </p>
 *
 * @author chixh
 * @since 2021-07-25
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "空间")
public class Zone implements Serializable {
    @Schema(name = "自增ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(name = "哪个用户发的空间")
    private Long userId;

    @Schema(name = "更新时间")
    private LocalDateTime updateTime;
    
    @Schema(name = "发布时间")
    private LocalDateTime publishTime;
}
