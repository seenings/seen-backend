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
import com.songchi.seen.core.util.CollUtils;
import com.songchi.seen.recommend.po.MiddleUserRecommendPO;

import cn.hutool.core.collection.CollUtil;
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
    public Set<Integer> haveUserId(Integer userId, Set<Integer> recommendUserIds) {
        if (CollUtil.isEmpty(recommendUserIds)) {
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
    public Map<Integer, List<Integer>> userIdToRecommendUserId(Set<Integer> userIds, String date) {
        List<Integer> list = CollUtils.valueIsNullToList(userIds);
        if (CollUtil.isEmpty(list)) {
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
    public int set(Integer userId, String date, List<Integer> recommendUserIds) {
        List<Integer> exists = userIdToRecommendUserId(Collections.singleton(userId), date).get(userId);
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
                    .reduce((o1, o2) -> o1 + o2)
                    .orElse(0);
        } else {
            return 0;
        }
    }
}
