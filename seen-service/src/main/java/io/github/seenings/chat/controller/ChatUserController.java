package io.github.seenings.chat.controller;

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

import io.github.seenings.article.enumeration.ContentType;
import io.github.seenings.chat.http.HttpChatHistoryService;
import io.github.seenings.chat.http.HttpChatUserService;
import io.github.seenings.chat.http.HttpPushChatMessageService;
import io.github.seenings.chat.model.ChatContentAndTime;
import io.github.seenings.chat.model.ChatUser;
import io.github.seenings.chat.model.RemoteChatMessage;
import io.github.seenings.chat.model.SingleContent;
import io.github.seenings.chat.model.UserChatInfo;
import io.github.seenings.common.model.R;
import io.github.seenings.common.model.ResultPage;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.info.service.InfoService;
import io.github.seenings.sys.constant.PublicConstant;
import io.github.seenings.text.http.HttpTextService;

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
    public R<Boolean> setFriend(@RequestParam("friendUserId") Long friendUserId, @SessionAttribute Long userId) {
        boolean set = httpChatUserService.set(friendUserId, userId);
        return ResUtils.ok(set);
    }

    @PostMapping("history-id-to-remote-chat-message")
    public R<Map<Integer, RemoteChatMessage>> historyIdToRemoteChatMessage(@RequestBody Set<Integer> historyIds) {

        Map<Integer, Long> historyIdToToUserIdMap = httpChatHistoryService.idToToUserId(historyIds);
        Map<Integer, Long> historyIdToFromUserIdMap = httpChatHistoryService.idToFromUserId(historyIds);
        Map<Integer, Boolean> historyIdToIsSentMap = httpChatHistoryService.idToIsSent(historyIds);
        Map<Integer, ChatContentAndTime> historyIdToChatContentAndTimeMap = httpChatHistoryService
                .idToChatContentAndTime(historyIds);
        Map<Integer, RemoteChatMessage> result = historyIds.stream().parallel().map(id -> {
            ChatContentAndTime chatContentAndTime = historyIdToChatContentAndTimeMap.get(id);
            Long fromUserId = historyIdToFromUserIdMap.get(id);
            Long toUserId = historyIdToToUserIdMap.get(id);
            Boolean sent = historyIdToIsSentMap.get(id);
            return Pair.of(id, new RemoteChatMessage(id, chatContentAndTime.contentType().getIndex(),
                    chatContentAndTime.contentId(), fromUserId, chatContentAndTime.sendTime(), sent, toUserId));
        }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        return ResUtils.ok(result);
    }

    @PostMapping("page-to-user-chat-info")
    public R<List<UserChatInfo>> pageToUserChatInfo(@RequestParam int current, @RequestParam int size,
                                                    @SessionAttribute Long userId) {

        ResultPage<ChatUser> chatUsers = httpChatUserService.page(userId, current, size);
        Set<Long> userIds = chatUsers.getPageList().stream().parallel().map(ChatUser::friendUserId)
                .collect(Collectors.toSet());
        log.info("用户ID：{}。", userIds);
        Map<Long, UserChatInfo> userIdToUserChatInfoMap = infoService.userIdToUserChatInfo(userIds, userId);

        List<UserChatInfo> collect = chatUsers.getPageList().stream().map(n -> {
            Long friendUserId = n.friendUserId();
            return userIdToUserChatInfoMap.get(friendUserId);
        }).collect(Collectors.toList());
        return ResUtils.ok(collect);
    }

    @PostMapping("send-chat-text-message")
    public R<Integer> sendChatTextMessage(@RequestBody SingleContent singleContent,
                                          @RequestParam Long toUserId, @SessionAttribute Long userId) {
        Integer textId = httpTextService.saveAndReturnId(singleContent.value());
        Integer chatHistoryId = httpChatHistoryService.add(ContentType.TEXT, textId, userId, toUserId, false,
                LocalDateTime.now());
        // 存储，调用微服务，推送消息

        httpPushChatMessageService.send(chatHistoryId);
        return ResUtils.ok(chatHistoryId);
    }

    @PostMapping("send-voice-message")
    public R<Integer> sendVoiceMessage(@RequestParam Integer voiceId,
                                       @RequestParam Long toUserId, @SessionAttribute Long userId) {
        Integer chatHistoryId = httpChatHistoryService.add(ContentType.VOICE, voiceId, userId, toUserId, false,
                LocalDateTime.now());
        // 存储，调用微服务，推送消息
        httpPushChatMessageService.send(chatHistoryId);
        return ResUtils.ok(chatHistoryId);
    }

    @PostMapping("send-photo-message")
    public R<Integer> sendPhotoMessage(@RequestParam Integer photoId,
                                       @RequestParam Long toUserId, @SessionAttribute Long userId) {
        Integer chatHistoryId = httpChatHistoryService.add(ContentType.IMAGE, photoId, userId, toUserId, false,
                LocalDateTime.now());
        // 存储，调用微服务，推送消息
        httpPushChatMessageService.send(chatHistoryId);
        return ResUtils.ok(chatHistoryId);
    }

    @PostMapping("user-id-to-history-id")
    public R<Map<Long, Set<Integer>>> userIdToHistoryId(@RequestBody Set<Long> pageUserIds,
                                                        @SessionAttribute Long userId) {
        Map<Long, Set<Integer>> collect = pageUserIds.stream().parallel().map(n -> {
            List<Integer> list = httpChatHistoryService.pageUserIdToHistoryId(n, userId);
            return Map.entry(n, new HashSet<>(list));
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return ResUtils.ok(collect);
    }
}
