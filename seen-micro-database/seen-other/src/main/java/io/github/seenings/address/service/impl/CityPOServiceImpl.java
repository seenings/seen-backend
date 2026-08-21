package io.github.seenings.address.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import io.github.seenings.address.po.CityPO;
import io.github.seenings.address.service.CityService;
import io.github.seenings.core.util.CollUtil;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
interface CityPOMapper extends BaseMapper<CityPO> {
}

@AllArgsConstructor
@Repository
public class CityPOServiceImpl implements CityService {

    private CityPOMapper cityPOMapper;

    @Override
    public Map<Integer, String> idToName(Set<Integer> ids) {
        List<Integer> list = CollUtil.valueIsNullToList(ids);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<CityPO, String> getValue = CityPO::getName;
        SFunction<CityPO, Integer> getKey = CityPO::getId;
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs ->
                        cityPOMapper.selectList(new LambdaQueryWrapper<CityPO>().in(getKey, subs).select(getKey, getValue)).stream())
                .collect(Collectors.toMap(getKey, getValue, (o1, o2) -> o2));
    }

    @Override
    public Map<String, String> codeToName(Set<String> codes) {
        List<String> list = CollUtil.valueIsNullToList(codes);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<CityPO, String> getValue = CityPO::getName;
        SFunction<CityPO, String> getKey = CityPO::getCode;
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs ->
                        cityPOMapper.selectList(new LambdaQueryWrapper<CityPO>().in(getKey, subs).select(getKey, getValue)).stream())
                .collect(Collectors.toMap(getKey, getValue));
    }

    @Override
    public Map<String, List<String>> provinceCodeToCode(Set<String> provinceCodes) {
        List<String> list = CollUtil.valueIsNullToList(provinceCodes);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<CityPO, String> getValue = CityPO::getCode;
        SFunction<CityPO, String> getKey = CityPO::getProvinceCode;
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs ->
                        cityPOMapper.selectList(new LambdaQueryWrapper<CityPO>().in(getKey, subs).select(getKey, getValue)).stream())
                .collect(Collectors.groupingBy(
                        CityPO::getProvinceCode, Collectors.mapping(CityPO::getCode, Collectors.toList())));
    }

    /// 获取城市名称
    ///
    /// @param cityNames 城市名称
    /// @return 城市名称
    @Override
    public Set<String> toCityName(Set<String> cityNames) {
        SFunction<CityPO, String> getKey = CityPO::getName;
        return ListUtil.partition(cn.hutool.core.collection.CollUtil.toList(cityNames), 500).stream()
                .flatMap(subs ->
                        cityPOMapper.selectList(new LambdaQueryWrapper<CityPO>()
                                .in(getKey, subs).select(getKey)).stream())
                .map(getKey)
                .collect(Collectors.toSet());
    }

    @Override
    public Map<String, Integer> cityCodeToCityId(Set<String> cityCodes) {
        List<String> list = CollUtil.valueIsNullToList(cityCodes);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> cityPOMapper.selectList(new LambdaQueryWrapper<CityPO>()
                                .in(CityPO::getCode, subs)
                                .select(CityPO::getId, CityPO::getCode))
                        .stream())
                .collect(Collectors.toMap(CityPO::getCode, CityPO::getId));
    }

    /// 根据城市名获取省会代码
    ///
    /// @param cityNames 城市名
    /// @return 城市名对应省会代码
    @Override
    public Map<String, String> cityNameToProvinceCode(Set<String> cityNames) {
        return ListUtil.partition(cn.hutool.core.collection.CollUtil.toList(cityNames), 500).stream()
                .flatMap(subs -> cityPOMapper.selectList(new LambdaQueryWrapper<CityPO>()
                                .in(CityPO::getName, subs)
                                .select(CityPO::getName, CityPO::getProvinceCode))
                        .stream())
                .collect(Collectors.toMap(CityPO::getName, CityPO::getProvinceCode));
    }
}
