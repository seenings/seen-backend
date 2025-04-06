package io.github.seenings.introduce.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * UserIntroducePO
 */
@Data
@Accessors(chain = true)
@TableName(value = "user_introduce_photo")
public class UserIntroducePhotoPO {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private Long userId;
    private Integer introduceType;
    /**
     * 顺序
     */
    private Integer orderNum;
    private LocalDateTime updateTime;
}
