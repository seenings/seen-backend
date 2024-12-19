package com.songchi.seen.common.model;

import java.util.List;

/**
 * 级联，value是字符串
 * @param v 值
 * @param t 标签
 * @param c 子节点
 */
public record CascaderString(String v, String t, List<CascaderString> c) {}
