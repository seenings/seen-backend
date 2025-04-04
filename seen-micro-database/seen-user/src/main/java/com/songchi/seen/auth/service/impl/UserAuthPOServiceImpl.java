package com.songchi.seen.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtils;
import com.songchi.seen.auth.po.UserAuthPO;
import com.songchi.seen.info.service.UserAuthService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserAuthPOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@Mapper
interface UserAuthPOMapper extends BaseMapper<UserAuthPO> {}

@Service
public class UserAuthPOServiceImpl extends ServiceImpl<UserAuthPOMapper, UserAuthPO> implements UserAuthService {

    @Override
    public Map<Long, Integer> userIdToUserAuth(Set<Long> userIds) {
        List<Long> list = CollUtils.valueIsNullToList(userIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<UserAuthPO, Integer> getValue = UserAuthPO::getAuthStatus;
        SFunction<UserAuthPO, Long> getKey = UserAuthPO::getUserId;
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs ->
                        list(new LambdaQueryWrapper<UserAuthPO>()
                                        .in(getKey, subs)
                                        .select(getKey, getValue))
                                .stream())
                .collect(Collectors.toMap(getKey, getValue, (o1, o2) -> o2));
    }

    @Override
    public boolean set(Long userId, Integer authStatus) {
        Integer exists = userIdToUserAuth(Collections.singleton(userId)).get(userId);
        var po = new UserAuthPO().setUserId(userId).setAuthStatus(authStatus).setUpdateTime(LocalDateTime.now());
        if (exists == null) {
            return save(po);
        } else {
            return update(po, new LambdaQueryWrapper<UserAuthPO>().eq(UserAuthPO::getUserId, userId));
        }
    }
}
