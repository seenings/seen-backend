package com.songchi.seen.voice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.songchi.seen.voice.entity.Voice;

import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 语音 服务类
 * </p>
 *
 * @author chixh
 * @since 2021-07-25
 */
public interface IVoiceService extends IService<Voice> {

    Integer setPath(String path, Integer userId);

    Map<Integer, String> voiceIdToUrl(Set<Integer> voiceIds);

    Map<Integer, String> voiceIdToPath(Set<Integer> voiceIds);
}
