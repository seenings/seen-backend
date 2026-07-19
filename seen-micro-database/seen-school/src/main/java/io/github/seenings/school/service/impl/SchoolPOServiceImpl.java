package io.github.seenings.school.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.seenings.core.util.CollUtil;
import io.github.seenings.school.po.SchoolPO;
import io.github.seenings.school.service.SchoolService;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SchoolPOServiceImpl
 *
 * @author chixuehui
 * @since 2022-12-03
 */
@Mapper
interface SchoolPOMapper extends BaseMapper<SchoolPO> {}

@AllArgsConstructor
@Repository
public class SchoolPOServiceImpl  implements SchoolService {

    private SchoolPOMapper schoolPOMapper;

    @Override
    public Map<Integer, String> idToSchoolName(Set<Integer> ids) {
        List<Integer> list = CollUtil.valueIsNullToList(ids);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .parallel()
                .flatMap(subs -> schoolPOMapper.selectList(new LambdaQueryWrapper<SchoolPO>()
                                .in(SchoolPO::getId, subs)
                                .select(SchoolPO::getId, SchoolPO::getSchoolName))
                        .stream())
                .collect(Collectors.toMap(SchoolPO::getId, SchoolPO::getSchoolName, (o1, o2) -> o2));
    }

    @Override
    public Map<String, List<Integer>> provinceCodeToSchoolId(Set<String> provinceCodes) {
        List<String> list = CollUtil.valueIsNullToList(provinceCodes);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> schoolPOMapper.selectList(new LambdaQueryWrapper<SchoolPO>()
                                .in(SchoolPO::getAreaId, subs)
                                .select(SchoolPO::getAreaId, SchoolPO::getId))
                        .stream())
                .collect(Collectors.groupingBy(
                        SchoolPO::getAreaId, Collectors.mapping(SchoolPO::getId, Collectors.toList())));
    }

    @Override
    public Map<Integer, String> schoolIdToProvinceCode(Set<Integer> schoolIds) {
        List<Integer> list = CollUtil.valueIsNullToList(schoolIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> schoolPOMapper.selectList(new LambdaQueryWrapper<SchoolPO>()
                                .in(SchoolPO::getId, subs)
                                .select(SchoolPO::getAreaId, SchoolPO::getId))
                        .stream())
                .collect(Collectors.toMap(SchoolPO::getId, SchoolPO::getAreaId));
    }
}
