package io.github.seenings.info.model;

import java.time.LocalDateTime;

/**
 * ApplyContent
 *
 * @author chixuehui
 * @since 2023-01-14
 */
public record RecApplyContent(
        Integer applyStatusId,
        String applyMsg,
        LocalDateTime agreeTime,
        LocalDateTime refuseTime,
        LocalDateTime lookTime,
        LocalDateTime sendTime) {}
