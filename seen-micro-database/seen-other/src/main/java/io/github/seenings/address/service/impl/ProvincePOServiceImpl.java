package io.github.seenings.address.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import io.github.seenings.address.po.ProvincePO;
import io.github.seenings.address.service.ProvinceService;
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
 * ProvincePOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@Mapper
interface ProvincePOMapper extends BaseMapper<ProvincePO> {
}

@AllArgsConstructor
@Repository
public class ProvincePOServiceImpl implements ProvinceService {

    private ProvincePOMapper provincePOMapper;

    @Override
    public Map<Integer, String> idToName(Set<Integer> ids) {
        List<Integer> list = CollUtil.valueIsNullToList(ids);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<ProvincePO, String> getValue = ProvincePO::getName;
        SFunction<ProvincePO, Integer> getKey = ProvincePO::getId;
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs ->
                        provincePOMapper.selectList(new LambdaQueryWrapper<ProvincePO>()
                                        .in(getKey, subs)
                                        .select(getKey, getValue))
                                .stream())
                .collect(Collectors.toMap(getKey, getValue, (o1, o2) -> o2));
    }

    @Override
    public Map<String, Integer> provinceCodeToProvinceId(Set<String> provinceCodes) {
        List<String> list = CollUtil.valueIsNullToList(provinceCodes);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> provincePOMapper.selectList(new LambdaQueryWrapper<ProvincePO>()
                                .in(ProvincePO::getCode, subs)
                                .select(ProvincePO::getCode, ProvincePO::getId))
                        .stream())
                .collect(Collectors.toMap(ProvincePO::getCode, ProvincePO::getId));
    }

    @Override
    public List<Map.Entry<String, String>> listAll() {
        List<ProvincePO> list = provincePOMapper.selectList(new QueryWrapper<ProvincePO>()
                .lambda()
                .select(ProvincePO::getCode, ProvincePO::getName)
                .orderByAsc(ProvincePO::getCode));
        return list.stream().map(n -> Map.entry(n.getCode(), n.getName())).collect(Collectors.toList());
    }

    /// 获取省份名称
    ///
    /// @param provinceNames 省份名称
    /// @return 省份名称
    @Override
    public Set<String> toProvinceName(Set<String> provinceNames) {
        SFunction<ProvincePO, String> getKey = ProvincePO::getName;
        return ListUtil.partition(cn.hutool.core.collection.CollUtil.toList(provinceNames), 500).stream()
                .flatMap(subs ->
                        provincePOMapper.selectList(new LambdaQueryWrapper<ProvincePO>()
                                .in(getKey, subs).select(getKey)).stream())
                .map(getKey)
                .collect(Collectors.toSet());
    }

    /// 根据省会名获取省会代码
    ///
    /// @param provinceNames 省会名
    /// @return 省会名对应省会代码
    @Override
    public Map<String, String> provinceNameToProvinceCode(Set<String> provinceNames) {
        return ListUtil.partition(cn.hutool.core.collection.CollUtil.toList(provinceNames), 500).stream()
                .flatMap(subs -> provincePOMapper.selectList(new LambdaQueryWrapper<ProvincePO>()
                                .in(ProvincePO::getName, subs)
                                .select(ProvincePO::getName, ProvincePO::getCode))
                        .stream())
                .collect(Collectors.toMap(ProvincePO::getName, ProvincePO::getCode));
    }
}
