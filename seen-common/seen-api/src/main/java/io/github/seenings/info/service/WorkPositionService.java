package io.github.seenings.info.service;

import java.util.Map;
import java.util.Set;

/**
 * WorkPositionService
 *
 * @author chixuehui
 * @since 2022-10-16
 */
public interface WorkPositionService {
    Map<Integer, String> workPosition();

    Map<Integer, String> positionIdToPositionName(Set<Integer> ids);

    boolean exists(String positionName);

    boolean set(String positionName);
}
