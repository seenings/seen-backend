package io.github.seenings.task.service;

/**
 * 做任务产生币
 */
public interface DoTaskToCoinService {
    /**
     * 分配币
     *
     * @param userId     用户
     * @param busiId     业务ID
     * @param coinAmount 币的数量
     */
    void doTask(Long userId, Long busiId, Long coinAmount);
}
