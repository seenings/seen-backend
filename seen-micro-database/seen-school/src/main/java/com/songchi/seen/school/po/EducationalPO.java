package com.songchi.seen.school.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 学历
 *
 * @author chixuehui
 * @since 2022-10-06
 */
@Data
@Accessors(chain = true)
@TableName(value = "educational")
public class EducationalPO {

    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 学历（0：其他，1：大专，2：本科，3：硕士，4：博士）
     */
    private Integer educational;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
