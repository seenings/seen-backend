package io.github.seenings.busi.controller;

import io.github.seenings.busi.model.BusiRegister;
import io.github.seenings.sys.constant.SeenConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

/**
 * 用户注册
 */
@HttpExchange(SeenConstant.SEEN_SMALL + "/busi/busi/busi-register")
public interface BusiRegisterController {
    /**
     * 查询所有
     *
     * @return 查询所有
     */
    @GetExchange("select")
    List<BusiRegister> select();

    /**
     * 增加用户注册
     *
     * @param busiRegister 用户注册信息
     * @return 用户注册ID
     */
    @PostExchange("insert")
    Long insert(@RequestBody BusiRegister busiRegister);
}
