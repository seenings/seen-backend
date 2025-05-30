package io.github.seenings.coin.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.coin.po.CoinSysAccountBalancePO;
import io.github.seenings.account.service.CoinSysAccountBalanceService;
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
 * CoinSysAccountBalancePOServiceImpl
 *
 * @author chixuehui
 * @since 2023-03-05
 */
@Mapper
interface CoinSysAccountBalancePOMapper extends BaseMapper<CoinSysAccountBalancePO> {
}

@Service
public class CoinSysAccountBalancePOServiceImpl extends ServiceImpl<CoinSysAccountBalancePOMapper, CoinSysAccountBalancePO> implements CoinSysAccountBalanceService {

    /**
     * 根据账户ID获取余额
     *
     * @param accountIds 账户ID
     * @return 账户ID对应余额
     */
    @Override
    public Map<Long, Integer> accountIdCoinAmount(Set<Long> accountIds) {

        List<Long> list = CollUtil.valueIsNullToList(accountIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .parallel()
                .flatMap(subs -> list(new QueryWrapper<CoinSysAccountBalancePO>()
                        .lambda()
                        .in(CoinSysAccountBalancePO::getAccountId, subs)
                        .select(CoinSysAccountBalancePO::getAccountId, CoinSysAccountBalancePO::getCoinAmount))
                        .stream())
                .collect(Collectors.toMap(
                        CoinSysAccountBalancePO::getAccountId, CoinSysAccountBalancePO::getCoinAmount, (o1, o2) -> o2));
    }

    /**
     * 添加余额
     *
     * @param accountId    账户ID
     * @param offsetAmount 虚拟币余额偏移，正（+），负数（-）
     * @return 添加余额成功
     */
    @Override
    public boolean add(Long accountId, Integer offsetAmount) {
        Integer existsCoinAmount =
                accountIdCoinAmount(Collections.singleton(accountId)).get(accountId);
        LocalDateTime now = LocalDateTime.now();
        CoinSysAccountBalancePO po = new CoinSysAccountBalancePO()
                .setCoinAmount(existsCoinAmount + offsetAmount)
                .setChangeTime(now)
                .setUpdateTime(now);
        return update(
                po,
                new UpdateWrapper<CoinSysAccountBalancePO>()
                        .lambda()
                        .eq(CoinSysAccountBalancePO::getAccountId, accountId));
    }
}
