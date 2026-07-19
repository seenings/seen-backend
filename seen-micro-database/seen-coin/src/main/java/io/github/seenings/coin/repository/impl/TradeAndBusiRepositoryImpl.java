package io.github.seenings.coin.repository.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.seenings.coin.po.TradeAndBusi;
import io.github.seenings.coin.repository.TradeAndBusiRepository;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * 交易与业务关系
 */
@Mapper
interface TradeAndBusiMapper extends BaseMapper<TradeAndBusi>{}
/**
 * 交易与业务关系
 */
@AllArgsConstructor
@Repository
public class TradeAndBusiRepositoryImpl implements TradeAndBusiRepository {

    private TradeAndBusiMapper tradeAndBusiMapper;
    /**
     * 增加交易与业务关系
     * @param tradeTime 交易时间
     * @param busiId    业务ID
     * @param tradeId   交易ID
     * @return  是否增加成功
     */
    @Override
    public boolean add(LocalDateTime tradeTime, Long busiId, Long tradeId){
        TradeAndBusi entity = new TradeAndBusi().setTradeTime(tradeTime)
                .setBusiId(busiId)
                .setTradeId(tradeId);
        return tradeAndBusiMapper.insert(entity)>0;
    }
}
