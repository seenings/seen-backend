package io.github.seenings.chat.model;

/**
 * 接收信息
 *
 * @param mainPhotoId 主照片ID
 * @param applyId     申请单ID
 * @param name        名字
 * @param applyStatus 申请状态
 * @param userId      用户ID
 */
public record RecInfo(Integer mainPhotoId,
                      Integer applyId, String name, Integer applyStatus, Long userId) {
}
