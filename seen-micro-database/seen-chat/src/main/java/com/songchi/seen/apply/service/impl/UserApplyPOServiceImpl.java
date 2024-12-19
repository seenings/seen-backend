package com.songchi.seen.apply.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.apply.po.UserApplyPO;
import com.songchi.seen.apply.service.UserApplyService;
import com.songchi.seen.core.util.CollUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserApplyPOServiceImpl
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@Mapper
interface UserApplyPOMapper extends BaseMapper<UserApplyPO> {
}

@Service
public class UserApplyPOServiceImpl extends ServiceImpl<UserApplyPOMapper, UserApplyPO> implements UserApplyService {


    /**
     * 根据被申请人用户ID获取申请ID
     * @param appliedUserId   被申请人用户ID
     * @param current   当前页
     * @param size  页大小
     * @return  申请ID
     */
    @Override
    public List<Integer> appliedUserIdToApplyIdByPage(Integer appliedUserId, int current, int size) {
        Page<UserApplyPO> page = page(
                new Page<>(current, size)
                , new LambdaQueryWrapper<UserApplyPO>()
                        .eq(UserApplyPO::getAppliedUserId, appliedUserId)
                        .select(UserApplyPO::getId)
                        .orderByDesc(UserApplyPO::getApplyTime)
        );
        return page.getRecords().stream().map(UserApplyPO::getId).collect(Collectors.toList());
    }

    /**
     * 根据申请人用户ID获取申请ID
     * @param applyUserId   申请人用户ID
     * @param current   当前页
     * @param size  页大小
     * @return  申请ID
     */
    @Override
    public List<Integer> applyUserIdToApplyIdByPage(Integer applyUserId, int current, int size) {
        Page<UserApplyPO> page = page(
                new Page<>(current, size)
                , new LambdaQueryWrapper<UserApplyPO>()
                        .eq(UserApplyPO::getUserId, applyUserId)
                        .select(UserApplyPO::getId)
                        .orderByDesc(UserApplyPO::getApplyTime)
        );
        return page.getRecords().stream().map(UserApplyPO::getId).collect(Collectors.toList());
    }

    @Override
    public Map<Integer, Integer> appliedUserIdToId(Set<Integer> appliedUserIds, Integer userId) {
        List<Integer> list = CollUtils.valueIsNullToList(appliedUserIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserApplyPO>()
                        .in(UserApplyPO::getAppliedUserId, subs)
                        .eq(UserApplyPO::getUserId, userId)
                        .select(UserApplyPO::getId, UserApplyPO::getAppliedUserId))
                        .stream())
                .collect(Collectors.toMap(UserApplyPO::getAppliedUserId, UserApplyPO::getId, (o1, o2) -> o2));
    }

    @Override
    public Map<Integer, Integer> idToTextId(Set<Integer> ids) {
        List<Integer> list = CollUtils.valueIsNullToList(ids);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserApplyPO>()
                        .in(UserApplyPO::getId, subs)
                        .select(UserApplyPO::getId, UserApplyPO::getTextId))
                        .stream())
                .collect(Collectors.toMap(UserApplyPO::getId, UserApplyPO::getTextId, (o1, o2) -> o2));
    }

    @Override
    public Map<Integer, Integer> idToApplyUserId(Set<Integer> ids) {
        List<Integer> list = CollUtils.valueIsNullToList(ids);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserApplyPO>()
                        .in(UserApplyPO::getId, subs)
                        .select(UserApplyPO::getId, UserApplyPO::getUserId))
                        .stream())
                .collect(Collectors.toMap(UserApplyPO::getId, UserApplyPO::getUserId));
    }
    @Override
    public Map<Integer, Integer> idToAppliedUserId(Set<Integer> ids) {
        List<Integer> list = CollUtils.valueIsNullToList(ids);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserApplyPO>()
                        .in(UserApplyPO::getId, subs)
                        .select(UserApplyPO::getId, UserApplyPO::getAppliedUserId))
                        .stream())
                .collect(Collectors.toMap(UserApplyPO::getId, UserApplyPO::getAppliedUserId));
    }

    @Override
    public Map<Integer, LocalDateTime> idToCreateTime(Set<Integer> ids) {
        List<Integer> list = CollUtils.valueIsNullToList(ids);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserApplyPO>()
                        .in(UserApplyPO::getId, subs)
                        .select(UserApplyPO::getId, UserApplyPO::getCreateTime))
                        .stream())
                .collect(Collectors.toMap(UserApplyPO::getId, UserApplyPO::getCreateTime, (o1, o2) -> o2));
    }

    @Override
    public Map<Integer, LocalDateTime> idToApplyTime(Set<Integer> ids) {
        List<Integer> list = CollUtils.valueIsNullToList(ids);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserApplyPO>()
                        .in(UserApplyPO::getId, subs)
                        .select(UserApplyPO::getId, UserApplyPO::getApplyTime))
                        .stream())
                .collect(Collectors.toMap(UserApplyPO::getId, UserApplyPO::getApplyTime, (o1, o2) -> o2));
    }

    @Override
    public Integer set(Integer userId, Integer textId, Integer appliedUserId) {

        UserApplyPO po = new UserApplyPO()
                .setAppliedUserId(appliedUserId)
                .setUserId(userId)
                .setTextId(textId)
                .setCreateTime(LocalDateTime.now());
        save(po);
        return po.getId();
    }
}
