package io.github.seenings.voice.service;

import io.github.seenings.voice.entity.Voice;

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
public interface IVoiceService {

    Integer setPath(String path, Long userId);

    Map<Integer, String> voiceIdToUrl(Set<Integer> voiceIds);

    Map<Integer, String> voiceIdToPath(Set<Integer> voiceIds);
}
