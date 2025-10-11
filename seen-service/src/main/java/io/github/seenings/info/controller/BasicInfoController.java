package io.github.seenings.info.controller;

import io.github.seenings.busi.controller.BusiController;
import io.github.seenings.busi.model.Busi;
import io.github.seenings.coin.constant.CoinConstant;
import io.github.seenings.coin.enumeration.BusiType;
import io.github.seenings.common.model.R;
import io.github.seenings.common.util.ResUtils;
import io.github.seenings.info.http.HttpUserBirthdayService;
import io.github.seenings.info.http.HttpUserSexService;
import io.github.seenings.info.model.BasicInfo;
import io.github.seenings.info.service.InfoService;
import io.github.seenings.school.http.HttpEducationalService;
import io.github.seenings.school.http.HttpSchoolGraduateService;
import io.github.seenings.sys.constant.PublicConstant;
import io.github.seenings.task.api.DoTaskApi;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * BasicInfoController
 *
 * @author chixuehui
 * @since 2022-10-06
 */
@RestController
@AllArgsConstructor
@RequestMapping(PublicConstant.REST + "user/basic-info")
public class BasicInfoController {

    private HttpEducationalService httpEducationalService;

    private HttpUserSexService httpUserSexService;

    private HttpUserBirthdayService httpUserBirthdayService;

    private HttpSchoolGraduateService httpSchoolGraduateService;

    private InfoService infoService;

    /**
     * 业务
     */
    private BusiController busiController;
    /**
     * 做任务
     */
    private DoTaskApi doTaskApi;

    @PostMapping("save-basic-info")
    public R<String> saveBasicInfo(@RequestBody BasicInfo basicInfo, @SessionAttribute Long userId) {

        httpEducationalService.set(basicInfo.getUserId(), basicInfo.getEducation());
        httpUserSexService.set(basicInfo.getUserId(), basicInfo.getSex());
        httpUserBirthdayService.set(basicInfo.getUserId(), basicInfo.getBirthYear(), null, null);
        httpSchoolGraduateService.set(basicInfo.getUserId(), basicInfo.getGraduated() ? 1 : 0);
        // 填入基本信息时，初始化虚拟币，并赠送玫瑰花个数50
        //TODO 业务处理
        long busiId = busiController.insert(new Busi().setBusiTime(LocalDateTime.now()).setBusiTypeId(BusiType.FILL_BASIC_INFO.getIndex()));
        doTaskApi.doTaskGetCoin(userId, busiId, (long) CoinConstant.FILL_BASIC_INFO_SPEND_COIN_AMOUNT);

        return ResUtils.ok(null);
    }


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
