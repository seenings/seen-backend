package io.github.seenings.zone.entity;

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
 * 空间回复
 * </p>
 *
 * @author chixh
 * @since 2021-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("zone_reply")
@Schema(name = "Reply对象", description = "空间回复")
public class Reply implements Serializable {


    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer zoneId;
    private Long userId;
    private Integer replyId;
    private Integer zoneContentId;
    private LocalDateTime updateTime;


}
