package io.github.seenings.chat.model;

/**
 * RecInfo
 *
 * @param applyId      申请单ID
 * @param mainPhotoUrl 照片URL
 * @param name         名字
 * @param applyStatus  申请状态
 * @param userId       用户ID
 * @author chixuehui
 * @since 2023-03-05
 */
public record RecInfo(Integer applyId, String mainPhotoUrl, String name, Integer applyStatus, Long userId) {
}
