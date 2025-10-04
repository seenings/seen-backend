package io.github.seenings.recommend.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 用户推荐列表
 */
@TableName(value = "middle_user_recommend")
@Data
@Accessors(chain = true)
public class MiddleUserRecommendPO {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 给这个用户ID推荐
     */
    private Long userId;

    /**
     * 日期YYYYMMDD格式
     */
    private String date;

    /**
     * 推荐的用户ID
     */
    private Long recommendUserId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
