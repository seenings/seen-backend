package io.github.seenings.apply.http;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * HttpUserApplyAgreeService
 *
 * @author chixuehui
 * @since 2023-03-05
 */
@HttpExchange(
        value = FEIGN_VERSION + "chat/apply-agree" )
public interface HttpUserApplyAgreeService {
    @PostExchange("apply-id-to-agree-time")
    Map<Integer, LocalDateTime> applyIdToAgreeTime
            (@RequestBody Set<Integer> applyIds);

    @PostExchange("set")
    Integer set(@RequestParam("applyId") Integer applyId,
                @RequestParam("agreeTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime agreeTime);
}
