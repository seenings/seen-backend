package io.github.seenings.account.service;

import io.github.seenings.coin.enumeration.BusiType;

import java.util.Set;

/**
 * FreezeService
 *
 * @author chixuehui
 * @since 2023-01-01
 */
public interface FreezeService {

    Long freezeToSysUse(Long userId, Long coinMount,
                           BusiType busiType, String description);

    Long freezeToTemporary(Long userId, Long coinMount,
                              BusiType busiType, String description);

    Boolean checkEnough(Long userId, Long coinMount);

    Set<Long> checkEnoughAndFreeze(Long userId, Long coinMount, BusiType busiType, String description);
}
