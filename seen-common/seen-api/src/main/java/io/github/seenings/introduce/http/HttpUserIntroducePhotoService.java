package io.github.seenings.introduce.http;

import static io.github.seenings.sys.constant.SeenConstant.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.github.seenings.article.http.HttpSeenArticleService;
import io.github.seenings.introduce.enumeration.IntroduceTypeEnum;
import io.github.seenings.introduce.model.IntroduceTypeAndPhoto;
import io.github.seenings.introduce.model.OrderAndPhotoId;
import io.github.seenings.sys.constant.ServiceNameConstant;

/**
 * HttpUserIntroducePhotoService
 *
 * @author chixuehui
 * @since 2022-11-27
 */
@FeignClient(name = ServiceNameConstant.SERVICE_SEEN_ARTICLE, path = FEIGN_VERSION
        + "introduce/user-introduce-photo", contextId = "HttpUserIntroducePhotoService")
public interface HttpUserIntroducePhotoService extends HttpSeenArticleService {

    /**
     * 根据用户ID获取介绍照片信息
     *
     * @param userIds 用户ID
     * @return 用户ID对应介绍照片信息
     */
    @PostMapping("user-id-to-introduce-type-and-photo")
    Map<Long, Set<IntroduceTypeAndPhoto>> userIdToIntroduceTypeAndPhoto(@RequestBody Set<Long> userIds);

    /**
     * 存入多个照片
     *
     * @param userId           用户ID
     * @param orderAndPhotoIds 介绍照片
     * @return 存入的介绍照片信息ID
     */
    @PostMapping("save-and-return-id")
    Set<Integer> saveAndReturnId(@RequestBody List<OrderAndPhotoId> orderAndPhotoIds,
            @RequestParam Integer max, @RequestParam IntroduceTypeEnum introduceTypeEnum,
            @RequestParam("userId") Long userId);
}
