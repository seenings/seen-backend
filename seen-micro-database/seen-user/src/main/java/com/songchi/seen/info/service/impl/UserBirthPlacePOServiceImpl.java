package com.songchi.seen.info.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtils;
import com.songchi.seen.info.po.UserBirthPlacePO;
import com.songchi.seen.info.service.UserBirthPlaceService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserBirthPlacePOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@Mapper
interface UserBirthPlacePOMapper extends BaseMapper<UserBirthPlacePO> {}

@Service
public class UserBirthPlacePOServiceImpl extends ServiceImpl<UserBirthPlacePOMapper, UserBirthPlacePO>
        implements UserBirthPlaceService {

    @Override
    public Map<Long, Integer> userIdToCityId(Set<Long> userIds) {
        List<Long> list = CollUtils.valueIsNullToList(userIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<UserBirthPlacePO, Integer> getValue = UserBirthPlacePO::getCityId;
        SFunction<UserBirthPlacePO, Long> getKey = UserBirthPlacePO::getUserId;
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs ->
                        list(new LambdaQueryWrapper<UserBirthPlacePO>()
                                        .in(getKey, subs)
                                        .select(getKey, getValue))
                                .stream())
                .collect(Collectors.toMap(getKey, getValue, (o1, o2) -> o2));
    }

    @Override
    public Map<Long, Integer> userIdToProvinceId(Set<Long> userIds) {
        List<Long> list = CollUtils.valueIsNullToList(userIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<UserBirthPlacePO, Integer> getValue = UserBirthPlacePO::getProvinceId;
        SFunction<UserBirthPlacePO, Long> getKey = UserBirthPlacePO::getUserId;
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs ->
                        list(new LambdaQueryWrapper<UserBirthPlacePO>()
                                        .in(getKey, subs)
                                        .select(getKey, getValue))
                                .stream())
                .collect(Collectors.toMap(getKey, getValue, (o1, o2) -> o2));
    }

    @Override
    public boolean set(Long userId, Integer provinceId, Integer cityId) {
        Integer exists = userIdToProvinceId(Collections.singleton(userId)).get(userId);
        var po = new UserBirthPlacePO()
                .setUserId(userId)
                .setProvinceId(provinceId)
                .setCityId(cityId)
                .setUpdateTime(LocalDateTime.now());
        if (exists == null) {
            return save(po);
        } else {
            return update(po, new LambdaQueryWrapper<UserBirthPlacePO>().eq(UserBirthPlacePO::getUserId, userId));
        }
    }
}
