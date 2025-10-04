package io.github.seenings.apply.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * UserApplyTradePO
 *
 * @author chixuehui
 * @since 2023-03-05
 */
@TableName(value = "user_apply_trade")
@Data
@Accessors(chain = true)
public class UserApplyTradePO {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 申请单ID
     */
    private Integer applyId;

    /**
     * 交易ID
     */
    private Integer tradeId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
