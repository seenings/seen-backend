package io.github.seenings.zone.util;

import cn.hutool.core.collection.CollUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;

/**
 * TagUtils标签工具类
 *
 * @author chixuehui
 * @since 2022-05-29
 */
public class TagUtils {

    /**
     * 计算标签匹配度
     *
     * @param needTagIds 需要的标签ID
     * @param destTagIds 实际的标签ID
     * @return 匹配度
     */
    public static BigDecimal calcTagMatchDegree(List<Integer> needTagIds, List<Integer> destTagIds) {

        Collection<Integer> intersectionTagIds = CollUtil.intersection(needTagIds, destTagIds);
        return BigDecimal.valueOf(intersectionTagIds.size())
                .divide(BigDecimal.valueOf(destTagIds.size()), RoundingMode.HALF_UP);
    }
}
