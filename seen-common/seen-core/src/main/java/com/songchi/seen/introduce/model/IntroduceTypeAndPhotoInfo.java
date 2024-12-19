package com.songchi.seen.introduce.model;

import com.songchi.seen.introduce.enumeration.IntroduceTypeEnum;

/**
 * 照片介绍信息
 */
public record IntroduceTypeAndPhotoInfo(IntroduceTypeEnum introduceTypeEnum, Integer order, Integer id) {
}
