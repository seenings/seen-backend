package io.github.seenings.coin.po;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户的账户
 */

@Data
@Accessors(chain = true)
@TableName(value = "coin_account_user")
public class CoinAccountUser {

    /**
     * 账户ID
     */
    private Long accountId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
