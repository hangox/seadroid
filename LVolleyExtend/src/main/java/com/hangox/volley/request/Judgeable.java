package com.hangox.volley.request;

/**
 * Created With Android Studio
 * User hangox
 * Date 15/4/7
 * Time 下午5:22
 * 是否可以判断的
 */
public interface Judgeable {

    /**
     * 判断是否成功
     * @return
     */
    boolean isSuccess();

    /**
     * 获取结果代码
     * @return 结果代码
     */
    int getResultCode();

    /**
     * 获取结果信息
     * @return 结果信息
     */
    String getResultMessage();

}
