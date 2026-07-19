package io.github.seenings.auth.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import io.github.seenings.core.util.CollUtil;
import io.github.seenings.auth.po.UserAuthPO;
import io.github.seenings.info.service.UserAuthService;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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

@AllArgsConstructor
@Repository
public class UserAuthPOServiceImpl  implements UserAuthService {
private UserAuthPOMapper userAuthPOMapper;
    @Override
    public Map<Long, Integer> userIdToUserAuth(Set<Long> userIds) {
        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<UserAuthPO, Integer> getValue = UserAuthPO::getAuthStatus;
        SFunction<UserAuthPO, Long> getKey = UserAuthPO::getUserId;
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs ->
                        userAuthPOMapper.selectList(new LambdaQueryWrapper<UserAuthPO>()
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
            return userAuthPOMapper.insert(po)>0;
        } else {
            return userAuthPOMapper.update(po, new LambdaQueryWrapper<UserAuthPO>().eq(UserAuthPO::getUserId, userId))>0;
        }
    }
}
