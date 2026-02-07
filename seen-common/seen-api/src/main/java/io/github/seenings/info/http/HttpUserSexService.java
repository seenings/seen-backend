package io.github.seenings.info.http;

import io.github.seenings.sys.constant.SeenConstant;
import io.github.seenings.sys.constant.ServiceNameConstant;
import io.github.seenings.info.enumeration.Sex;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * UserSexService
 *
 * @author chixuehui
 * @since 2022-10-06
 */
@HttpExchange(SeenConstant.FEIGN_VERSION + "user/sex")
public interface HttpUserSexService {
    @PostExchange("user-id-to-sex")
    Map<Long, Integer> userIdToSex(@RequestBody Set<Long> userIds);

    @PostExchange("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("sex") Sex sex);

    @PostExchange("sex-to-user-id")
    List<Long> sexToUserId(
            @RequestParam("sex") Sex sex, @RequestParam("current") int current, @RequestParam("size") int size);

    @PostExchange("sex-count")
    long sexCount(@RequestParam("sex") Sex sex);
}
