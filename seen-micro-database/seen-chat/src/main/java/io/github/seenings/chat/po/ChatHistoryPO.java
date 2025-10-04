package io.github.seenings.chat.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 聊天历史记录
 */
@TableName(value = "chat_history")
@Data
@Accessors(chain = true)
public class ChatHistoryPO {
    /**
     * ID
     */
    private Integer id;

    /**
     * 发送者-聊天用户
     */
    private Long fromUserId;

    /**
     * 接收者-聊天用户
     */
    private Long toUserId;

    /**
     * 内容类型：1文本，2图片，3语音，4视频
     */
    private Integer contentTypeId;

    /**
     * 聊天内容ID，根据content_type_id决定是何种内容
     */
    private Integer contentId;

    /**
     * 已发送，默认是发送true
     */
    private boolean sent;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
