package com.songchi.seen.trade.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.trade.po.TradeRegisterPO;
import com.songchi.seen.trade.service.TradeRegisterService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

/**
 * TradeRegisterPOServiceImpl
 *
 * @author chixuehui
 * @since 2023-02-19
 */
@Mapper
interface TradeRegisterPOMapper extends BaseMapper<TradeRegisterPO> {}

@Service
public class TradeRegisterPOServiceImpl extends ServiceImpl<TradeRegisterPOMapper, TradeRegisterPO>
        implements TradeRegisterService {



}
