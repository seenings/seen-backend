package io.github.seenings.info.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户的出生年月日
 *
 * @author chixuehui
 * @since 2022-10-07
 */
@Data
@Accessors(chain = true)
@TableName(value = "user_birthday")
public class UserBirthdayPO {
    /**
     * 自增ID
     */
    private Integer id;

    private Long userId;

    private Integer year;

    private Integer month;

    private Integer day;

    private LocalDateTime updateTime;
}
