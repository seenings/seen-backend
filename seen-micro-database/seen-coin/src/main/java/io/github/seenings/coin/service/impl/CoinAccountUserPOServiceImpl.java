package io.github.seenings.coin.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.coin.po.CoinAccountUser;
import io.github.seenings.account.service.CoinAccountUserService;
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
 * CoinAccountUserPOServiceImpl
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@Mapper
interface CoinAccountUserPOMapper extends BaseMapper<CoinAccountUser> {}

@Service
public class CoinAccountUserPOServiceImpl extends ServiceImpl<CoinAccountUserPOMapper, CoinAccountUser>
        implements CoinAccountUserService {

    /**
     * 根据用户ID获取账户ID
     * @param userIds 用户ID
     * @return  用户ID对应账户ID
     */
    @Override
    public Map<Long, Set<Long>> userIdToAccountId(Set<Long> userIds) {

        List<Long> list = CollUtil.valueIsNullToList(userIds);
        if (CollUtil.isEmpty(list)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(list, 100).stream()
                .parallel()
                .flatMap(subs -> list(new QueryWrapper<CoinAccountUser>()
                                .lambda()
                                .in(CoinAccountUser::getUserId, subs)
                                .select(CoinAccountUser::getUserId, CoinAccountUser::getAccountId))
                        .stream())
                .collect(Collectors.groupingBy(
                        CoinAccountUser::getUserId,
                        Collectors.mapping(CoinAccountUser::getAccountId, Collectors.toSet())));
    }

    /**
     * 设置账户与用户关系
     * @param accountId  账户ID
     * @param userId    用户ID
     * @return  设置成功
     */
    @Override
    public boolean set(Long accountId, Long userId) {
        Set<Long> accountIds =
                userIdToAccountId(Collections.singleton(userId)).get(userId);
        boolean contains = CollUtil.contains(accountIds, accountId);
        if (!contains) {
            CoinAccountUser po = new CoinAccountUser()
                    .setUserId(userId)
                    .setAccountId(accountId)
                    .setCreateTime(LocalDateTime.now());
            return save(po);
        } else {
            return false;
        }
    }
}
