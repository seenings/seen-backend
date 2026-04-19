package io.github.seenings.apply.http;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/// 拒绝申请
@HttpExchange( "chat/apply-refuse")
public interface HttpUserApplyRefuseService {
    @PostExchange("apply-id-to-refuse-time")
    Map<Integer, LocalDateTime> applyIdToRefuseTime(@RequestBody Set<Integer> applyIds );

    @PostExchange("add")
    Integer  add(@RequestParam("applyId") Integer applyId, @RequestParam("textId") Integer textId);
}
