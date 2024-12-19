package com.songchi.seen.address.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.address.po.ProvincePO;
import com.songchi.seen.address.service.ProvinceService;
import com.songchi.seen.core.util.CollUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ProvincePOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@Mapper
interface ProvincePOMapper extends BaseMapper<ProvincePO> {}

@Service
public class ProvincePOServiceImpl extends ServiceImpl<ProvincePOMapper, ProvincePO> implements ProvinceService {

    @Override
    public Map<Integer, String> idToName(Set<Integer> ids) {
        List<Integer> list = CollUtils.valueIsNullToList(ids);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<ProvincePO, String> getValue = ProvincePO::getName;
        SFunction<ProvincePO, Integer> getKey = ProvincePO::getId;
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs ->
                        list(new LambdaQueryWrapper<ProvincePO>()
                                        .in(getKey, subs)
                                        .select(getKey, getValue))
                                .stream())
                .collect(Collectors.toMap(getKey, getValue, (o1, o2) -> o2));
    }

    @Override
    public Map<String, Integer> provinceCodeToProvinceId(Set<String> provinceCodes) {
        List<String> list = CollUtils.valueIsNullToList(provinceCodes);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<ProvincePO>()
                                .in(ProvincePO::getCode, subs)
                                .select(ProvincePO::getCode, ProvincePO::getId))
                        .stream())
                .collect(Collectors.toMap(ProvincePO::getCode, ProvincePO::getId));
    }

    @Override
    public List<Map.Entry<String, String>> listAll() {
        List<ProvincePO> list = list(new QueryWrapper<ProvincePO>()
                .lambda()
                .select(ProvincePO::getCode, ProvincePO::getName)
                .orderByAsc(ProvincePO::getCode));
        return list.stream().map(n -> Map.entry(n.getCode(), n.getName())).collect(Collectors.toList());
    }
}
