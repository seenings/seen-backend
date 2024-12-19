package com.songchi.seen.voice.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.core.util.CollUtils;
import com.songchi.seen.sys.util.ListUtils;
import com.songchi.seen.sys.constant.PublicConstant;
import com.songchi.seen.voice.entity.Voice;
import com.songchi.seen.voice.mapper.VoiceMapper;
import com.songchi.seen.voice.service.IVoiceService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 语音 服务实现类
 * </p>
 *
 * @author chixh
 * @since 2021-07-25
 */
@Service
public class VoiceServiceImpl extends ServiceImpl<VoiceMapper, Voice> implements IVoiceService {
    @Override
    public Integer setPath(String path, Integer userId) {
        Voice entity = new Voice();
        entity.setPath(path);
        save(entity);
        return entity.getId();
    }

    /**
     * 根据声音ID获取相对url
     *
     * @param voiceIds 声音ID
     * @return 声音ID对应相对url
     */
    @Override
    public Map<Integer, String> voiceIdToUrl(Set<Integer> voiceIds) {
        Map<Integer, String> voiceIdToPathMap = voiceIdToPath(voiceIds);
        return voiceIdToPathMap.entrySet().stream().parallel().collect(Collectors
                .toMap(Map.Entry::getKey, n -> PublicConstant.VOICE_VERSION + n.getValue()
                        , (o1, o2) -> o2));
    }

    /**
     * 根据声音ID获取相对路径
     *
     * @param voiceIds 声音ID
     * @return 声音ID对应相对路径
     */
    @Override
    public Map<Integer, String> voiceIdToPath(Set<Integer> voiceIds) {
        List<Integer> voiceIdList = ListUtils.valueIsNull(voiceIds);
        if (CollUtils.isEmpty(voiceIdList)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(voiceIdList, 500).stream().flatMap(subs -> list(new QueryWrapper<Voice>().lambda().in(Voice::getId, subs).select(Voice::getId, Voice::getPath)).stream()).collect(Collectors.toMap(Voice::getId, Voice::getPath, (o1, o2) -> o2));
    }
}
