package io.github.seenings.calc.controller;

import io.github.seenings.calc.http.HttpRecommendService;
import io.github.seenings.calc.service.RecommendService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.github.seenings.sys.constant.SeenConstant.FEIGN_VERSION;

/**
 * RecommendController
 *
 * @author chixuehui
 * @since 2023-05-21
 */
@RestController
@RequestMapping(FEIGN_VERSION + "calc/recommend")
@AllArgsConstructor
public class RecommendController implements HttpRecommendService {

    private RecommendService recommendService;


    /**
     * 为用户推荐用户
     *
     * @param userId 用户ID
     * @return 被推荐的用户
     */
    @Override
    @PostMapping("create-recommend-user")
    public List<Long> createRecommendUser(@RequestParam("userId") Long userId) {
        return recommendService.createRecommendUser(userId);
    }
}
