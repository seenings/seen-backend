package io.github.seenings.info.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.core.util.CollUtil;
import io.github.seenings.info.po.UserWeightPO;
import io.github.seenings.info.service.UserWeightService;
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
    public Map<Long, Integer> userIdToWeightKg(Set<Long> userIds) {
        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<UserWeightPO, Integer> getValue = UserWeightPO::getWeightKg;
        SFunction<UserWeightPO, Long> getKey = UserWeightPO::getUserId;
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs ->
                        list(new LambdaQueryWrapper<UserWeightPO>()
                                        .in(getKey, subs)
                                        .select(getKey, getValue))
                                .stream())
                .collect(Collectors.toMap(getKey, getValue, (o1, o2) -> o2));
    }

    @Override
    public boolean set(Long userId, Integer weightKg) {
        Integer exists = userIdToWeightKg(Collections.singleton(userId)).get(userId);
        var po = new UserWeightPO().setUserId(userId).setWeightKg(weightKg).setUpdateTime(LocalDateTime.now());
        if (exists == null) {
            return save(po);
        } else {
            return update(po, new LambdaQueryWrapper<UserWeightPO>().eq(UserWeightPO::getUserId, userId));
        }
    }
}
