package io.github.seenings.info.model;

import java.util.Map;

/**
 * 个人介绍
 *
 * @param introduceTypeId   介绍类型ID
 * @param introduceTitle    介绍标题
 * @param introduceContent  介绍内容
 * @param orderToPhotoIdMap 顺序对应照片ID
 */
public record PersonIntroduce(Integer introduceTypeId, String introduceTitle, String introduceContent,

        Map<Integer, Integer> orderToPhotoIdMap) {

}
