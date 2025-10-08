package io.github.seenings.chat.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 可以聊天的用户列表
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@TableName("chat_user")
@Data
@Accessors(chain = true)
public class ChatUserPO {

    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 好友ID
     */
    private Long friendUserId;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
