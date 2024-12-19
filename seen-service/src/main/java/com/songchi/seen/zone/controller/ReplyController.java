package com.songchi.seen.zone.controller;


import com.songchi.seen.sys.constant.PublicConstant;
import com.songchi.seen.common.model.R;
import com.songchi.seen.common.util.ResUtils;
import com.songchi.seen.zone.service.IReplyService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.Collections;

/**
 * <p>
 * 空间回复 前端控制器
 * </p>
 *
 * @author chixh
 * @since 2021-07-25
 */
@RestController
@RequestMapping(PublicConstant.REST + "zone/reply")
public class ReplyController {

    @Resource
    private IReplyService iReplyService;

    /**
     * 发表空间评论
     *
     * @param zoneId  空间ID
     * @param message 评论消息
     * @param userId  用户
     * @return 新生成的回复ID
     */
    @PostMapping("publish-zone-comment")
    public R<Integer> publishZoneComment(Integer zoneId, String message, @SessionAttribute Integer userId) {

        Integer replyId = iReplyService.publishZoneComment(zoneId, userId, message);
        return ResUtils.ok(replyId);
    }

    /**
     * 回复空间评论
     *
     * @param replyId 评论空间的回复ID
     * @param message 回复消息
     * @param userId  用户
     * @return 新生成的回复ID
     */
    @PostMapping("publish-reply-comment")
    public R<Integer> publishReplyComment(Integer replyId, String message, @SessionAttribute Integer userId) {
        Integer zoneId = iReplyService.idToZoneId(Collections.singleton(replyId)).get(replyId);
        Integer resultReplyId = iReplyService.publishReplyComment(zoneId, replyId, userId, message);
        return ResUtils.ok(resultReplyId);
    }


}
