package io.github.seenings.work.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.core.util.CollUtil;
import io.github.seenings.work.po.UserWorkPositionPO;
import io.github.seenings.info.service.UserWorkPositionService;
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
    public Map<Long, Integer> userIdToPosition(Set<Long> userIds) {
        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<UserWorkPositionPO, Integer> getValue = UserWorkPositionPO::getPosition;
        SFunction<UserWorkPositionPO, Long> getKey = UserWorkPositionPO::getUserId;
        return ListUtil.partition(list, 500).stream()
                .flatMap(
                        subs -> list(new LambdaQueryWrapper<UserWorkPositionPO>()
                                        .in(getKey, subs)
                                        .select(getKey, getValue))
                                .stream())
                .collect(Collectors.toMap(getKey, getValue, (o1, o2) -> o2));
    }

    @Override
    public boolean set(Long userId, Integer position) {
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
