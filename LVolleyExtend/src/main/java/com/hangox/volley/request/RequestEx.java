package com.hangox.volley.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;

import java.util.Map;

/**
 * Created With Android Studio
 * User hangox
 * Date 15/5/6
 * Time 上午11:02
 */
public abstract class RequestEx<T> extends Request<T> {
    private Map<String,String> mParams;
    public RequestEx(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    public void setParams(Map<String,String> map){
        mParams = map;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }
}
