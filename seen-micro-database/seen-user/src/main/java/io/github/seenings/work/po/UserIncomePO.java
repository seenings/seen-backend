package io.github.seenings.work.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * UserIncomePO
 *
 * @author chixuehui
 * @since 2023-02-11
 */
@Data
@Accessors(chain = true)
@TableName(value = "user_income")
public class UserIncomePO {
    /**
     * 自增ID
     */
    private Integer id;

    private Long userId;

    private Integer annualIncome;

    private LocalDateTime updateTime;
}
