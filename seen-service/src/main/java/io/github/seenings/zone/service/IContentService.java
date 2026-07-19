package io.github.seenings.zone.service;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 空间内容 服务类
 * </p>
 *
 * @author chixh
 * @since 2021-07-25
 */
public interface IContentService  {

    Map<Integer, Set<Integer>> zoneIdToContentIdIsImage(Set<Integer> zoneIds);

    Map<Integer, Set<Integer>> zoneIdToContentIdIsText(Set<Integer> zoneIds);

    Map<Integer, Set<Integer>> zoneContentIdToTextId(Set<Integer> zoneContentIds);
}
