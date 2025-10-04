package io.github.seenings.school.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 学校信息
 */
@TableName(value = "school")
@Data
@Accessors(chain = true)
public class SchoolPO {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 学校全名
     */
    private String schoolName;

    /**
     * 学校所属城市的id，对应province的code字段
     */
    private String areaId;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新用户
     */
    private Long updateUser;
}
