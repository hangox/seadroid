package com.hangox.volley.request;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hangox.volley.error.JudgeableError;

import java.io.UnsupportedEncodingException;

/**
 * Created With Android Studio
 * User 47
 * Date 15/4/7
 * Time 下午5:17
 * 新的GsonRequest,结合了判断
 */
public class NGsonRequest<T extends Judgeable> extends RequestEx<T>{
    public static final String TAG = NGsonRequest.class.getName();

    private Gson mGson = new Gson();

    private Class<T> mClass;

    /**
     * 完成后的监听器
     */
    private OnCompletedListener mCompletedListener;
    private Response.Listener<T> mListener;


    /**
     *
     * @param method 调用的方法
     * @param url 访问的链接
     * @param objectClass class 类型
     * @param listener 成功回调
     * @param error 错误回调
     * @param completedListener 完成回调
     */
    public NGsonRequest(int method,
                        String url,
                        Class<T> objectClass,
                        Response.Listener<T> listener,
                        Response.ErrorListener error,
                        OnCompletedListener completedListener) {
        super(method, url,error);
        mClass = objectClass;
        this.mListener = listener;
        mCompletedListener = completedListener;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            if(VolleyLog.DEBUG) Log.i(TAG,json);
            T t = mGson.fromJson(json,mClass);
            if(t.isSuccess()){
                return Response.success(mGson.fromJson(json, mClass),
                        HttpHeaderParser.parseCacheHeaders(response));
            }else{
                return Response.error(new JudgeableError(t.getResultMessage(),t.getResultCode()));
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }catch (Exception e){
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        super.deliverError(error);
        if(error != null && VolleyLog.DEBUG) Log.e(TAG,error.getLocalizedMessage());
        if(mCompletedListener != null) mCompletedListener.onRequestCompleted(false);
    }

    @Override
    protected void deliverResponse(T response) {
        if(mListener != null) mListener.onResponse(response);
        if(mCompletedListener != null) mCompletedListener.onRequestCompleted(true);
    }


}
