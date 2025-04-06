package io.github.seenings.coin.enumeration;

import lombok.Getter;

/**
 * TradeType
 *
 * @author chixuehui
 * @since 2023-01-01
 */
@Getter
public enum TradeType {
    //普通交易类型，没有详细的数据记录
    SIGN_UP(1, "注册"),
    FILL_BASIC_INFO(2, "填入基本信息"),
    FILL_FULL_BASIC_INFO(3, "完善基本资料"),
    //复杂交易类型，有详细的数据记录
    SIGN_IN(101, "平台签到"),
    RECHARGE_COIN(201, "充值虚拟币"),
    APPLY_DO_FRIEND(301, "申请成为好友"),
    AGREE_DO_FRIEND(302, "同意做朋友"),
    REFUSE_DO_FRIEND(303, "拒绝做朋友"),
    EXPIRE_DO_FRIEND(304, "申请过期"),
    VIEW(401, "查看用户资料"),
    APPLY_USER(402, "邀请用户"),
    ;
    private final int index;
    private final String label;

    TradeType(int index, String label) {
        this.index = index;
        this.label = label;
    }
}
