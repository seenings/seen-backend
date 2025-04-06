package io.github.seenings.chat.http;

import static io.github.seenings.sys.constant.SeenConstant.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.seenings.article.enumeration.ContentType;
import io.github.seenings.chat.model.ChatContentAndTime;
import io.github.seenings.sys.constant.ServiceNameConstant;

/**
 * HttpChatHistoryService
 *
 * @author chixuehui
 * @since 2022-10-09
 */
@FeignClient(
        name = ServiceNameConstant.SERVICE_SEEN_CHAT,
        path = FEIGN_VERSION + "chat/chat-history",
        contextId = "HttpChatHistoryService")
public interface HttpChatHistoryService {

    @PostMapping("add")
    Integer add(@RequestParam("contentType") ContentType contentType, @RequestParam("contentId") Integer contentId,
                @RequestParam("fromUserId") Long fromUserId,
                @RequestParam("toUserId") Long toUserId,
                @RequestParam("sent") Boolean sent,
                @RequestParam("sendTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                LocalDateTime sendTime);

    /**
     * 根据页面用户ID和自己的用户ID获取聊天记录ID
     *
     * @param pageUserId 页面用户ID
     * @param selfUserId 自己用户ID
     * @return 聊天记录ID
     */
    @PostMapping("page-user-id-to-history-id")
    List<Integer> pageUserIdToHistoryId(@RequestParam("pageUserId") Long pageUserId,
                                        @RequestParam("selfUserId") Long selfUserId);

    /**
     * 根据ID获取聊天内容和时间
     *
     * @param ids 聊天记录ID
     * @return 聊天记录ID对应聊天内容和时间
     */
    @PostMapping("id-to-chat-content-and-time")
    Map<Integer, ChatContentAndTime> idToChatContentAndTime(@RequestBody Set<Integer> ids);

    @PostMapping("user-id-to-chat-content-and-time")
    Map<Long, ChatContentAndTime> userIdToChatContentAndTime(
            @RequestBody Set<Long> userIds, @RequestParam("toUserId") Long toUserId);

    @PostMapping("set-sent")
    boolean setSent(@RequestParam("id") Integer id);

    /**
     * 根据聊天记录ID获取发送方ID
     *
     * @param ids 聊天记录ID
     * @return 聊天记录ID对应发送方ID
     */
    @PostMapping("id-to-from-user-id")
    Map<Integer, Long> idToFromUserId(@RequestBody Set<Integer> ids);

    /**
     * 根据聊天记录ID获取是否发出
     *
     * @param ids 聊天记录ID
     * @return 聊天记录ID对应是否发出
     */
    @PostMapping("id-to-is-sent")
    Map<Integer, Boolean> idToIsSent(@RequestBody Set<Integer> ids);

    /**
     * 根据聊天记录ID获取接收方ID
     *
     * @param ids 聊天记录ID
     * @return 聊天记录ID对应接收方ID
     */
    @PostMapping("id-to-to-user-id")
    Map<Integer, Long> idToToUserId(@RequestBody Set<Integer> ids);
}
