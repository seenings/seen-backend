package com.songchi.seen.info.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.info.po.UserBirthdayPO;
import com.songchi.seen.core.util.CollUtils;
import com.songchi.seen.info.service.UserBirthdayService;
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
    public Map<Integer, Integer> userIdToYear(Set<Integer> userIds) {
        List<Integer> list = CollUtils.valueIsNullToList(userIds);
        if (CollUtil.isEmpty(list)) {
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
    public Map<Integer, Integer> userIdToMonth(Set<Integer> userIds) {
        List<Integer> list = CollUtils.valueIsNullToList(userIds);
        if (CollUtil.isEmpty(list)) {
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
    public Map<Integer, Integer> userIdToDay(Set<Integer> userIds) {
        List<Integer> list = CollUtils.valueIsNullToList(userIds);
        if (CollUtil.isEmpty(list)) {
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
    public boolean set(Integer userId, Integer year, Integer month, Integer day) {
        Map<Integer, Integer> userIdToYearMap = userIdToYear(Collections.singleton(userId));
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
