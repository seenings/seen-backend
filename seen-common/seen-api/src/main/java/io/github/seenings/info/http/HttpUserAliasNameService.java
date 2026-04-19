package io.github.seenings.info.http;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;
import java.util.Set;

/// 昵称
@HttpExchange("user/user-alias-name")
public interface HttpUserAliasNameService {
    @PostExchange("user-id-to-alias-name")
    Map<Long, String> userIdToAliasName(@RequestBody Set<Long> userIds);

    @PostExchange("set")
    boolean set(@RequestParam("userId") Long userId, @RequestParam("aliasName") String aliasName);
}
