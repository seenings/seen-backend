package com.songchi.seen.chat.model;

import java.time.LocalDateTime;

/**
 * 聊天内容记录
 *
 * @param id            聊天记录ID
 * @param contentTypeId 聊天内容类型
 * @param contentId     聊天内容ID
 * @param sendTime      发送时间
 * @param fromUserId    消息来源
 * @param toUserId      消息目标
 * @param sent          是否已发送
 */
public record ChatMessage(Integer id, Integer contentTypeId, Integer contentId,
                          Long fromUserId,
                          Long toUserId, Boolean sent, LocalDateTime sendTime) {

}
