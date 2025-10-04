package io.github.seenings.file.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.file.enumeration.StorageType;
import io.github.seenings.file.po.FilePO;
import io.github.seenings.file.service.FilePOService;
import io.github.seenings.sys.util.ListUtils;
import io.github.seenings.sys.util.ToEnumerationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 文件
 */
@Mapper
interface FilePOMapper extends BaseMapper<FilePO> {
}

/**
 * 文件
 */
@Slf4j
@Service
public class FilePOServiceImpl extends ServiceImpl<FilePOMapper, FilePO> implements FilePOService {

    /**
     * 根据文件ID获取存储类型
     *
     * @param ids 文件ID
     * @return 文件ID对应存储类型
     */
    @Override
    public Map<Integer, StorageType> idToStorageType(Set<Integer> ids) {

        return ListUtil.partition(ListUtils.valueIsNull(ids), 100).parallelStream().flatMap(subs -> list(new LambdaQueryWrapper<FilePO>().in(FilePO::getId, subs).select(FilePO::getId, FilePO::getStorageType)).stream()).collect(Collectors.toMap(FilePO::getId, n -> ToEnumerationUtil.indexToEnum(StorageType.class, n.getStorageType())));
    }

    /**
     * 根据文件ID获取名称
     *
     * @param ids 文件ID
     * @return 文件ID对应名称
     */
    @Override
    public Map<Integer, String> idToName(Set<Integer> ids) {

        return ListUtil.partition(ListUtils.valueIsNull(ids), 100).parallelStream().flatMap(subs -> list(new LambdaQueryWrapper<FilePO>().in(FilePO::getId, subs).select(FilePO::getId, FilePO::getName)).stream()).collect(Collectors.toMap(FilePO::getId, FilePO::getName));
    }

    /**
     * 根据文件ID获取路径
     *
     * @param ids 文件ID
     * @return 文件ID对应路径
     */
    @Override
    public Map<Integer, String> idToPath(Set<Integer> ids) {

        return ListUtil.partition(ListUtils.valueIsNull(ids), 100).parallelStream().flatMap(subs -> list(new LambdaQueryWrapper<FilePO>().in(FilePO::getId, subs).select(FilePO::getId, FilePO::getPath)).stream()).collect(Collectors.toMap(FilePO::getId, FilePO::getPath));
    }

    /**
     * 设置文件
     *
     * @param storageType 存储类型
     * @param path        路径
     * @param name        名字
     * @return 文件ID
     */
    @Override
    public Integer set(StorageType storageType, String path, String name) {
        FilePO entity = new FilePO();
        entity.setStorageType(storageType.getIndex());
        entity.setPath(path);
        entity.setName(name);
        save(entity);
        log.info("entity:{}", entity);
        return entity.getId();
    }
}
