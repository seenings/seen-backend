package io.github.seenings.file.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.file.entity.FileContentPo;
import io.github.seenings.file.mapper.FileContentPoMapper;
import io.github.seenings.file.model.FileContent;
import io.github.seenings.file.service.FileContentPoService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;

/**
 *
 */
@Service
public class FileContentPoServiceImpl extends ServiceImpl<FileContentPoMapper, FileContentPo>
        implements FileContentPoService {

    /**
     * 根据文件ID获取文件内容
     *
     * @param fileIds 文件ID
     * @return 文件内容
     */
    @Override
    public Map<Integer, FileContent> fileIdToFileContent(Set<Integer> fileIds) {

        if (CollUtil.isEmpty(fileIds)) {
            return Collections.emptyMap();
        }
        return ListUtil.partition(new ArrayList<>(fileIds), 100).stream().parallel().flatMap(subs ->
                list(new QueryWrapper<FileContentPo>().lambda().in(FileContentPo::getId, fileIds)
                ).stream()).collect(Collectors.toMap(FileContentPo::getId, n -> new FileContent()
                        .setFileContent(n.getFileContent()).setFileName(n.getFileName()).setId(n.getId())
                , (o1, o2) -> o2));
    }

    /**
     * 保存文件
     *
     * @param fileContent 文件内容
     * @return 保存成功
     */
    @Override
    public Integer insert(FileContent fileContent) {
        FileContentPo entity = new FileContentPo().setFileContent(fileContent.getFileContent())
                .setFileName(fileContent.getFileName());
        save(entity);
        return entity.getId();
    }
}




