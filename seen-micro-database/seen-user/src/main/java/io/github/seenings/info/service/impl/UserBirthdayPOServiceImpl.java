package io.github.seenings.info.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.info.po.UserBirthdayPO;
import io.github.seenings.core.util.CollUtil;
import io.github.seenings.info.service.UserBirthdayService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserBirthdayPOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-07
 */
@Mapper
interface UserBirthdayPOMapper extends BaseMapper<UserBirthdayPO> {}

@Service
public class UserBirthdayPOServiceImpl extends ServiceImpl<UserBirthdayPOMapper, UserBirthdayPO>
        implements UserBirthdayService {

    @Override
    public Map<Long, Integer> userIdToYear(Set<Long> userIds) {
        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserBirthdayPO>()
                                .in(UserBirthdayPO::getUserId, subs)
                                .select(UserBirthdayPO::getUserId, UserBirthdayPO::getYear))
                        .stream())
                .collect(Collectors.toMap(UserBirthdayPO::getUserId, UserBirthdayPO::getYear, (o1, o2) -> o2));
    }

    @Override
    public Map<Long, Integer> userIdToMonth(Set<Long> userIds) {
        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserBirthdayPO>()
                                .in(UserBirthdayPO::getUserId, subs)
                                .select(UserBirthdayPO::getUserId, UserBirthdayPO::getMonth))
                        .stream())
                .filter(n -> n.getMonth() != null)
                .collect(Collectors.toMap(UserBirthdayPO::getUserId, UserBirthdayPO::getMonth, (o1, o2) -> o2));
    }

    @Override
    public Map<Long, Integer> userIdToDay(Set<Long> userIds) {
        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserBirthdayPO>()
                                .in(UserBirthdayPO::getUserId, subs)
                                .select(UserBirthdayPO::getUserId, UserBirthdayPO::getDay))
                        .stream())
                .filter(n -> n.getDay() != null)
                .collect(Collectors.toMap(UserBirthdayPO::getUserId, UserBirthdayPO::getDay, (o1, o2) -> o2));
    }

    @Override
    public boolean set(Long userId, Integer year, Integer month, Integer day) {
        Map<Long, Integer> userIdToYearMap = userIdToYear(Collections.singleton(userId));
        Integer existsYear = userIdToYearMap.get(userId);
        UserBirthdayPO po = new UserBirthdayPO()
                .setUserId(userId)
                .setYear(year)
                .setMonth(month)
                .setDay(day)
                .setUpdateTime(LocalDateTime.now());
        if (existsYear == null) {
            return save(po);
        } else {
            return update(po, new LambdaQueryWrapper<UserBirthdayPO>().eq(UserBirthdayPO::getUserId, userId));
        }
    }
}
