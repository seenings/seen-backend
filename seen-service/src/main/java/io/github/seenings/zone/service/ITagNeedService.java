package io.github.seenings.zone.service;

import java.util.Map;
import java.util.Set;

/**
 *
 */
public interface ITagNeedService  {

    Map<Long, Set<Integer>> userIdToNeedTagId(Set<Long> userIds);
}
