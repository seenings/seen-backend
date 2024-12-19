package com.songchi.seen.chat.model;


import java.time.LocalDateTime;

/**
 * ChatUser
 *
 * @author chixuehui
 * @since 2023-01-01
 */
public record ChatUser(Integer id, Integer userId, Integer friendUserId, LocalDateTime updateTime) {}
