package io.github.seenings.coin.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.seenings.coin.po.TradeRegisterPO;
import io.github.seenings.trade.service.TradeRegisterService;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * TradeRegisterPOServiceImpl
 *
 * @author chixuehui
 * @since 2023-02-19
 */
@Mapper
interface TradeRegisterPOMapper extends BaseMapper<TradeRegisterPO> {
}

@AllArgsConstructor
@Repository
public class TradeRegisterPOServiceImpl
        implements TradeRegisterService {


}
