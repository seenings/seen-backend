package com.songchi.seen.calc.service.impl;

import com.songchi.seen.calc.service.RecommendService;
import com.songchi.seen.sys.util.ListUtils;
import com.songchi.seen.core.util.SetUtils;
import com.songchi.seen.info.enumeration.Sex;
import com.songchi.seen.info.http.HttpUserCurrentResidenceService;
import com.songchi.seen.info.http.HttpUserMainPhotoService;
import com.songchi.seen.info.http.HttpUserSexService;
import com.songchi.seen.info.util.UserEnumUtils;
import com.songchi.seen.recommend.constant.RecommendConstant;
import com.songchi.seen.recommend.http.HttpMiddleUserRecommendService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * RecommendServiceImpl
 *
 * @author chixuehui
 * @since 2023-05-21
 */
@Service
public class RecommendServiceImpl implements RecommendService {

    @Resource
    private HttpUserCurrentResidenceService httpUserCurrentResidenceService;
    @Resource
    private HttpUserMainPhotoService httpUserMainPhotoService;
    @Resource
    private HttpMiddleUserRecommendService httpMiddleUserRecommendService;
    @Resource
    private HttpUserSexService httpUserSexService;

    /**
     * 硬性条件
     *
     * @param recommendSex 推荐的性别
     * @param current      当前页
     * @param size         大小
     * @return 被推荐的用户
     */
    @Override
    public List<Integer> mandatoryRecommendUser(Sex recommendSex, int current, int size) {
        // 1. 异性
        List<Integer> userIds = httpUserSexService.sexToUserId(recommendSex, current, size);
        // 2.匹配有头像的人
        Map<Integer, Integer> userIdPhotoIdMap = httpUserMainPhotoService.userIdPhotoId(new HashSet<>(userIds));
        return ListUtils.interset(userIds, userIdPhotoIdMap.keySet().stream().toList());
    }

    /**
     * 软性条件
     *
     * @param userId        用户ID
     * @param userIds       被推荐的用户ID
     * @param currentCityId 用户当前城市
     * @return 被推荐的用户
     */
    @Override
    public List<Integer> softRecommendUser(Integer userId, List<Integer> userIds, Integer currentCityId) {
        // 1.匹配同城
        Map<Integer, Integer> userIdToCityIdMap =
                httpUserCurrentResidenceService.userIdToCityId(new HashSet<>(userIds));
        Set<Integer> middleUserIds = userIdToCityIdMap.entrySet().stream()
                .parallel()
                .filter(n -> Objects.equals(n.getValue(), currentCityId))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        // 2.剔除已匹配的
        Set<Integer> haveRecommendUserIds = httpMiddleUserRecommendService.haveUserId(userId, middleUserIds);
        Set<Integer> okList = SetUtils.except(middleUserIds, haveRecommendUserIds);
        return okList.stream().toList();
    }

    @Override
    public List<Integer> createRecommendUser(Integer userId) {
        // 1.硬性条件里做推荐
        // 推荐异性
        Integer sex =
                httpUserSexService.userIdToSex(Collections.singleton(userId)).get(userId);
        Sex recommendSex = (UserEnumUtils.indexToSexEnum(sex) == Sex.FEMALE) ? Sex.MALE : Sex.FEMALE;
        List<Integer> resultList = new ArrayList<>();
        long total = httpUserSexService.sexCount(recommendSex);
        int size = RecommendConstant.RECOMMEND_COUNT;
        // 20,10 = 2;21,10=3，把总个数按推荐个数分块计算
        int count = BigDecimal.valueOf(total)
                .divide(BigDecimal.valueOf(size), RoundingMode.UP)
                .intValue();
        Integer currentCityId = httpUserCurrentResidenceService
                .userIdToCityId(Collections.singleton(userId))
                .get(userId);
        for (int i = 0; i < count; i++) {
            //硬性条件
            List<Integer> mandatoryRecommendUserIds = mandatoryRecommendUser(recommendSex, i, size);
            //软性条件
            List<Integer> okList = softRecommendUser(userId, mandatoryRecommendUserIds, currentCityId);
            // 本次结果有用户
            if (okList.size() > 0) {
                resultList = ListUtils.union(resultList, okList);
                if (resultList.size() >= size) {
                    //找够了
                    break;
                }
            }
        }
        //如果没找够，剔除软性条件来找
        if (resultList.size() < size) {
            for (int i = 0; i < count; i++) {
                //硬性条件
                List<Integer> okList = mandatoryRecommendUser(recommendSex, i, size);
                // 本次结果有用户
                if (okList.size() > 0) {
                    resultList = ListUtils.union(resultList, okList);
                    if (resultList.size() >= size) {
                        //找够了
                        break;
                    }
                }
            }
        }
        return resultList.stream().limit(size).toList();
    }
}
