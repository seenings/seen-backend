package com.songchi.seen.info.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtil;
import com.songchi.seen.info.po.UserCurrentResidencePO;
import com.songchi.seen.info.service.UserCurrentResidenceService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserCurrentResidencePOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@Mapper
interface UserCurrentResidencePOMapper extends BaseMapper<UserCurrentResidencePO> {}

@Service
public class UserCurrentResidencePOServiceImpl extends ServiceImpl<UserCurrentResidencePOMapper, UserCurrentResidencePO>
        implements UserCurrentResidenceService {

    @Override
    public List<Long> currentResidenceCityToUserId(Integer cityId, int current, int size) {
        Page<UserCurrentResidencePO> page = page(
                new Page<>(current, size),
                new QueryWrapper<UserCurrentResidencePO>()
                        .lambda()
                        .eq(UserCurrentResidencePO::getCityId, cityId)
                        .select(UserCurrentResidencePO::getUserId));
        return page.getRecords().stream()
                .parallel()
                .map(UserCurrentResidencePO::getUserId)
                .collect(Collectors.toList());
    }

    @Override
    public Map<Long, Integer> userIdToCityId(Set<Long> userIds) {
        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<UserCurrentResidencePO, Integer> getValue = UserCurrentResidencePO::getCityId;
        SFunction<UserCurrentResidencePO, Long> getKey = UserCurrentResidencePO::getUserId;
        return ListUtil.partition(list, 500).stream()
                .flatMap(
                        subs -> list(new LambdaQueryWrapper<UserCurrentResidencePO>()
                                        .in(getKey, subs)
                                        .select(getKey, getValue))
                                .stream())
                .collect(Collectors.toMap(getKey, getValue, (o1, o2) -> o2));
    }

    @Override
    public Map<Long, Integer> userIdToProvinceId(Set<Long> userIds) {
        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<UserCurrentResidencePO, Integer> getValue = UserCurrentResidencePO::getProvinceId;
        SFunction<UserCurrentResidencePO, Long> getKey = UserCurrentResidencePO::getUserId;
        return ListUtil.partition(list, 500).stream()
                .flatMap(
                        subs -> list(new LambdaQueryWrapper<UserCurrentResidencePO>()
                                        .in(getKey, subs)
                                        .select(getKey, getValue))
                                .stream())
                .collect(Collectors.toMap(getKey, getValue, (o1, o2) -> o2));
    }

    @Override
    public boolean set(Long userId, Integer provinceId, Integer cityId) {
        Integer exists = userIdToProvinceId(Collections.singleton(userId)).get(userId);
        var po = new UserCurrentResidencePO()
                .setUserId(userId)
                .setProvinceId(provinceId)
                .setCityId(cityId)
                .setUpdateTime(LocalDateTime.now());
        if (exists == null) {
            return save(po);
        } else {
            return update(
                    po, new LambdaQueryWrapper<UserCurrentResidencePO>().eq(UserCurrentResidencePO::getUserId, userId));
        }
    }
}
