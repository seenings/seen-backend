package com.songchi.seen.zone.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.article.enumeration.ContentType;
import com.songchi.seen.text.http.HttpTextService;
import com.songchi.seen.zone.entity.Content;
import com.songchi.seen.zone.entity.Zone;
import com.songchi.seen.zone.mapper.ZoneMapper;
import com.songchi.seen.zone.model.ZoneContent;
import com.songchi.seen.zone.service.IContentService;
import com.songchi.seen.zone.service.IZoneService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 空间 服务实现类
 * </p>
 *
 * @author chixh
 * @since 2021-07-25
 */
@Service
public class ZoneServiceImpl extends ServiceImpl<ZoneMapper, Zone> implements IZoneService {

    @Resource
    private IContentService iContentService;

    /**
     * 根据空间ID获取发布时间
     *
     * @param zoneIds 空间ID
     * @return 空间ID对应发布时间
     */
    @Override
    public Map<Integer, LocalDateTime> zoneIdToPublishTime(Set<Integer> zoneIds) {
        if (CollUtil.isEmpty(zoneIds)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(zoneIds), 100).parallelStream()
                .flatMap(subs -> list(new QueryWrapper<Zone>().lambda().in(Zone::getId, subs)).stream())
                .collect(Collectors.toMap(Zone::getId, Zone::getPublishTime, (o1, o2) -> o2));
    }

    /**
     * 根据空间ID获取用户ID
     *
     * @param zoneIds 空间ID
     * @return 空间ID对应用户ID
     */
    @Override
    public Map<Integer, Integer> zoneIdToUserId(Set<Integer> zoneIds) {
        if (CollUtil.isEmpty(zoneIds)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(zoneIds), 100).parallelStream()
                .flatMap(
                        subs -> list(new QueryWrapper<Zone>()
                                        .lambda()
                                        .select(Zone::getUserId, Zone::getId)
                                        .in(Zone::getId, subs))
                                .stream())
                .collect(Collectors.toMap(Zone::getId, Zone::getUserId, (o1, o2) -> o2));
    }

    /**
     * 根据用户ID获取空间ID
     *
     * @param userIds 用户ID
     * @return 用户ID对应空间ID
     */
    @Override
    public Map<Integer, Set<Integer>> userIdToZoneId(Set<Integer> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(userIds), 100).parallelStream()
                .flatMap(subs -> list(new QueryWrapper<Zone>().lambda().in(Zone::getUserId, subs)).stream())
                .collect(Collectors.groupingBy(Zone::getUserId, Collectors.mapping(Zone::getId, Collectors.toSet())));
    }

    /**
     * 根据空间ID删除
     *
     * @param zoneIds 空间ID
     * @return 删除
     */
    @Override
    public boolean delete(Set<Integer> zoneIds) {
        return remove(new QueryWrapper<Zone>().lambda().in(Zone::getId, zoneIds));
    }

    @Resource
    private HttpTextService httpTextService;
    /**
     * 发表说说
     *
     * @param zoneContents 说说内容
     * @param userId       用户
     * @return 空间ID
     */
    @Override
    public Integer publish(List<ZoneContent> zoneContents, Integer userId) {
        Zone zone = new Zone().setUserId(userId).setPublishTime(LocalDateTime.now());
        boolean save = save(zone);
        if (save) {
            // 文本，需要存库后获取ID
            zoneContents.stream()
                    .filter(n -> ContentType.TEXT == n.getContentType())
                    .forEach(n -> {
                        Integer textId = httpTextService.saveAndReturnId(n.getText());
                        n.setContentId(textId);
                    });
            List<Content> contents = zoneContents.stream()
                    .parallel()
                    .map(n -> new Content()
                            .setZoneId(zone.getId())
                            .setContentTypeId(n.getContentType().getIndex())
                            .setContentId(n.getContentId()))
                    .collect(Collectors.toList());
            boolean saveBatch = iContentService.saveBatch(contents);
            if (saveBatch) {
                return zone.getId();
            }
        }
        return null;
    }

    /**
     * 列出热门的用户
     *
     * @param current 当前页
     * @param size    页面大小
     * @return 返回用户
     */
    @Override
    public Map<Integer, LocalDateTime> news(int current, int size) {

        Page<Zone> page = page(
                new Page<>(current, size),
                new QueryWrapper<Zone>()
                        .lambda()
                        .select(Zone::getId, Zone::getPublishTime)
                        .orderByDesc(Zone::getPublishTime));
        return page.getRecords().stream().collect(Collectors.toMap(Zone::getId, Zone::getPublishTime, (o1, o2) -> o1));
    }
}
