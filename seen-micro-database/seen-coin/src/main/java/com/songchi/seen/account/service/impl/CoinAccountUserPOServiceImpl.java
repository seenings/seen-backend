package com.songchi.seen.account.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.account.po.CoinAccountUserPO;
import com.songchi.seen.account.service.CoinAccountUserService;
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
 * CoinAccountUserPOServiceImpl
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@Mapper
interface CoinAccountUserPOMapper extends BaseMapper<CoinAccountUserPO> {}

@Service
public class CoinAccountUserPOServiceImpl extends ServiceImpl<CoinAccountUserPOMapper, CoinAccountUserPO>
        implements CoinAccountUserService {

    /**
     * 根据用户ID获取账户ID
     * @param userIds 用户ID
     * @return  用户ID对应账户ID
     */
    @Override
    public Map<Integer, Set<Integer>> userIdToAccountId(Set<Integer> userIds) {

        List<Integer> list = CollUtils.valueIsNullToList(userIds);
        if (CollUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .parallel()
                .flatMap(subs -> list(new QueryWrapper<CoinAccountUserPO>()
                                .lambda()
                                .in(CoinAccountUserPO::getUserId, subs)
                                .select(CoinAccountUserPO::getUserId, CoinAccountUserPO::getAccountId))
                        .stream())
                .collect(Collectors.groupingBy(
                        CoinAccountUserPO::getUserId,
                        Collectors.mapping(CoinAccountUserPO::getAccountId, Collectors.toSet())));
    }

    /**
     * 设置账户与用户关系
     * @param accountId  账户ID
     * @param userId    用户ID
     * @return  设置成功
     */
    @Override
    public boolean set(Integer accountId, Integer userId) {
        Set<Integer> accountIds =
                userIdToAccountId(Collections.singleton(userId)).get(userId);
        boolean contains = CollUtils.contains(accountIds, accountId);
        if (!contains) {
            CoinAccountUserPO po = new CoinAccountUserPO()
                    .setUserId(userId)
                    .setAccountId(accountId)
                    .setCreateTime(LocalDateTime.now());
            return save(po);
        } else {
            return false;
        }
    }
}
