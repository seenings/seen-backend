package io.github.seenings.coin.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.coin.po.CoinAccount;
import com.songchi.seen.account.service.CoinAccountService;
import com.songchi.seen.coin.enumeration.AccountType;
import com.songchi.seen.core.util.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 币账户
 */
@Mapper
interface CoinAccountMapper extends BaseMapper<CoinAccount> {
}

/**
 * 币账户
 */
@Slf4j
@Service
public class CoinAccountServiceImpl extends ServiceImpl<CoinAccountMapper, CoinAccount> implements CoinAccountService {

    @Override
    public Map<Integer, List<Long>> accountTypeToAccountId(Set<Integer> accountTypeIds) {

        List<Integer> list = CollUtil.valueIsNullToList(accountTypeIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream().parallel().flatMap(subs -> list(new QueryWrapper<CoinAccount>().lambda().in(CoinAccount::getAccountType, subs).select(CoinAccount::getId, CoinAccount::getAccountType)).stream()).collect(Collectors.groupingBy(CoinAccount::getAccountType, Collectors.mapping(CoinAccount::getId, Collectors.toList())));


    }

    /**
     * 根据账户ID获取账户类型
     *
     * @param accountIds 账户ID
     * @return 账户ID对应账户类型
     */
    @Override
    public Map<Long, AccountType> accountIdToAccountType(Set<Long> accountIds) {

        List<Long> list = CollUtil.valueIsNullToList(accountIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream().parallel().flatMap(subs -> list(new QueryWrapper<CoinAccount>().lambda().in(CoinAccount::getId, subs).select(CoinAccount::getId, CoinAccount::getAccountType)).stream()).map(n -> {
            AccountType accountType = AccountType.indexToEnum(n.getAccountType());
            if (accountType == null) {
                String msg = String.format("账户类型有误，账户ID：%s。", n.getAccountType());
                log.error(msg);
                return null;
            }
            return Map.entry(n.getId(), accountType);
        }).filter(Objects::nonNull).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (o1, o2) -> o2));
    }

    /**
     * 生成账户
     *
     * @param accountType 账户类型
     * @return 账户ID
     */
    @Override
    public Long createAccount(AccountType accountType) {
        CoinAccount po = new CoinAccount().setAccountType(accountType.getIndex()).setCreateTime(LocalDateTime.now());
        boolean save = save(po);
        if (save) {
            return po.getId();
        } else {
            return null;
        }
    }
}
