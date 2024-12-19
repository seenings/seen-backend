package com.songchi.seen.info.model;

import java.time.LocalDateTime;

/**
 * ApplyContent
 *
 * @author chixuehui
 * @since 2023-01-14
 */
public record SendApplyContent(
        Integer applyStatusId,
        String applyMsg,
        LocalDateTime agreeTime,
        LocalDateTime refuseTime,
        LocalDateTime lookTime,
        LocalDateTime sendTime) {}
