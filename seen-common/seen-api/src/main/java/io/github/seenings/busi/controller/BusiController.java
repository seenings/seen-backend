package io.github.seenings.busi.controller;

import io.github.seenings.busi.model.Busi;
import io.github.seenings.sys.constant.SeenConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * 业务
 */
@HttpExchange(SeenConstant.SEEN_SMALL + "/busi/busi/busi")
public interface BusiController {
    /**
     * 业务
     *
     * @param busi 业务
     * @return 业务ID
     */
    @PostExchange("insert")
    Long insert(@RequestBody Busi busi);
}
