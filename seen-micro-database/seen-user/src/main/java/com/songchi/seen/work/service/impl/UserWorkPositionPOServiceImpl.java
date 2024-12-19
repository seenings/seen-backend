package com.songchi.seen.work.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtils;
import com.songchi.seen.work.po.UserWorkPositionPO;
import com.songchi.seen.info.service.UserWorkPositionService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserWorkPositionPOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@Mapper
interface UserWorkPositionPOMapper extends BaseMapper<UserWorkPositionPO> {}

@Service
public class UserWorkPositionPOServiceImpl extends ServiceImpl<UserWorkPositionPOMapper, UserWorkPositionPO>
        implements UserWorkPositionService {

    @Override
    public Map<Integer, Integer> userIdToPosition(Set<Integer> userIds) {
        List<Integer> list = CollUtils.valueIsNullToList(userIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<UserWorkPositionPO, Integer> getValue = UserWorkPositionPO::getPosition;
        SFunction<UserWorkPositionPO, Integer> getKey = UserWorkPositionPO::getUserId;
        return ListUtil.partition(list, 500).stream()
                .flatMap(
                        subs -> list(new LambdaQueryWrapper<UserWorkPositionPO>()
                                        .in(getKey, subs)
                                        .select(getKey, getValue))
                                .stream())
                .collect(Collectors.toMap(getKey, getValue, (o1, o2) -> o2));
    }

    @Override
    public boolean set(Integer userId, Integer position) {
        Integer exists = userIdToPosition(Collections.singleton(userId)).get(userId);
        var po =
                new UserWorkPositionPO().setUserId(userId).setPosition(position).setUpdateTime(LocalDateTime.now());
        if (exists == null) {
            return save(po);
        } else {
            return update(po, new LambdaQueryWrapper<UserWorkPositionPO>().eq(UserWorkPositionPO::getUserId, userId));
        }
    }
}
