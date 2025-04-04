package io.github.seenings.coin.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.coin.po.CoinAccountBalancePO;
import com.songchi.seen.account.service.CoinAccountBalanceService;
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
 * CoinAccountBalancePOServiceImpl
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@Mapper
interface CoinAccountBalancePOMapper extends BaseMapper<CoinAccountBalancePO> {
}

@Service
public class CoinAccountBalancePOServiceImpl extends ServiceImpl<CoinAccountBalancePOMapper, CoinAccountBalancePO>
        implements CoinAccountBalanceService {

    /**
     * 根据账户ID获取余额
     *
     * @param accountIds 账户ID
     * @return 账户ID对应余额
     */
    @Override
    public Map<Long, Integer> accountIdCoinAmount(Set<Long> accountIds) {

        List<Long> list = CollUtils.valueIsNullToList(accountIds);
        if (CollUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .parallel()
                .flatMap(subs -> list(new QueryWrapper<CoinAccountBalancePO>()
                        .lambda()
                        .in(CoinAccountBalancePO::getAccountId, subs)
                        .select(CoinAccountBalancePO::getAccountId, CoinAccountBalancePO::getCoinAmount))
                        .stream())
                .collect(Collectors.toMap(
                        CoinAccountBalancePO::getAccountId, CoinAccountBalancePO::getCoinAmount, (o1, o2) -> o2));
    }

    /**
     * 设置账户余额
     *
     * @param accountId 账户ID
     * @param amount    虚拟币余额
     * @return 设置成功
     */
    @Override
    public boolean set(Long accountId, Integer amount) {
        Integer existsCoinAmount =
                accountIdCoinAmount(Collections.singleton(accountId)).get(accountId);
        LocalDateTime now = LocalDateTime.now();
        CoinAccountBalancePO po = new CoinAccountBalancePO()
                .setCoinAmount(amount)
                .setChangeTime(now)
                .setUpdateTime(now);
        if (existsCoinAmount != null) {
            return update(
                    po,
                    new UpdateWrapper<CoinAccountBalancePO>()
                            .lambda()
                            .eq(CoinAccountBalancePO::getAccountId, accountId));
        } else {
            po.setCreateTime(now);
            po.setAccountId(accountId);
            return save(po);
        }
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
        CoinAccountBalancePO po = new CoinAccountBalancePO()
                .setCoinAmount(existsCoinAmount + offsetAmount)
                .setChangeTime(now)
                .setUpdateTime(now);
        return update(
                po,
                new UpdateWrapper<CoinAccountBalancePO>()
                        .lambda()
                        .eq(CoinAccountBalancePO::getAccountId, accountId));
    }
}
