package com.songchi.seen.info.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtils;
import com.songchi.seen.info.po.UserWeightPO;
import com.songchi.seen.info.service.UserWeightService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserWeightPOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@Mapper
interface UserWeightPOMapper extends BaseMapper<UserWeightPO> {}

@Service
public class UserWeightPOServiceImpl extends ServiceImpl<UserWeightPOMapper, UserWeightPO>
        implements UserWeightService {

    @Override
    public Map<Integer, Integer> userIdToWeightKg(Set<Integer> userIds) {
        List<Integer> list = CollUtils.valueIsNullToList(userIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<UserWeightPO, Integer> getValue = UserWeightPO::getWeightKg;
        SFunction<UserWeightPO, Integer> getKey = UserWeightPO::getUserId;
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs ->
                        list(new LambdaQueryWrapper<UserWeightPO>()
                                        .in(getKey, subs)
                                        .select(getKey, getValue))
                                .stream())
                .collect(Collectors.toMap(getKey, getValue, (o1, o2) -> o2));
    }

    @Override
    public boolean set(Integer userId, Integer weightKg) {
        Integer exists = userIdToWeightKg(Collections.singleton(userId)).get(userId);
        var po = new UserWeightPO().setUserId(userId).setWeightKg(weightKg).setUpdateTime(LocalDateTime.now());
        if (exists == null) {
            return save(po);
        } else {
            return update(po, new LambdaQueryWrapper<UserWeightPO>().eq(UserWeightPO::getUserId, userId));
        }
    }
}
