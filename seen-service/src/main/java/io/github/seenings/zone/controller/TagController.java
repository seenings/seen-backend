package io.github.seenings.zone.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import io.github.seenings.common.model.R;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.sys.constant.PublicConstant;
import io.github.seenings.zone.model.UserTag;
import io.github.seenings.zone.service.ITagService;
import io.github.seenings.zone.service.ResultRecommendService;

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
