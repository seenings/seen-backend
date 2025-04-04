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

    Integer freezeToSysUse(Long userId, int coinMount,
                           TradeType tradeType, String description);

    Integer freezeToTemporary(Long userId, int coinMount,
                              TradeType tradeType, String description);

    Boolean checkEnough(Long userId, int coinMount);

    Set<Integer> checkEnoughAndFreeze(Long userId, int coinMount, TradeType tradeType, String description);
}
