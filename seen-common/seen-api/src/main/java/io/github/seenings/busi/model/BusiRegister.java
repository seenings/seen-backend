package io.github.seenings.busi.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户注册信息
 */
@Data
@Accessors(chain = true)
public class BusiRegister {

    /**
     * 自增ID
     */
    private Long registerId;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 注册时间
     */
    private LocalDateTime registerTime;
    /**
     * 业务ID
     */
    private Long busiId;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
