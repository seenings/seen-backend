package io.github.seenings.chat.util;

import io.github.seenings.chat.enumeration.ApplyStatus;

import java.util.Arrays;
import java.util.Objects;

/**
 * ChatEnumUtils
 *
 * @author chixuehui
 * @since 2023-03-05
 */
public class ChatEnumUtils {

    /**
     * 根据索引获取枚举，申请状态
     *
     * @param index 索引
     * @return 枚举
     */
    public static ApplyStatus indexToApplyStatusEnum(Integer index) {
        return Arrays.stream(ApplyStatus.values())
                .filter(n -> Objects.equals(index, n.getIndex()))
                .findFirst()
                .orElse(null);
    }
}
