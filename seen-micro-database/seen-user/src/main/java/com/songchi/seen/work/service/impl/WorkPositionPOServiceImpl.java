package com.songchi.seen.work.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtil;
import com.songchi.seen.info.service.WorkPositionService;
import com.songchi.seen.work.po.WorkPositionPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * WorkPositionPOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@Mapper
interface WorkPositionPOMapper extends BaseMapper<WorkPositionPO> {}

@Service
public class WorkPositionPOServiceImpl extends ServiceImpl<WorkPositionPOMapper, WorkPositionPO>
        implements WorkPositionService {

    @Override
    public Map<Integer, String> workPosition() {
        return list(new QueryWrapper<WorkPositionPO>()
                        .lambda()
                        .select(WorkPositionPO::getId, WorkPositionPO::getPositionName))
                .stream()
                .collect(Collectors.toMap(WorkPositionPO::getId, WorkPositionPO::getPositionName, (o1, o2) -> o2));
    }

    @Override
    public Map<Integer, String> positionIdToPositionName(Set<Integer> ids) {
        List<Integer> list = CollUtil.valueIsNullToList(ids);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        SFunction<WorkPositionPO, String> getValue = WorkPositionPO::getPositionName;
        SFunction<WorkPositionPO, Integer> getKey = WorkPositionPO::getId;
        return ListUtil.partition(list, 500).stream()
                .flatMap(subs ->
                        list(new LambdaQueryWrapper<WorkPositionPO>()
                                        .in(getKey, subs)
                                        .select(getKey, getValue))
                                .stream())
                .collect(Collectors.toMap(getKey, getValue, (o1, o2) -> o2));
    }

    @Override
    public boolean exists(String positionName) {
        List<WorkPositionPO> list = list(new QueryWrapper<WorkPositionPO>()
                .lambda()
                .eq(WorkPositionPO::getPositionName, positionName)
                .select(WorkPositionPO::getId));
        return CollUtil.isNotEmpty(list);
    }

    @Override
    public boolean set(String positionName) {
        var po = new WorkPositionPO().setPositionName(positionName).setUpdateTime(LocalDateTime.now());
        if (!exists(positionName)) {
            return save(po);
        } else {
            return false;
        }
    }
}
