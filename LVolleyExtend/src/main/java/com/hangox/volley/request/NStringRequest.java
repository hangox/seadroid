package com.hangox.volley.request;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

/**
 * Created With Android Studio
 * User hangox
 * Date 15/4/9
 * Time 下午4:50
 * 返回String的request
 */
public class NStringRequest extends RequestEx<String> {

    private final Response.Listener<String> mListener;

    private final OnCompletedListener mOnCompletedListener;
    public NStringRequest(int method, String url,
                          Response.Listener<String> listener,
                          Response.ErrorListener errorListener,
                          OnCompletedListener onCompletedListener) {
        super(method,url,errorListener);
        mListener = listener;
        mOnCompletedListener = onCompletedListener;

    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
        if(mOnCompletedListener != null) mOnCompletedListener.onRequestCompleted(true);
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
        if(mOnCompletedListener != null) mOnCompletedListener.onRequestCompleted(false);
    }
}
