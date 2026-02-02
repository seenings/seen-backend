package io.github.seenings.calc.controller;

import io.github.seenings.calc.http.HttpRecommendService;
import io.github.seenings.calc.service.RecommendService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/// 推荐
@RestController
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
    public List<Long> createRecommendUser(Long userId) {
        return recommendService.createRecommendUser(userId);
    }
}
