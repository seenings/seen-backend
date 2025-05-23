package io.github.seenings.apply.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.apply.po.UserApplyTradePO;
import io.github.seenings.apply.service.UserApplyTradeService;
import io.github.seenings.core.util.CollUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserApplyTradePOServiceImpl
 *
 * @author chixuehui
 * @since 2023-03-05
 */
@Mapper
interface UserApplyTradePOMapper extends BaseMapper<UserApplyTradePO> {
}

@Service
public class UserApplyTradePOServiceImpl extends ServiceImpl<UserApplyTradePOMapper, UserApplyTradePO> implements UserApplyTradeService {


    @Override
    public Map<Integer, List<Integer>> applyIdToTradeId(Set<Integer> applyIds) {
        List<Integer> list = CollUtil.valueIsNullToList(applyIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserApplyTradePO>()
                        .in(UserApplyTradePO::getApplyId, subs)
                        .select(UserApplyTradePO::getApplyId, UserApplyTradePO::getTradeId))
                        .stream())
                .collect(Collectors.groupingBy(UserApplyTradePO::getApplyId, Collectors.mapping(UserApplyTradePO::getTradeId, Collectors.toList())));
    }

    @Override
    public Map<Integer, Integer> tradeIdToApplyId(Set<Integer> tradeIds) {
        List<Integer> list = CollUtil.valueIsNullToList(tradeIds);
        if (cn.hutool.core.collection.CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .flatMap(subs -> list(new LambdaQueryWrapper<UserApplyTradePO>()
                        .in(UserApplyTradePO::getTradeId, subs)
                        .select(UserApplyTradePO::getApplyId, UserApplyTradePO::getTradeId))
                        .stream())
                .collect(Collectors.toMap(UserApplyTradePO::getTradeId, UserApplyTradePO::getApplyId));
    }

    @Override
    public List<Integer> set(Integer applyId, List<Integer> tradeIds) {

        return tradeIds.stream().map(tradeId -> {
            UserApplyTradePO po = new UserApplyTradePO()
                    .setApplyId(applyId)
                    .setTradeId(tradeId)
                    .setCreateTime(LocalDateTime.now());
            save(po);
            return po.getId();
        }).collect(Collectors.toList());

    }
}
