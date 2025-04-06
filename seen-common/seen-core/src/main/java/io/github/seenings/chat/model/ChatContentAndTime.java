package io.github.seenings.chat.model;

import io.github.seenings.article.enumeration.ContentType;

import java.time.LocalDateTime;

/**
 * ChatContentAndTime
 *
 * @author chixuehui
 * @since 2023-01-27
 *
 * @param contentType   内容类型
 * @param contentId 内容ID
 * @param sendTime  发送消息时间
 */
public record ChatContentAndTime(ContentType contentType, Integer contentId, LocalDateTime sendTime) {}
