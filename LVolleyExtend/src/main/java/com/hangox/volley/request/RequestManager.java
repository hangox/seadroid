package com.hangox.volley.request;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.hangox.volley.http.OkHttpStack;

/**
 * Manager for the queue
 *
 * @author Trey Robinson
 */
public class RequestManager {

    /**
     * the queue :-)
     */
    private static RequestQueue mRequestQueue;
    private static String HOST;

    /**
     * Nothing to see here.
     */
    private RequestManager() {
        // no instances
    }

    /**
     * @param context application context
     * @param host 主机地址
     */
    public static void init(Context context, String host) {
        mRequestQueue = Volley.newRequestQueue(context, new OkHttpStack());
        HOST = host;
    }

    /**
     * @return instance of the queue
     * @throws IllegalStateException if init has not yet been called
     */
    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("Not initialized");
        }
    }

    /**
     * 添加请求
     *
     * @param requestParam
     * @return
     */
    public static Request addRequest(RequestParam requestParam) {
        ParamProcessor paramProcessor = new ParamProcessor().process(requestParam);
        RequestEx request = null;
        if (String.class.equals(paramProcessor.getParser())) {
            request = new NStringRequest(paramProcessor.getMethod(),
                    paramProcessor.getUrl(HOST),
                    requestParam.listener,
                    requestParam.errorListener,
                    requestParam.onCompletedListener
            );
        } else {
            request = new NGsonRequest(paramProcessor.getMethod(),
                    paramProcessor.getUrl(HOST),
                    paramProcessor.getParser(),
                    requestParam.listener,
                    requestParam.errorListener,
                    requestParam.onCompletedListener
            );
        }
        request.setParams(paramProcessor.getParams().getMap());
        return getRequestQueue().add(request);
    }

}
