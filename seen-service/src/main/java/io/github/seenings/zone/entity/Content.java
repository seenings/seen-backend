package io.github.seenings.zone.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.seenings.article.enumeration.ContentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 空间内容
 * </p>
 *
 * @author chixh
 * @since 2021-07-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("zone_content")
@Schema(description = "空间内容")
public class Content implements Serializable {

    @Schema(name = "自增ID")
    @TableId(value = "id", type = IdType.AUTO)

    private Integer id;
    @Schema(name = "空间ID")

    private Integer zoneId;
    /**
     * @see ContentType#TEXT 文本
     * @see ContentType#IMAGE 图片
     * @see ContentType#VOICE 图片
     * @see ContentType#VIDEO 视频
     */
    @Schema(name = "内容类型：1文本，2图片，3语音，4视频")
    private Integer contentTypeId;
    @Schema(name = "内容ID")

    private Integer contentId;
    @Schema(name = "修改时间")

    private LocalDateTime updateTime;
    @Schema(name = "创建时间")
    private LocalDateTime createTime;


}
