package com.songchi.seen.info.model;

import com.songchi.seen.introduce.model.OrderAndPhotoId;

import java.util.List;

/**
 * 个人介绍
 *
 * @param introduceTypeIndex 介绍标题ID
 * @param introduceContent   介绍内容
 * @param orderAndPhotoIds   介绍照片
 * @param max                最大个数
 */
public record PersonIntroduceSave(int introduceTypeIndex, String introduceContent,
                                  List<OrderAndPhotoId> orderAndPhotoIds, Integer max) {
}
