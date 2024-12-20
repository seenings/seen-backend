package com.songchi.seen.chat.controller;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.songchi.seen.article.enumeration.ContentType;
import com.songchi.seen.chat.http.HttpChatHistoryService;
import com.songchi.seen.chat.http.HttpChatUserService;
import com.songchi.seen.chat.http.HttpPushChatMessageService;
import com.songchi.seen.chat.model.ChatContentAndTime;
import com.songchi.seen.chat.model.ChatUser;
import com.songchi.seen.chat.model.RemoteChatMessage;
import com.songchi.seen.chat.model.SingleContent;
import com.songchi.seen.chat.model.UserChatInfo;
import com.songchi.seen.common.model.R;
import com.songchi.seen.common.model.ResultPage;
import com.songchi.seen.common.util.ResUtils;
import com.songchi.seen.info.service.InfoService;
import com.songchi.seen.sys.constant.PublicConstant;
import com.songchi.seen.text.http.HttpTextService;

import cn.hutool.core.lang.Pair;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 可以聊天的用户列表 前端控制器
 * </p>
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(PublicConstant.REST + "chat/chat-user")
public class ChatUserController {

    private HttpTextService httpTextService;
    private HttpChatHistoryService httpChatHistoryService;
    private HttpChatUserService httpChatUserService;
    private InfoService infoService;
    /**
     * 推送消息实现
     */
    private HttpPushChatMessageService httpPushChatMessageService;

    @PostMapping("set-friend")
    public R<Boolean> setFriend(@RequestParam("friendUserId") Integer friendUserId, @SessionAttribute Integer userId) {
        boolean set = httpChatUserService.set(friendUserId, userId);
        return ResUtils.ok(set);
    }

    @PostMapping("history-id-to-remote-chat-message")
    public R<Map<Integer, RemoteChatMessage>> historyIdToRemoteChatMessage(@RequestBody Set<Integer> historyIds) {

        Map<Integer, Integer> historyIdToToUserIdMap = httpChatHistoryService.idToToUserId(historyIds);
        Map<Integer, Integer> historyIdToFromUserIdMap = httpChatHistoryService.idToFromUserId(historyIds);
        Map<Integer, Boolean> historyIdToIsSentMap = httpChatHistoryService.idToIsSent(historyIds);
        Map<Integer, ChatContentAndTime> historyIdToChatContentAndTimeMap = httpChatHistoryService
                .idToChatContentAndTime(historyIds);
        Map<Integer, RemoteChatMessage> result = historyIds.stream().parallel().map(id -> {
            ChatContentAndTime chatContentAndTime = historyIdToChatContentAndTimeMap.get(id);
            Integer fromUserId = historyIdToFromUserIdMap.get(id);
            Integer toUserId = historyIdToToUserIdMap.get(id);
            Boolean sent = historyIdToIsSentMap.get(id);
            return Pair.of(id, new RemoteChatMessage(id, chatContentAndTime.contentType().getIndex(),
                    chatContentAndTime.contentId(), fromUserId, chatContentAndTime.sendTime(), sent, toUserId));
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        return ResUtils.ok(result);
    }

    @PostMapping("page-to-user-chat-info")
    public R<List<UserChatInfo>> pageToUserChatInfo(@RequestParam int current, @RequestParam int size,
            @SessionAttribute Integer userId) {

        ResultPage<ChatUser> chatUsers = httpChatUserService.page(userId, current, size);
        Set<Integer> userIds = chatUsers.getPageList().stream().parallel().map(ChatUser::friendUserId)
                .collect(Collectors.toSet());
        log.info("用户ID：{}。", userIds);
        Map<Integer, UserChatInfo> userIdToUserChatInfoMap = infoService.userIdToUserChatInfo(userIds, userId);

        List<UserChatInfo> collect = chatUsers.getPageList().stream().map(n -> {
            Integer friendUserId = n.friendUserId();
            return userIdToUserChatInfoMap.get(friendUserId);
        }).collect(Collectors.toList());
        return ResUtils.ok(collect);
    }

    @PostMapping("send-chat-text-message")
    public R<Integer> sendChatTextMessage(@RequestBody SingleContent singleContent,
            @RequestParam Integer toUserId, @SessionAttribute Integer userId) {
        Integer textId = httpTextService.saveAndReturnId(singleContent.value());
        Integer chatHistoryId = httpChatHistoryService.add(ContentType.TEXT, textId, userId, toUserId, false,
                LocalDateTime.now());
        // 存储，调用微服务，推送消息

        httpPushChatMessageService.send(chatHistoryId);
        return ResUtils.ok(chatHistoryId);
    }

    @PostMapping("send-voice-message")
    public R<Integer> sendVoiceMessage(@RequestParam Integer voiceId,
            @RequestParam Integer toUserId, @SessionAttribute Integer userId) {
        Integer chatHistoryId = httpChatHistoryService.add(ContentType.VOICE, voiceId, userId, toUserId, false,
                LocalDateTime.now());
        // 存储，调用微服务，推送消息
        httpPushChatMessageService.send(chatHistoryId);
        return ResUtils.ok(chatHistoryId);
    }

    @PostMapping("send-photo-message")
    public R<Integer> sendPhotoMessage(@RequestParam Integer photoId,
            @RequestParam Integer toUserId, @SessionAttribute Integer userId) {
        Integer chatHistoryId = httpChatHistoryService.add(ContentType.IMAGE, photoId, userId, toUserId, false,
                LocalDateTime.now());
        // 存储，调用微服务，推送消息
        httpPushChatMessageService.send(chatHistoryId);
        return ResUtils.ok(chatHistoryId);
    }

    @PostMapping("user-id-to-history-id")
    public R<Map<Integer, Set<Integer>>> userIdToHistoryId(@RequestBody Set<Integer> pageUserIds,
            @SessionAttribute Integer userId) {
        Map<Integer, Set<Integer>> collect = pageUserIds.stream().parallel().map(n -> {
            List<Integer> list = httpChatHistoryService.pageUserIdToHistoryId(n, userId);
            return Pair.of(n, new HashSet<>(list));
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        return ResUtils.ok(collect);
    }
}
