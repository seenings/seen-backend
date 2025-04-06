package io.github.seenings.introduce.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.seenings.introduce.po.UserIntroducePhotoToPhotoPO;
import io.github.seenings.introduce.service.UserIntroducePhotoToPhotoPOService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserIntroducePhotoToPhotoPOServiceImpl
 */
@Mapper
interface UserIntroducePhotoToPhotoPOMapper extends BaseMapper<UserIntroducePhotoToPhotoPO> {

}

@Service
public class UserIntroducePhotoToPhotoPOServiceImpl extends ServiceImpl<UserIntroducePhotoToPhotoPOMapper, UserIntroducePhotoToPhotoPO> implements UserIntroducePhotoToPhotoPOService {


    /**
     * 插入
     *
     * @param userIntroducePhotoId 介绍照片信息ID
     * @param photoId              照片ID
     * @return 是否成功
     */
    @Override
    public boolean insert(Integer userIntroducePhotoId, Integer photoId) {
        return save(new UserIntroducePhotoToPhotoPO().setUserIntroducePhotoId(userIntroducePhotoId).setPhotoId(photoId));
    }


    /**
     * 根据照片介绍ID获取照片ID
     *
     * @param userIntroducePhotoIds 照片介绍ID
     * @return 照片介绍ID对应照片ID
     */
    @Override
    public Map<Integer, Integer> userIntroducePhotoIdToPhotoId(Set<Integer> userIntroducePhotoIds) {
        return ListUtil.partition(userIntroducePhotoIds.stream().toList(), 100)
                .stream().parallel()
                .flatMap(subs -> list(new QueryWrapper<UserIntroducePhotoToPhotoPO>()
                                .lambda()
                                .in(UserIntroducePhotoToPhotoPO::getUserIntroducePhotoId, subs)
                                .select(UserIntroducePhotoToPhotoPO::getUserIntroducePhotoId, UserIntroducePhotoToPhotoPO::getPhotoId)
                        ).stream()
                )
                .collect(Collectors.toMap(UserIntroducePhotoToPhotoPO::getUserIntroducePhotoId
                        , UserIntroducePhotoToPhotoPO::getPhotoId));
    }
}
