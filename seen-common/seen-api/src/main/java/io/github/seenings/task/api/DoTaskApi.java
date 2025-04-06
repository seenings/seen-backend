package io.github.seenings.task.api;


import io.github.seenings.sys.constant.PublicConstant;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * 做任务
 */
@HttpExchange(PublicConstant.REST + "/task/do-task")
public interface DoTaskApi {
    /**
     * 分配币
     *
     * @param userId     用户
     * @param busiId     业务ID
     * @param coinAmount 币的数量
     */
    @PostExchange("do-task-get-coin")
    void doTaskGetCoin(@RequestParam("userId") Long userId, @RequestParam("busiId") Long busiId, @RequestParam("coinAmount") Long coinAmount);
}
