package io.github.seenings.coin.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.coin.enumeration.TradeType;
import io.github.seenings.coin.po.CoinTradeTypePO;
import com.songchi.seen.trade.service.CoinTradeTypeService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * CoinTradeTypePOServiceImpl
 *
 * @author chixuehui
 * @since 2023-03-05
 */
@Mapper
interface CoinTradeTypePOMapper extends BaseMapper<CoinTradeTypePO> {
}

@Service
public class CoinTradeTypePOServiceImpl extends ServiceImpl<CoinTradeTypePOMapper, CoinTradeTypePO> implements CoinTradeTypeService {

    /**
     * 添加一笔交易的类型
     *
     * @param tradeType 交易类型
     * @param tradeId   交易ID
     * @return 交易类型记录ID
     */
    @Override
    public Integer addTradeType(Integer tradeId, TradeType tradeType) {
        LocalDateTime now = LocalDateTime.now();
        CoinTradeTypePO po = new CoinTradeTypePO()
                .setCreateTime(now)
                .setTradeTypeId(tradeType.getIndex())
                .setTradeId(tradeId);
        boolean save = save(po);
        if (save) {
            return po.getId();
        } else {
            return null;
        }
    }

}
