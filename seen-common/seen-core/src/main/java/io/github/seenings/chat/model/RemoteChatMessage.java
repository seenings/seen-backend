package io.github.seenings.chat.model;

import java.time.LocalDateTime;


/**
 * 表示远程聊天信息的记录类型
 *
 * @param id            记录的唯一标识符
 * @param contentTypeId 聊天信息的内容类型标识符
 * @param contentId     聊天信息的内容标识符
 * @param fromUserId    发送方用户的标识符
 * @param sendTime      聊天信息发送时间
 * @param sent          是否已发送
 * @param toUserId      接收方用户的标识符
 */
public record RemoteChatMessage(
        int id,
        int contentTypeId,
        int contentId,
        Long fromUserId,
        LocalDateTime sendTime,
        boolean sent,
        Long toUserId
) {
}
