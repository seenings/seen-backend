package io.github.seenings.voice.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.seenings.core.util.CollUtil;
import io.github.seenings.sys.util.ListUtils;
import io.github.seenings.sys.constant.PublicConstant;
import io.github.seenings.voice.entity.Voice;
import io.github.seenings.voice.mapper.VoiceMapper;
import io.github.seenings.voice.service.IVoiceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

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
@AllArgsConstructor
@Repository
public class VoiceServiceImpl  implements IVoiceService {

    private VoiceMapper voiceMapper;
    @Override
    public Integer setPath(String path, Long userId) {
        Voice entity = new Voice();
        entity.setPath(path);
        voiceMapper.insert(entity);
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
        if (CollUtil.isEmpty(voiceIdList)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(voiceIdList, 500).stream().flatMap(subs -> voiceMapper.selectList(new QueryWrapper<Voice>().lambda().in(Voice::getId, subs).select(Voice::getId, Voice::getPath)).stream()).collect(Collectors.toMap(Voice::getId, Voice::getPath, (o1, o2) -> o2));
    }
}
