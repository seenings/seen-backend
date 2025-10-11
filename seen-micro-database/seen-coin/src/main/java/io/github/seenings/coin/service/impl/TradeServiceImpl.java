package io.github.seenings.coin.service.impl;

import io.github.seenings.busi.controller.BusiController;
import io.github.seenings.busi.model.Busi;
import io.github.seenings.coin.api.CoinTradeApi;
import io.github.seenings.coin.enumeration.BusiType;
import io.github.seenings.trade.service.TradeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * TradeServiceImpl
 *
 * @author chixuehui
 * @since 2023-03-11
 */
@Service
@AllArgsConstructor
public class TradeServiceImpl implements TradeService {

    /**
     * 业务
     */
    private BusiController busiController;
    /**
     * 玫瑰币
     */
    private CoinTradeApi coinTradeApi;

    @Override
    public Long trade(Long inAccountId, Long outAccountId, Long coinAmount, BusiType busiType,
                      String description) {
        long busiId = busiController.insert(new Busi().setBusiTime(LocalDateTime.now()).setBusiTypeId(busiType.getIndex()));
        coinTradeApi.tradeToTradeTime(inAccountId, outAccountId, coinAmount, busiId);
        return busiId;
    }

    @Override
    public Long sysInTrade(Long inSysAccountId, Long outAccountId, Long coinAmount, BusiType busiType, String description) {
        //一笔交易
        //1.记录交易表
        //2.出账户余额减少
        //3.进账户余额添加
        long busiId = busiController.insert(new Busi().setBusiTime(LocalDateTime.now()).setBusiTypeId(busiType.getIndex()));
        coinTradeApi.tradeToTradeTime(inSysAccountId, outAccountId, coinAmount, busiId);
        return busiId;
    }

    @Override
    public Long sysOutTrade(Long inAccountId, Long outSysAccountId, Long coinAmount, BusiType busiType, String description) {
        //一笔交易
        //1.记录交易表
        //2.出账户余额减少
        //3.进账户余额添加

        long busiId = busiController.insert(new Busi().setBusiTime(LocalDateTime.now()).setBusiTypeId(busiType.getIndex()));
        coinTradeApi.tradeToTradeTime(inAccountId, outSysAccountId, coinAmount, busiId);
        return busiId;
    }
}
