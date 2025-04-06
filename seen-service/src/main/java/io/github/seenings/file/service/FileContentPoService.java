package io.github.seenings.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.github.seenings.file.entity.FileContentPo;
import io.github.seenings.file.model.FileContent;

import java.util.Map;
import java.util.Set;

/**
 *
 */
public interface FileContentPoService extends IService<FileContentPo> {

    Map<Integer, FileContent> fileIdToFileContent(Set<Integer> fileIds);

    Integer insert(FileContent fileContent);
}
