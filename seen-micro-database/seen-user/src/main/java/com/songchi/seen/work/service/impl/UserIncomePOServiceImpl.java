package com.songchi.seen.work.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtil;
import com.songchi.seen.info.enumeration.YearIncomeEnum;
import com.songchi.seen.work.po.UserIncomePO;
import com.songchi.seen.work.service.UserIncomeService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserIncomePOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-06
 */
@Mapper
interface UserIncomePOMapper extends BaseMapper<UserIncomePO> {}

@Service
public class UserIncomePOServiceImpl extends ServiceImpl<UserIncomePOMapper, UserIncomePO>
        implements UserIncomeService {

    /**
     * 根据用户ID获取年度收入
     *
     * @param userIds 用户ID
     * @return 用户ID对应年度收入
     */
    @Override
    public Map<Long, Integer> userIdToAnnualIncome(Set<Long> userIds) {
        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserIncomePO>()
                                .in(UserIncomePO::getUserId, subs)
                                .select(UserIncomePO::getUserId, UserIncomePO::getAnnualIncome))
                        .stream())
                .collect(Collectors.toMap(UserIncomePO::getUserId, UserIncomePO::getAnnualIncome, (o1, o2) -> o2));
    }

    @Override
    public long annualIncomeCount(YearIncomeEnum yearIncomeEnum) {
        return count(
                new QueryWrapper<UserIncomePO>().lambda().eq(UserIncomePO::getAnnualIncome, yearIncomeEnum.getIndex()));
    }

    @Override
    public List<Long> annualIncomeToUserId(YearIncomeEnum yearIncomeEnum, int current, int size) {
        Page<UserIncomePO> page = page(
                new Page<>(current, size),
                new QueryWrapper<UserIncomePO>()
                        .lambda()
                        .eq(UserIncomePO::getAnnualIncome, yearIncomeEnum.getIndex())
                        .select(UserIncomePO::getUserId));
        return page.getRecords().stream()
                .parallel()
                .map(UserIncomePO::getUserId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean set(Long userId, YearIncomeEnum yearIncomeEnum) {
        Map<Long, Integer> userIdToAnnualIncomeMap = userIdToAnnualIncome(Collections.singleton(userId));
        Integer data = userIdToAnnualIncomeMap.get(userId);
        UserIncomePO po = new UserIncomePO()
                .setUserId(userId)
                .setAnnualIncome(yearIncomeEnum.getIndex())
                .setUpdateTime(LocalDateTime.now());
        if (data == null) {
            return save(po);
        } else {
            return update(po, new LambdaQueryWrapper<UserIncomePO>().eq(UserIncomePO::getUserId, userId));
        }
    }
}
