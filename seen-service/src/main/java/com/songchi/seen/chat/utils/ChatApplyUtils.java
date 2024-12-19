package com.songchi.seen.chat.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.songchi.seen.chat.constant.ChatConstant;
import com.songchi.seen.chat.enumeration.ApplyStatus;

/**
 * ChatApplyUtils
 *
 * @author chixuehui
 * @since 2023-03-11
 */
public class ChatApplyUtils {

    /**
     * 获取申请状态
     * @param agreeTime 同意时间
     * @param refuseTime    拒绝时间
     * @param applyTime 申请时间
     * @param lookTime  查看时间
     * @param now   现在
     * @return  申请状态
     */
    public static ApplyStatus toApplyStatus(LocalDateTime agreeTime, LocalDateTime refuseTime,
                                            LocalDateTime applyTime, LocalDateTime lookTime
            , LocalDateTime now
    ) {

        if (agreeTime != null) {
            return ApplyStatus.AGREE;
        }
        if (refuseTime != null) {
            return ApplyStatus.REFUSE;
        }
        if (applyTime.plus(ChatConstant.EXPIRE_DAYS, ChronoUnit.DAYS).isBefore(now)) {
            return ApplyStatus.EXPIRE;
        }
        if (lookTime != null) {
            return ApplyStatus.LOOK;
        }
        return ApplyStatus.NONE;
    }
}
