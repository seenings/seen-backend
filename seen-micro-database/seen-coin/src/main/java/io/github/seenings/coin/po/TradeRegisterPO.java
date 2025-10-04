package io.github.seenings.coin.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * TradeRegister
 *
 * @author chixuehui
 * @since 2023-02-19
 */
@TableName(value = "trade_register")
@Data
public class TradeRegisterPO {

    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 注册时间
     */
    private LocalDateTime registerTime;
    /**
     * 交易ID
     */
    private Integer tradeId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
