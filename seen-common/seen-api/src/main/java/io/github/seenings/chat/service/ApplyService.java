package io.github.seenings.chat.service;

import io.github.seenings.chat.enumeration.ApplyStatus;

import java.util.Map;
import java.util.Set;

/**
 * ApplyService
 *
 * @author chixuehui
 * @since 2023-03-05
 */
public interface ApplyService {
    Map<Integer, ApplyStatus> applyIdToApplyStatus(Set<Integer> applyIds);
}
