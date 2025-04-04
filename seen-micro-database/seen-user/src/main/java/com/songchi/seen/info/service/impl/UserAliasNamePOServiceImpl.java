package com.songchi.seen.info.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtil;
import com.songchi.seen.info.po.UserAliasNamePO;
import com.songchi.seen.info.service.UserAliasNameService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserAliasNamePOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@Mapper
interface UserAliasNamePOMapper extends BaseMapper<UserAliasNamePO> {}

@Service
public class UserAliasNamePOServiceImpl extends ServiceImpl<UserAliasNamePOMapper, UserAliasNamePO>
        implements UserAliasNameService {

    @Override
    public Map<Long, String> userIdToAliasName(Set<Long> userIds) {
        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<UserAliasNamePO, String> getValue = UserAliasNamePO::getAliasName;
        SFunction<UserAliasNamePO, Long> getKey = UserAliasNamePO::getUserId;
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs ->
                        list(new LambdaQueryWrapper<UserAliasNamePO>()
                                        .in(getKey, subs)
                                        .select(getKey, getValue))
                                .stream())
                .collect(Collectors.toMap(getKey, getValue, (o1, o2) -> o2));
    }

    @Override
    public boolean set(Long userId, String aliasName) {
        Map<Long, String> userIdToValueMap = userIdToAliasName(Collections.singleton(userId));
        String exists = userIdToValueMap.get(userId);
        UserAliasNamePO po =
                new UserAliasNamePO().setUserId(userId).setAliasName(aliasName).setUpdateTime(LocalDateTime.now());
        if (exists == null) {
            return save(po);
        } else {
            return update(po, new LambdaQueryWrapper<UserAliasNamePO>().eq(UserAliasNamePO::getUserId, userId));
        }
    }
}
