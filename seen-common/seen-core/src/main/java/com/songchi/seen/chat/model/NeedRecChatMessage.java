package com.songchi.seen.chat.model;

import java.time.LocalDateTime;

/**
 * 需要接收的消息
 * 
 * @param sendTime 发送时间，指服务器接受到消息的时间
 */
public record NeedRecChatMessage(
        Long userIdOfSender,
        Integer contentTypeId,
        Integer contentId, LocalDateTime sendTime) {
}
