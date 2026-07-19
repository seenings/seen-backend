package io.github.seenings.coin.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.seenings.coin.po.CoinTradePO;
import io.github.seenings.trade.service.CoinTradeService;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * CoinTradePOServiceImpl
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@Mapper
interface CoinTradePOMapper extends BaseMapper<CoinTradePO> {}
@AllArgsConstructor
@Repository
public class CoinTradePOServiceImpl   implements CoinTradeService {

    private CoinTradePOMapper coinTradePOMapper;
    /**
     * 添加一笔交易
     * @param inAccountId   资金进账户ID
     * @param outAccountId  资金出账户ID
     * @param coinAmount    虚拟币个数
     * @param description   交易描述
     * @return  交易ID
     */
    @Override
    public Integer addTrade(Long inAccountId, Long outAccountId, Integer coinAmount, String description) {
        LocalDateTime now = LocalDateTime.now();
        CoinTradePO po = new CoinTradePO()
                .setTradeTime(now)
                .setCreateTime(now)
                .setCoinAmount(coinAmount)
                .setInAccountId(inAccountId)
                .setOutAccountId(outAccountId)
                .setDescription(description);
        boolean save = coinTradePOMapper.insert(po)>0;
        if (save) {
            return po.getId();
        } else {
            return null;
        }
    }
}
