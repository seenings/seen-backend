package io.github.seenings.info.model;

import io.github.seenings.info.enumeration.Sex;

/**
 * 用户信息
 *
 * @param userId      用户ID
 * @param aliasName   用户昵称
 * @param sex         1男2女
 * @param mainPhotoId 头像照片ID
 */
public record UserInfo(Long userId, String aliasName, Sex sex, Integer mainPhotoId) {
}
