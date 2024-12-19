package com.songchi.seen.info.model;

import com.songchi.seen.info.enumeration.Sex;

/**
 * 用户信息
 * 
 * @param userId      用户ID
 * @param aliasName   用户昵称
 * @param sex         1男2女
 * @param mainPhotoId 头像照片ID
 */
public record UserInfo(Integer userId, String aliasName, Sex sex, Integer mainPhotoId) {
}
