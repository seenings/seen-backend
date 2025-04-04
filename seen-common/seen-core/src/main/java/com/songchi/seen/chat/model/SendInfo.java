package com.songchi.seen.chat.model;

import java.time.LocalDateTime;

/**
 * 发送请求信息
 *
 * @param applyId      申请单ID
 * @param mainPhotoUrl 照片URL
 * @param name         名字
 * @param applyStatus  申请状态
 * @param applyTime    申请时间
 * @param userId       用户ID
 * @author chixuehui
 * @since 2023-03-11
 */
public record SendInfo(Integer applyId, String mainPhotoUrl, String name, Integer applyStatus, Long userId,
                       LocalDateTime applyTime
) {
}
