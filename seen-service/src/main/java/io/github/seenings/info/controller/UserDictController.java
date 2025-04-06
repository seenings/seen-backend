package io.github.seenings.info.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.seenings.common.model.R;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.info.enumeration.YearIncomeEnum;
import io.github.seenings.info.http.HttpWorkPositionService;
import io.github.seenings.sys.constant.PublicConstant;

import jakarta.annotation.Resource;

/**
 * <p>
 * 用户字典 前端控制器
 * </p>
 *
 * @author chixh
 * @since 2023-02-05
 */
@RestController
@RequestMapping(PublicConstant.REST + "user/dict")
public class UserDictController {
    @Resource
    private HttpWorkPositionService httpWorkPositionService;

    @GetMapping("work-position")
    public R<Map<Integer, String>> workPosition() {
        Map<Integer, String> map = httpWorkPositionService.workPosition();
        return ResUtils.ok(map);
    }

    @GetMapping("year-income")
    public R<Map<Integer, String>> yearIncome() {
        Map<Integer, String> map = Arrays.stream(YearIncomeEnum.values())
                .collect(Collectors.toMap(YearIncomeEnum::getIndex, YearIncomeEnum::getName, (o1, o2) -> o2));
        return ResUtils.ok(map);
    }
}
