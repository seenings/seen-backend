package com.songchi.seen.voice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.songchi.seen.voice.entity.Voice;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 语音 Mapper 接口
 * </p>
 *
 * @author chixh
 * @since 2021-07-25
 */
@Mapper
public interface VoiceMapper extends BaseMapper<Voice> {}
