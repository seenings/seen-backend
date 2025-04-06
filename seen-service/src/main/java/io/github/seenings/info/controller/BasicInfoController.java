package io.github.seenings.info.controller;

import io.github.seenings.coin.constant.CoinConstant;
import io.github.seenings.coin.enumeration.TradeType;
import io.github.seenings.common.model.R;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.info.http.HttpUserBirthdayService;
import io.github.seenings.info.http.HttpUserSexService;
import io.github.seenings.info.model.BasicInfo;
import io.github.seenings.info.service.InfoService;
import io.github.seenings.school.http.HttpEducationalService;
import io.github.seenings.school.http.HttpSchoolGraduateService;
import io.github.seenings.sys.constant.PublicConstant;
import io.github.seenings.trade.http.HttpCoinTradeService;
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
    public R<String> saveBasicInfo(@RequestBody BasicInfo basicInfo, @SessionAttribute Long userId) {

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
    public R<Map<Long, BasicInfo>> userIdToBasicInfo(@RequestBody Set<Long> userIds) {
        return ResUtils.ok(infoService.userIdToBasicInfo(userIds));
    }
}
