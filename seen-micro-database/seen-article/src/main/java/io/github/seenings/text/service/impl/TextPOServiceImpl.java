package io.github.seenings.text.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.seenings.text.po.TextPO;
import io.github.seenings.text.service.TextService;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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

@AllArgsConstructor
@Repository
public class TextPOServiceImpl implements TextService {

    private TextPOMapper textPOMapper;

    /**
     * 保存文本
     * @param text  文本
     * @return  文本ID
     */
    @Override
    public Integer saveAndReturnId(String text) {
        TextPO po = new TextPO().setText(text);
        textPOMapper.insert(po);
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
                        subs -> textPOMapper.selectList(new QueryWrapper<TextPO>()
                                        .lambda()
                                        .in(TextPO::getId, subs)
                                        .eq(TextPO::getDeleted, false))
                                .stream())
                .collect(Collectors.toMap(TextPO::getId, TextPO::getText, (o1, o2) -> o2));
    }
}
