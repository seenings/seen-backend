package com.songchi.seen.recommend.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtil;
import com.songchi.seen.recommend.po.MiddleUserRecommendPO;

import cn.hutool.core.collection.ListUtil;

/**
 * MiddleUserRecommendPOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-23
 */
@Mapper
interface MiddleUserRecommendPOMapper extends BaseMapper<MiddleUserRecommendPO> {
}

@Service
public class MiddleUserRecommendPOServiceImpl extends ServiceImpl<MiddleUserRecommendPOMapper, MiddleUserRecommendPO>
        implements MiddleUserRecommendService {

    @Override
    public Set<Long> haveUserId(Long userId, Set<Long> recommendUserIds) {
        if (cn.hutool.core.collection.CollUtil.isEmpty(recommendUserIds)) {
            return Collections.emptySet();
        }
        return list(new QueryWrapper<MiddleUserRecommendPO>()
                .lambda()
                .eq(MiddleUserRecommendPO::getUserId, userId)
                .in(MiddleUserRecommendPO::getRecommendUserId, recommendUserIds)
                .select(MiddleUserRecommendPO::getRecommendUserId))
                .stream()
                .parallel()
                .map(MiddleUserRecommendPO::getRecommendUserId)
                .collect(Collectors.toSet());
    }

    @Override
    public Map<Long, List<Long>> userIdToRecommendUserId(Set<Long> userIds, String date) {
        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<MiddleUserRecommendPO>()
                        .in(MiddleUserRecommendPO::getUserId, subs)
                        .eq(MiddleUserRecommendPO::getDate, date)
                        .select(MiddleUserRecommendPO::getUserId, MiddleUserRecommendPO::getRecommendUserId))
                        .stream())
                .collect(Collectors.groupingBy(
                        MiddleUserRecommendPO::getUserId,
                        Collectors.mapping(MiddleUserRecommendPO::getRecommendUserId, Collectors.toList())));
    }

    @Override
    public int set(Long userId, String date, List<Long> recommendUserIds) {
        List<Long> exists = userIdToRecommendUserId(Collections.singleton(userId), date).get(userId);
        if (exists == null) {
            return recommendUserIds.stream()
                    .map(n -> {
                        MiddleUserRecommendPO po = new MiddleUserRecommendPO()
                                .setRecommendUserId(n)
                                .setUserId(userId)
                                .setDate(date);
                        return save(po);
                    })
                    .map(n -> n ? 1 : 0)
                    .reduce(Integer::sum)
                    .orElse(0);
        } else {
            return 0;
        }
    }
}
