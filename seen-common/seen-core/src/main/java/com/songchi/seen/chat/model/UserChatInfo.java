package com.songchi.seen.chat.model;

import java.time.LocalDateTime;

import com.songchi.seen.info.enumeration.Sex;

/**
 * 用户聊天信息
 *
 *
 * @param userId         用户ID
 * @param aliasName      用户昵称
 * @param sex            1男2女
 * @param mainPhotoId    头像照片ID
 * @param newestChatText 最新的聊天文本
 * @param newestChatTime 最新的聊天发送时间
 */
public record UserChatInfo(
        Integer userId,
        String aliasName,
        Sex sex,
        Integer mainPhotoId,
        String newestChatText,
        LocalDateTime newestChatTime) {
}
