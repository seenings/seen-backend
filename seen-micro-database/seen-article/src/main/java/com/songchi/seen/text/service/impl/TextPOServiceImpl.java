package com.songchi.seen.text.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.songchi.seen.text.po.TextPO;
import com.songchi.seen.text.service.TextService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TextPOServiceImpl
 *
 * @author chixuehui
 * @since 2022-10-16
 */
@Mapper
interface TextPOMapper extends BaseMapper<TextPO> {}

@Service
public class TextPOServiceImpl extends ServiceImpl<TextPOMapper, TextPO> implements TextService {

    /**
     * 保存文本
     * @param text  文本
     * @return  文本ID
     */
    @Override
    public Integer saveAndReturnId(String text) {
        TextPO po = new TextPO().setText(text);
        save(po);
        return po.getId();
    }

    /**
     * 根据文本ID获取文本
     *
     * @param textIds 文本ID
     * @return 文本ID对应文本
     */
    @Override
    public Map<Integer, String> textIdToText(Set<Integer> textIds) {
        if (CollUtil.isEmpty(textIds)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(textIds), 100).stream()
                .parallel()
                .flatMap(
                        subs -> list(new QueryWrapper<TextPO>()
                                        .lambda()
                                        .in(TextPO::getId, subs)
                                        .eq(TextPO::getDeleted, false))
                                .stream())
                .collect(Collectors.toMap(TextPO::getId, TextPO::getText, (o1, o2) -> o2));
    }
}
