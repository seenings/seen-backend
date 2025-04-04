package com.songchi.seen.info.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtil;
import com.songchi.seen.info.po.UserStaturePO;
import com.songchi.seen.info.service.UserStatureService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserStaturePOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@Mapper
interface UserStaturePOMapper extends BaseMapper<UserStaturePO> {}

@Service
public class UserStaturePOServiceImpl extends ServiceImpl<UserStaturePOMapper, UserStaturePO>
        implements UserStatureService {

    @Override
    public Map<Long, Integer> userIdToStatureCm(Set<Long> userIds) {
        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<UserStaturePO, Integer> getValue = UserStaturePO::getStatureCm;
        SFunction<UserStaturePO, Long> getKey = UserStaturePO::getUserId;
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs ->
                        list(new LambdaQueryWrapper<UserStaturePO>()
                                        .in(getKey, subs)
                                        .select(getKey, getValue))
                                .stream())
                .collect(Collectors.toMap(getKey, getValue, (o1, o2) -> o2));
    }

    @Override
    public boolean set(Long userId, Integer statureCm) {
        Integer exists = userIdToStatureCm(Collections.singleton(userId)).get(userId);
        var po = new UserStaturePO().setUserId(userId).setStatureCm(statureCm).setUpdateTime(LocalDateTime.now());
        if (exists == null) {
            return save(po);
        } else {
            return update(po, new LambdaQueryWrapper<UserStaturePO>().eq(UserStaturePO::getUserId, userId));
        }
    }
}
