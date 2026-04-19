package io.github.seenings.apply.http;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/// 查看申请
@HttpExchange( "chat/apply-look")
public interface HttpUserApplyLookService {
    @PostExchange("apply-id-to-look-time")
    Map<Integer, LocalDateTime> applyIdToLookTime(@RequestBody Set<Integer> applyIds);

    @PostExchange("set")
    Integer set(@RequestParam("applyId") Integer applyId, @RequestParam("lookTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lookTime);
}
