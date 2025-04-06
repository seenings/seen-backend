package io.github.seenings.chat.model;


import java.time.LocalDateTime;

/**
 * ChatUser
 *
 * @author chixuehui
 * @since 2023-01-01
 */
public record ChatUser(Integer id, Long userId, Long friendUserId, LocalDateTime updateTime) {}
