package com.songchi.seen.chat.service;

import com.songchi.seen.article.enumeration.ContentType;
import com.songchi.seen.chat.model.ChatContentAndTime;
import com.songchi.seen.chat.model.ChatMessage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ChatHistoryService
 *
 * @author chixuehui
 * @since 2022-10-09
 */
public interface ChatHistoryService {

    List<Integer> fromUserIdToId(Long fromUserId, Long toUserId, int current, int size);


    Map<Integer, ChatContentAndTime> idToChatContentAndTime(Set<Integer> ids);

    Map<Integer, Long> idToFromUserId(Set<Integer> ids);

    Map<Integer, LocalDateTime> idToSendTime(Set<Integer> ids);

    Map<Integer, Boolean> idToIsSent(Set<Integer> ids);

    Map<Integer, Long> idToToUserId(Set<Integer> ids);

    boolean add(ChatMessage chatMessage, LocalDateTime
            sendTime, boolean sent);

    Integer add(ContentType contentType, Integer contentId,
                Long fromUserId,
                Long toUserId, Boolean sent, LocalDateTime sendTime);

    boolean setSent(Integer id);
}
