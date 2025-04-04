package com.songchi.seen.zone.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.songchi.seen.common.model.R;
import com.songchi.seen.common.util.ResUtils;
import com.songchi.seen.sys.constant.PublicConstant;
import com.songchi.seen.zone.model.UserTag;
import com.songchi.seen.zone.service.ITagService;
import com.songchi.seen.zone.service.ResultRecommendService;

import jakarta.annotation.Resource;

@RestController
@RequestMapping(PublicConstant.REST + "zone/tag")
public class TagController {

    @Resource
    private ITagService tagService;

    @Resource
    private ResultRecommendService resultRecommendService;

    @PostMapping("user-id-to-recommend-user-id")
    public R<List<Long>> userIdToRecommendUserId(@SessionAttribute Long userId) {
        List<Long> recommendUserIds = resultRecommendService.userIdToRecommendUserId(userId);
        return ResUtils.ok(recommendUserIds);
    }

    @PostMapping("tag")
    public R<Map<Long, UserTag>> hotTag(@RequestBody Set<Long> userIds) {
        Map<Long, UserTag> userIdToUserTag = tagService.userIdToUserTag(userIds);
        return ResUtils.ok(userIdToUserTag);
    }

    /**
     * 根据父标签名获取标签名
     *
     * @param tagParentNames 父标签名
     * @return 父标签名对应标签名
     */
    @PostMapping("tag-parent-name-to-tag-name")
    public R<Map<String, String>> tagParentNameToTagName(
            @RequestBody Set<String> tagParentNames, @RequestParam Long otherUserId) {
        Map<String, String> tagParentNameToTagName = tagService.tagParentNameToTagName(tagParentNames, otherUserId);
        return ResUtils.ok(tagParentNameToTagName);
    }
}
