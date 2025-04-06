package io.github.seenings.zone.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.seenings.zone.entity.Zone;
import io.github.seenings.zone.model.ZoneContent;

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

    Map<Integer, Long> zoneIdToUserId(Set<Integer> zoneIds);

    Map<Long, Set<Integer>> userIdToZoneId(Set<Long> userIds);

    boolean delete(Set<Integer> zoneIds);

    Integer publish(List<ZoneContent> zoneContents, Long userId);

    Map<Integer, LocalDateTime> news(int current, int size);
}
