package com.songchi.seen.introduce.model;

import com.songchi.seen.introduce.enumeration.IntroduceTypeEnum;

/**
 * IntroduceTypeAndText
 *
 * @author chixuehui
 * @since 2022-11-27
 */
public record IntroduceTypeAndText(IntroduceTypeEnum introduceTypeEnum, Integer textId) {}
