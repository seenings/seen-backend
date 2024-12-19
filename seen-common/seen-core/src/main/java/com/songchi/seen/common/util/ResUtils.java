package com.songchi.seen.common.util;

import com.songchi.seen.common.exception.SeenException;
import com.songchi.seen.common.model.R;
import com.songchi.seen.common.model.ResultPage;
import com.songchi.seen.common.model.StatusCode;

import java.util.List;

/**
 * 结果返回工具类
 *
 * @author chixuehui
 * @see R
 * @see ResultPage
 * @since 2022-02-19
 */
public class ResUtils {

    private ResUtils() {
    }

    /**
     * 根据当前页内容创建分页结果对象
     *
     * @param pageList 当前页内容
     * @param total    总数
     * @param <T>      列表元素类型
     * @return 分页结果对象
     */
    public static <T> ResultPage<T> createPage(List<T> pageList, Integer total) {
        ResultPage<T> resultPage = new ResultPage<>();
        resultPage.setPageList(pageList);
        resultPage.setTotal(total);
        return resultPage;
    }

    /**
     * 生成R
     *
     * @param statusCode 状态码
     * @param data       数据
     * @param msg        消息
     * @param <T>        数据类型
     * @return r
     */
    public static <T> R<T> createR(StatusCode statusCode, T data, String msg) {
        R<T> r = new R<>();
        r.setData(data);
        r.setMsg(msg);
        r.setCode(statusCode.getCode());
        return r;
    }

    /**
     * 生成成功的R
     *
     * @param data 数据
     * @param msg  消息
     * @param <T>  数据类型
     * @return r
     */
    public static <T> R<T> ok(T data, String msg) {
        return createR(StatusCode.SUCCESS, data, msg);
    }

    /**
     * 生成失败的R
     *
     * @param data 数据
     * @param msg  消息
     * @param <T>  数据类型
     * @return r
     */
    public static <T> R<T> error(T data, String msg) {
        return createR(StatusCode.ERROR, data, msg);
    }

    /**
     * 生成失败的R
     *
     * @param msg 消息
     * @param <T> 数据类型
     * @return r
     */
    public static <T> R<T> error(String msg) {
        return error(null, msg);
    }

    /**
     * 生成成功的R
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return r
     */
    public static <T> R<T> ok(T data) {
        return ok(data, StatusCode.SUCCESS.getMsg());
    }

    /**
     * 成功的数据
     * @param r 结果对象
     * @param <T>   对象类型
     * @return  数据
     * @throws SeenException    不成功时的异常
     */
    public static <T> T successData(R<T> r) throws SeenException {
        if (r.getCode() == StatusCode.SUCCESS.getCode()) {
            return r.getData();
        } else {
            throw new SeenException(r.getMsg());
        }
    }
}
