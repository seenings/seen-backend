package com.songchi.seen.address.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.address.po.CityPO;
import com.songchi.seen.address.service.CityService;
import com.songchi.seen.core.util.CollUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CityPOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@Mapper
interface CityPOMapper extends BaseMapper<CityPO> {}

@Service
public class CityPOServiceImpl extends ServiceImpl<CityPOMapper, CityPO> implements CityService {

    @Override
    public Map<Integer, String> idToName(Set<Integer> ids) {
        List<Integer> list = CollUtils.valueIsNullToList(ids);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<CityPO, String> getValue = CityPO::getName;
        SFunction<CityPO, Integer> getKey = CityPO::getId;
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs ->
                        list(new LambdaQueryWrapper<CityPO>().in(getKey, subs).select(getKey, getValue)).stream())
                .collect(Collectors.toMap(getKey, getValue, (o1, o2) -> o2));
    }

    @Override
    public Map<String, String> codeToName(Set<String> codes) {
        List<String> list = CollUtils.valueIsNullToList(codes);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<CityPO, String> getValue = CityPO::getName;
        SFunction<CityPO, String> getKey = CityPO::getCode;
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs ->
                        list(new LambdaQueryWrapper<CityPO>().in(getKey, subs).select(getKey, getValue)).stream())
                .collect(Collectors.toMap(getKey, getValue));
    }

    @Override
    public Map<String, List<String>> provinceCodeToCode(Set<String> provinceCodes) {
        List<String> list = CollUtils.valueIsNullToList(provinceCodes);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<CityPO, String> getValue = CityPO::getCode;
        SFunction<CityPO, String> getKey = CityPO::getProvinceCode;
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs ->
                        list(new LambdaQueryWrapper<CityPO>().in(getKey, subs).select(getKey, getValue)).stream())
                .collect(Collectors.groupingBy(
                        CityPO::getProvinceCode, Collectors.mapping(CityPO::getCode, Collectors.toList())));
    }

    @Override
    public Map<String, Integer> cityCodeToCityId(Set<String> cityCodes) {
        List<String> list = CollUtils.valueIsNullToList(cityCodes);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<CityPO>()
                                .in(CityPO::getCode, subs)
                                .select(CityPO::getId, CityPO::getCode))
                        .stream())
                .collect(Collectors.toMap(CityPO::getCode, CityPO::getId));
    }
}
