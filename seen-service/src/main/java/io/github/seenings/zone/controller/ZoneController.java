package io.github.seenings.zone.controller;

import cn.hutool.core.collection.CollUtil;
import io.github.seenings.photo.http.HttpPhotoService;
import io.github.seenings.sys.constant.PublicConstant;
import io.github.seenings.common.model.R;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.text.http.HttpTextService;
import io.github.seenings.info.service.InfoService;
import io.github.seenings.zone.entity.Reply;
import io.github.seenings.zone.model.ZoneContent;
import io.github.seenings.zone.model.ZoneRecord;
import io.github.seenings.zone.model.ZoneReply;
import io.github.seenings.zone.model.ZoneText;
import io.github.seenings.zone.service.IContentService;
import io.github.seenings.zone.service.IReplyService;
import io.github.seenings.zone.service.IZoneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 空间 前端控制器
 * </p>
 *
 * @author chixh
 * @since 2021-07-25
 */
@Slf4j
@RestController
@RequestMapping(PublicConstant.REST + "zone/zone")
public class ZoneController {

    @Resource
    private IZoneService iZoneService;

    @Resource
    private IContentService iContentService;


    @Resource
    private IReplyService iReplyService;

    @Resource
    private InfoService infoService;

    /**
     * 删除空间
     *
     * @param zoneIds 空间ID
     * @return 是否删除成功
     */
    @PostMapping("delete")
    public R<Boolean> delete(@RequestBody Set<Integer> zoneIds, @SessionAttribute Long userId) {
        Set<Integer> enableZoneIds =
                iZoneService.userIdToZoneId(Collections.singleton(userId)).get(userId);
        Set<Integer> deletingZoneIds = new HashSet<>(CollUtil.intersection(enableZoneIds, zoneIds));
        if (CollUtil.isEmpty(deletingZoneIds)) {
            String msg = "空间内容不允许删除。" + zoneIds;
            return ResUtils.error(msg);
        }
        boolean delete = iZoneService.delete(deletingZoneIds);
        return ResUtils.ok(delete);
    }

    @PostMapping("publish")
    public R<Integer> publish(
            @RequestBody List<ZoneContent> zoneContents, @SessionAttribute(PublicConstant.USER_ID) Long userId) {

        Integer zoneId = iZoneService.publish(zoneContents, userId);
        if (zoneId != null) {
            return ResUtils.ok(zoneId, "说说发布成功。");
        } else {
            String msg = "发布说说失败。";
            log.error("{}{}", msg, zoneContents);
            return ResUtils.error(msg);
        }
    }

    @Resource
    private HttpTextService httpTextService;

    @PostMapping("my-zone-text")
    public R<List<ZoneText>> myZoneText(@SessionAttribute Long userId) {
        Set<Integer> zoneIds =
                iZoneService.userIdToZoneId(Collections.singleton(userId)).get(userId);
        Map<Integer, LocalDateTime> zoneIdToPublishTimeMap = iZoneService.zoneIdToPublishTime(zoneIds);
        Map<Integer, Set<Integer>> zoneIdToContentIdIsTextMap = iContentService.zoneIdToContentIdIsText(zoneIds);
        Set<Integer> textIds = zoneIdToContentIdIsTextMap.values().stream()
                .parallel()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        Map<Integer, String> textIdToTextMap = httpTextService.textIdToText(textIds);
        List<ZoneText> zoneTexts = zoneIdToContentIdIsTextMap.entrySet().stream()
                // 时间从大到小
                .sorted((o2, o1) -> {
                    LocalDateTime o1PublishTime = zoneIdToPublishTimeMap.get(o1.getKey());
                    LocalDateTime o2PublishTime = zoneIdToPublishTimeMap.get(o2.getKey());
                    return o1PublishTime.compareTo(o2PublishTime);
                })
                .flatMap(n -> {
                    Integer zoneId = n.getKey();
                    LocalDateTime localDateTime = zoneIdToPublishTimeMap.get(zoneId);
                    return n.getValue().stream().map(m -> {
                        String text = textIdToTextMap.get(m);
                        return new ZoneText().setZoneId(zoneId).setText(text).setPublishTime(localDateTime);
                    });
                })
                .collect(Collectors.toList());
        return ResUtils.ok(zoneTexts);
    }

    /**
     * 根据说说获取我的相册的图片
     *
     * @param userId 用户ID
     * @return 图片ID
     */
    @PostMapping("my-photo-album")
    public R<List<Integer>> myPhotoAlbum(@SessionAttribute Long userId) {

        Set<Integer> zoneIds =
                iZoneService.userIdToZoneId(Collections.singleton(userId)).get(userId);
        Map<Integer, LocalDateTime> zoneIdToPublishTimeMap = iZoneService.zoneIdToPublishTime(zoneIds);
        Map<Integer, Set<Integer>> zoneIdToContentIdIsImageMap = iContentService.zoneIdToContentIdIsImage(zoneIds);
        List<Integer> imageIds = zoneIdToContentIdIsImageMap.entrySet().stream()
                // 时间从大到小
                .sorted((o2, o1) -> {
                    LocalDateTime o1PublishTime = zoneIdToPublishTimeMap.get(o1.getKey());
                    LocalDateTime o2PublishTime = zoneIdToPublishTimeMap.get(o2.getKey());
                    return o1PublishTime.compareTo(o2PublishTime);
                })
                .map(Map.Entry::getValue)
                .distinct()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return ResUtils.ok(imageIds);
    }

    /**
     * 列出热门的用户
     *
     * @param current 当前页
     * @param size    页面大小
     * @return 返回用户
     */
    @PostMapping("news")
    public R<Map<Integer, LocalDateTime>> news(int current, int size) {
        Map<Integer, LocalDateTime> news = iZoneService.news(current, size);
        return ResUtils.ok(news);
    }

    @Resource
    private HttpPhotoService httpPhotoService;

    @PostMapping("zone-id-to-zone-record")
    public R<Map<Integer, ZoneRecord>> zoneIdToZoneRecord(@RequestBody Set<Integer> zoneIds) {
        Map<Integer, LocalDateTime> zoneIdToPublishTimeMap = iZoneService.zoneIdToPublishTime(zoneIds);
        Map<Integer, Long> zoneIdToUserIdMap = iZoneService.zoneIdToUserId(zoneIds);

        Map<Integer, Set<Integer>> zoneIdToContentIdIsTextMap = iContentService.zoneIdToContentIdIsText(zoneIds);

        Map<Integer, Set<Integer>> zoneIdToContentIdIsImageMap = iContentService.zoneIdToContentIdIsImage(zoneIds);
        Map<Integer, Set<Integer>> zoneIdToReplyIdMap = iReplyService.zoneIdToReplyId(zoneIds);
        Set<Integer> replyIds =
                zoneIdToReplyIdMap.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
        Map<Integer, Reply> firstReplyIdToReply = iReplyService.idToZoneReplyFirst(replyIds);
        Map<Integer, Reply> secondReplyIdToReply = iReplyService.idToZoneReplySecond(replyIds);

        Map<Integer, Set<Reply>> firstReplyIdToReplyMap = secondReplyIdToReply.entrySet().stream()
                .collect(Collectors.groupingBy(
                        n -> firstReplyIdToReply.get(n.getValue().getReplyId()).getId(),
                        Collectors.mapping(Map.Entry::getValue, Collectors.toSet())));

        Set<Integer> zoneContentIds = firstReplyIdToReply.values().stream()
                .map(Reply::getZoneContentId)
                .collect(Collectors.toSet());
        Map<Integer, Set<Integer>> zoneContentIdToTextIdMap = iContentService.zoneContentIdToTextId(zoneContentIds);
        Set<Integer> textIds = Stream.concat(
                        zoneContentIdToTextIdMap.values().stream().flatMap(Collection::stream),
                        zoneIdToContentIdIsTextMap.values().stream().flatMap(Collection::stream))
                .parallel()
                .collect(Collectors.toSet());
        Map<Integer, String> textIdToTextMap = httpTextService.textIdToText(textIds);

        Set<Long> userIds = Stream.concat(
                        zoneIdToUserIdMap.values().stream(),
                        // 空间发表用户
                        Stream.concat(
                                firstReplyIdToReply.values().stream().parallel().map(Reply::getUserId) // 空间第一层评论用户
                                ,
                                secondReplyIdToReply.values().stream()
                                        .parallel()
                                        .map(Reply::getUserId) // 空间第二层评论用户
                                ))
                .collect(Collectors.toSet());
        Map<Long, Integer> userIdToProfilePhotoIdMap = infoService.userIdToProfilePhotoId(userIds);
        Set<Integer> photoIds = Stream.concat(
                        userIdToProfilePhotoIdMap.values().stream(),
                        zoneIdToContentIdIsImageMap.values().stream().flatMap(Collection::stream))
                .collect(Collectors.toSet());

        Map<Integer, String> photoIdToPhotoUrlMap = httpPhotoService.photoIdToPhotoUrl(photoIds);

        Map<Long, String> userIdToUserNameMap = infoService.userIdToUserName(userIds);

        // 第一层评论
        Map<Integer, ZoneReply> replyIdToZoneReplyMap = firstReplyIdToReply.entrySet().stream()
                .sorted(((o2, o1) ->
                        o1.getValue().getUpdateTime().compareTo(o2.getValue().getUpdateTime())))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        n -> {
                            Reply reply = n.getValue();
                            ZoneReply zoneReply = iReplyService.replyToZoneReply(
                                    reply, userIdToUserNameMap, zoneContentIdToTextIdMap, textIdToTextMap);
                            Set<Reply> secondReplys = firstReplyIdToReplyMap.get(reply.getId());
                            List<ZoneReply> collect = secondReplys.stream()
                                    .sorted((p2, p1) -> p1.getUpdateTime().compareTo(p2.getUpdateTime()))
                                    .map(m -> iReplyService.replyToZoneReply(
                                            m, userIdToUserNameMap, zoneContentIdToTextIdMap, textIdToTextMap))
                                    .collect(Collectors.toList());
                            zoneReply.setReplies(collect);
                            return zoneReply;
                        },
                        (o1, o2) -> o2));

        Map<Integer, ZoneRecord> zoneIdToZoneRecordMap = zoneIdToPublishTimeMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        n -> {
                            Integer zoneId = n.getKey();
                            Integer textId = zoneIdToContentIdIsTextMap.get(zoneId).stream()
                                    .findFirst()
                                    .orElse(null);
                            String text = "";
                            if (textId != null) {
                                text = textIdToTextMap.get(textId);
                            }
                            Long userId = zoneIdToUserIdMap.get(zoneId);
                            String userName = userIdToUserNameMap.get(userId);
                            Integer profilePhotoId = userIdToProfilePhotoIdMap.get(userId);
                            String headUrl = photoIdToPhotoUrlMap.get(profilePhotoId);
                            Set<Integer> zonePhotoIds = zoneIdToContentIdIsImageMap.get(zoneId);
                            List<String> photoUrls = zonePhotoIds.stream()
                                    .map(photoIdToPhotoUrlMap::get)
                                    .collect(Collectors.toList());

                            Set<Integer> replyIdss = zoneIdToReplyIdMap.get(zoneId);
                            if (replyIdss == null) {
                                replyIdss = Collections.emptySet();
                            }
                            List<ZoneReply> zoneReplys = replyIdss.stream()
                                    .map(replyIdToZoneReplyMap::get)
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.toList());

                            ZoneRecord zoneRecord = new ZoneRecord();
                            zoneRecord.setContent(text);
                            zoneRecord.setId(zoneId);
                            zoneRecord.setUserId(userId);
                            zoneRecord.setUserName(userName);
                            zoneRecord.setSendDateTime(n.getValue());
                            zoneRecord.setHeadUrl(headUrl);
                            zoneRecord.setPhotoUrls(photoUrls);
                            zoneRecord.setZoneReplys(zoneReplys);
                            return zoneRecord;
                        },
                        (o1, o2) -> o2));

        return ResUtils.ok(zoneIdToZoneRecordMap);
    }
}
