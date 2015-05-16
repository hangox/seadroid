package com.hangox.volley.request;

import com.android.volley.Response;
import com.hangox.volley.annotaion.NotKey;

/**
 * Created With Android Studio
 * User hangox
 * Date 15/5/5
 * Time 下午5:20
 * 请求参数父类
 */
public class RequestParam<T> {
    @NotKey
    String url;

    @NotKey
    Class parser;
    @NotKey
    public OnCompletedListener onCompletedListener;

    @NotKey
    public Response.Listener<T> listener;

    @NotKey
    public Response.ErrorListener errorListener;
}
