package io.github.seenings.chat.model;

import java.time.LocalDateTime;

/**
 * 发送请求信息
 *
 * @param mainPhotoId      主照片ID
 * @param applyId      申请单ID
 * @param name         名字
 * @param applyStatus  申请状态
 * @param applyTime    申请时间
 * @param userId       用户ID
 */
public record SendInfo(Integer mainPhotoId ,Integer applyId,  String name, Integer applyStatus, Long userId,
                       LocalDateTime applyTime
) {
}
