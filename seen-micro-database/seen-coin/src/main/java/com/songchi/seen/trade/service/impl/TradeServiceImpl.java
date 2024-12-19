package com.songchi.seen.trade.service.impl;

import com.songchi.seen.account.service.CoinAccountBalanceService;
import com.songchi.seen.account.service.CoinSysAccountBalanceService;
import com.songchi.seen.coin.enumeration.TradeType;
import com.songchi.seen.trade.service.CoinTradeService;
import com.songchi.seen.trade.service.CoinTradeTypeService;
import com.songchi.seen.trade.service.TradeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * TradeServiceImpl
 *
 * @author chixuehui
 * @since 2023-03-11
 */
@Service
public class TradeServiceImpl implements TradeService {

    @Resource
    private CoinTradeService coinTradeService;
    @Resource
    private CoinTradeTypeService coinTradeTypeService;
    @Resource
    private CoinSysAccountBalanceService coinSysAccountBalanceService;
    @Resource
    private CoinAccountBalanceService coinAccountBalanceService;

    @Override
    public Integer trade(Integer inAccountId, Integer outAccountId, Integer coinAmount, TradeType tradeType,
                         String description){
        //一笔交易
        //1.记录交易表
        //2.出账户余额减少
        //3.进账户余额添加
        Integer tradeId = coinTradeService.addTrade(inAccountId, outAccountId, coinAmount, description );
        //TODO 交易频繁时做成异步，进账户余额+，出账户余额-。
        coinAccountBalanceService.add(outAccountId, -coinAmount);
        coinAccountBalanceService.add(inAccountId, coinAmount);
        coinTradeTypeService.addTradeType(tradeId, tradeType);
        return tradeId;
    }
    @Override
    public Integer sysInTrade(Integer inSysAccountId, Integer outAccountId, Integer coinAmount, TradeType tradeType, String description){
        //一笔交易
        //1.记录交易表
        //2.出账户余额减少
        //3.进账户余额添加

        Integer tradeId = coinTradeService.addTrade(inSysAccountId, outAccountId, coinAmount, description );
        coinAccountBalanceService.add(outAccountId, -coinAmount);
        coinSysAccountBalanceService.add(inSysAccountId, coinAmount);
        coinTradeTypeService.addTradeType(tradeId, tradeType);
        return tradeId;
    }
    @Override
    public Integer sysOutTrade(Integer inAccountId, Integer outSysAccountId, Integer coinAmount,  TradeType tradeType,String description){
        //一笔交易
        //1.记录交易表
        //2.出账户余额减少
        //3.进账户余额添加
        Integer tradeId = coinTradeService.addTrade(inAccountId, outSysAccountId, coinAmount, description );
        coinSysAccountBalanceService.add(outSysAccountId, -coinAmount);
        coinAccountBalanceService.add(inAccountId, coinAmount);
        coinTradeTypeService.addTradeType(tradeId, tradeType);
        return tradeId;
    }
    @Override
    public Integer sysTrade(Integer inSysAccountId, Integer outSysAccountId, Integer coinAmount, TradeType tradeType, String description){
        //一笔交易
        //1.记录交易表
        //2.出账户余额减少
        //3.进账户余额添加

        Integer tradeId = coinTradeService.addTrade(inSysAccountId, outSysAccountId, coinAmount, description );
        coinSysAccountBalanceService.add(outSysAccountId, -coinAmount);
        coinSysAccountBalanceService.add(inSysAccountId, coinAmount);
        coinTradeTypeService.addTradeType(tradeId, tradeType);
        return tradeId;
    }
}
