package com.songchi.seen.account.service;

import com.songchi.seen.coin.enumeration.TradeType;

import java.util.Set;

/**
 * FreezeService
 *
 * @author chixuehui
 * @since 2023-01-01
 */
public interface FreezeService {

    Integer freezeToSysUse(Integer userId, int coinMount,
                           TradeType tradeType, String description);

    Integer freezeToTemporary(Integer userId, int coinMount,
                              TradeType tradeType, String description);

    Boolean checkEnough(Integer userId, int coinMount);

    Set<Integer> checkEnoughAndFreeze(Integer userId, int coinMount, TradeType tradeType, String description);
}
