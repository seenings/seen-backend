package com.songchi.seen.info.controller;

import com.songchi.seen.coin.constant.CoinConstant;
import com.songchi.seen.coin.enumeration.TradeType;
import com.songchi.seen.common.model.R;
import com.songchi.seen.common.util.ResUtils;
import com.songchi.seen.info.http.HttpUserBirthdayService;
import com.songchi.seen.info.http.HttpUserSexService;
import com.songchi.seen.info.model.BasicInfo;
import com.songchi.seen.info.service.InfoService;
import com.songchi.seen.school.http.HttpEducationalService;
import com.songchi.seen.school.http.HttpSchoolGraduateService;
import com.songchi.seen.sys.constant.PublicConstant;
import com.songchi.seen.trade.http.HttpCoinTradeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * BasicInfoController
 *
 * @author chixuehui
 * @since 2022-10-06
 */
@RestController
@RequestMapping(PublicConstant.REST + "user/basic-info")
public class BasicInfoController {

    @Resource
    private HttpEducationalService httpEducationalService;

    @Resource
    private HttpUserSexService httpUserSexService;

    @Resource
    private HttpUserBirthdayService httpUserBirthdayService;

    @Resource
    private HttpSchoolGraduateService httpSchoolGraduateService;


    @Resource
    private HttpCoinTradeService httpCoinTradeService;

    @PostMapping("save-basic-info")
    public R<String> saveBasicInfo(@RequestBody BasicInfo basicInfo, @SessionAttribute Integer userId) {

        httpEducationalService.set(basicInfo.getUserId(), basicInfo.getEducation());
        httpUserSexService.set(basicInfo.getUserId(), basicInfo.getSex());
        httpUserBirthdayService.set(basicInfo.getUserId(), basicInfo.getBirthYear(), null, null);
        httpSchoolGraduateService.set(basicInfo.getUserId(), basicInfo.getGraduated() ? 1 : 0);
        // 填入基本信息时，初始化虚拟币，并赠送玫瑰花个数50
        httpCoinTradeService.simpleTradeTypeTo(userId, CoinConstant.FILL_BASIC_INFO_SPEND_COIN_AMOUNT, TradeType.FILL_BASIC_INFO);

        return ResUtils.ok(null);
    }

    @Resource
    private InfoService infoService;

    /**
     * 根据用户ID获取基本信息
     *
     * @param userIds 用户ID
     * @return 用户ID对应基本信息
     */
    @PostMapping("user-id-to-basic-info")
    public R<Map<Integer, BasicInfo>> userIdToBasicInfo(@RequestBody Set<Integer> userIds) {
        return ResUtils.ok(infoService.userIdToBasicInfo(userIds));
    }
}
