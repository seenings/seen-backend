package io.github.seenings.voice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 语音
 * </p>
 *
 * @author chixh
 * @since 2021-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(name = "Voice对象", description = "语音")
public class Voice implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String path;
    private Integer deleted;
    private LocalDateTime updateTime;
    private Long updateUser;


}
