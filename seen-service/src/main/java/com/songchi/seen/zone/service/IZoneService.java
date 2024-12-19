package com.songchi.seen.zone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songchi.seen.zone.entity.Zone;
import com.songchi.seen.zone.model.ZoneContent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 空间 服务类
 * </p>
 *
 * @author chixh
 * @since 2021-07-25
 */
public interface IZoneService extends IService<Zone> {

    Map<Integer, LocalDateTime> zoneIdToPublishTime(Set<Integer> zoneIds);

    Map<Integer, Integer> zoneIdToUserId(Set<Integer> zoneIds);

    Map<Integer, Set<Integer>> userIdToZoneId(Set<Integer> userIds);

    boolean delete(Set<Integer> zoneIds);

    Integer publish(List<ZoneContent> zoneContents, Integer userId);

    Map<Integer, LocalDateTime> news(int current, int size);
}
