package io.github.seenings.zone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.seenings.zone.entity.Content;

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
public interface IContentService extends IService<Content> {

    Map<Integer, Set<Integer>> zoneIdToContentIdIsImage(Set<Integer> zoneIds);

    Map<Integer, Set<Integer>> zoneIdToContentIdIsText(Set<Integer> zoneIds);

    Map<Integer, Set<Integer>> zoneContentIdToTextId(Set<Integer> zoneContentIds);
}
