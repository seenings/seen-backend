package io.github.seenings.task.api.impl;

import io.github.seenings.task.api.DoTaskApi;
import io.github.seenings.task.service.DoTaskToCoinService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * 做任务
 */
@Slf4j
@RestController
@AllArgsConstructor
public class DoTaskApiImpl implements DoTaskApi {
    /**
     * 做任务产生币
     */
    private DoTaskToCoinService doTaskToCoinService;

    /**
     * 分配币
     *
     * @param userId     用户
     * @param busiId     业务ID
     * @param coinAmount 币的数量
     */
    @Override
    public void doTaskGetCoin(Long userId, Long busiId, Long coinAmount) {
        doTaskToCoinService.doTask(userId, busiId, coinAmount);
    }
}
