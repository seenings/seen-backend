package com.songchi.seen.introduce.model;

import com.songchi.seen.introduce.enumeration.IntroduceTypeEnum;

/**
 * 介绍照片
 */
public record IntroduceTypeAndPhoto(IntroduceTypeEnum introduceTypeEnum, Integer order, Integer photoId) {
}
