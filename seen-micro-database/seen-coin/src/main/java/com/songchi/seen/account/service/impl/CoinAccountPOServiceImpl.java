package com.songchi.seen.account.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Pair;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.account.po.CoinAccountPO;
import com.songchi.seen.account.service.CoinAccountService;
import com.songchi.seen.coin.enumeration.AccountType;
import com.songchi.seen.core.util.CollUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CoinAccountPOServiceImpl
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@Mapper
interface CoinAccountPOMapper extends BaseMapper<CoinAccountPO> {
}

@Slf4j
@Service
public class CoinAccountPOServiceImpl extends ServiceImpl<CoinAccountPOMapper, CoinAccountPO>
        implements CoinAccountService {

    @Override
    public Map<Integer, List<Integer>> accountTypeToAccountId(Set<Integer> accountTypeIds) {

        List<Integer> list = CollUtils.valueIsNullToList(accountTypeIds);
        if (CollUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .parallel()
                .flatMap(subs -> list(new QueryWrapper<CoinAccountPO>()
                        .lambda()
                        .in(CoinAccountPO::getAccountType, subs)
                        .select(CoinAccountPO::getId, CoinAccountPO::getAccountType))
                        .stream())
                .collect(Collectors.groupingBy(CoinAccountPO::getAccountType, Collectors.mapping(CoinAccountPO::getId, Collectors.toList())));


    }

    /**
     * 根据账户ID获取账户类型
     *
     * @param accountIds 账户ID
     * @return 账户ID对应账户类型
     */
    @Override
    public Map<Integer, AccountType> accountIdToAccountType(Set<Integer> accountIds) {

        List<Integer> list = CollUtils.valueIsNullToList(accountIds);
        if (CollUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .parallel()
                .flatMap(subs -> list(new QueryWrapper<CoinAccountPO>()
                        .lambda()
                        .in(CoinAccountPO::getId, subs)
                        .select(CoinAccountPO::getId, CoinAccountPO::getAccountType))
                        .stream())
                .map(n -> {
                    AccountType accountType = AccountType.indexToEnum(n.getAccountType());
                    if (accountType == null) {
                        String msg = String.format("账户类型有误，账户ID：%s。", n.getAccountType());
                        log.error(msg);
                        return null;
                    }
                    return Pair.of(n.getId(), accountType);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue, (o1, o2) -> o2));
    }

    /**
     * 生成账户
     *
     * @param accountType 账户类型
     * @param description 描述
     * @return 账户ID
     */
    @Override
    public Integer createAccount(String description, AccountType accountType) {
        CoinAccountPO po = new CoinAccountPO()
                .setAccountType(accountType.getIndex())
                .setDescription(description)
                .setCreateTime(LocalDateTime.now());
        boolean save = save(po);
        if (save) {
            return po.getId();
        } else {
            return null;
        }
    }
}
