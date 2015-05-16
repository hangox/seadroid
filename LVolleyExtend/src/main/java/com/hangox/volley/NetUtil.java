package com.hangox.volley;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.hangox.volley.request.RequestManager;
import com.hangox.volley.request.GsonRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * Created With Android Studio
 * Date 13-12-2
 * Time 下午3:24
 * @author hangox
 * 访问类
 */
public class NetUtil {

    private static final String TAG = NetUtil.class.getName();


    /**
     * 返回为String，和post 请求的时候使用这个
     *
     * @param url
     * @param params
     * @param listener
     * @param errorListener
     * @return
     */
    public static Request doPostRequest(String url, ParamsMap params,
                                        Response.Listener<String> listener,
                                        Response.ErrorListener errorListener) {
        Request request = requestFactory(REQUEST_STRING, Request.Method.POST, url, params, listener,
                errorListener, null);
        RequestManager.getRequestQueue().add(request);
        return request;
    }

    /**
     * 请求为string 类型的时候且为Get的使用这个
     *
     * @param url
     * @param params
     * @param listener
     * @param errorListener
     * @return
     */
    public static Request doGetRequest(String url, ParamsMap params,
                                       Response.Listener<String> listener,
                                       Response.ErrorListener errorListener) {
        Request request = requestFactory(REQUEST_STRING, Request.Method.GET, url, params, listener,
                errorListener, null);
        RequestManager.getRequestQueue().add(request);
        return request;
    }

    /**
     * 请求返回为Gson和请求为post的时候使用这个
     *
     * @param url
     * @param params
     * @param listener
     * @param errorListener
     * @param type
     * @return
     */
    public static Request doPostRequest(String url, final ParamsMap params,
                                        Response.Listener listener,
                                        Response.ErrorListener errorListener, Class type) {
        Request request = requestFactory(REQUEST_GSON, Request.Method.POST, url, params, listener,
                errorListener, type);
        RequestManager.getRequestQueue().add(request);
        return request;
    }

    /**
     * 请求为gson 类型的时候且为Get的使用这个
     *
     * @param url
     * @param params
     * @param listener
     * @param errorListener
     * @return
     */
    public static Request doGetRequest(String url, final ParamsMap params,
                                       Response.Listener listener,
                                       Response.ErrorListener errorListener, Class type) {
        Request request = requestFactory(REQUEST_GSON, Request.Method.GET, url, params, listener,
                errorListener, type);
        RequestManager.getRequestQueue().add(request);
        return request;
    }

    /**
     * 请求器工厂
     *
     * @param requestType   请求器类型
     * @param method
     * @param url
     * @param params
     * @param listener
     * @param errorListener
     * @param type          Gson使用这个字段,其他为NULL
     * @return
     */
    public static Request requestFactory(int requestType, int method,
                                         String url, final ParamsMap params,
                                         Response.Listener listener,
                                         Response.ErrorListener errorListener, Class type) {
        if (params != null) ignoreNull(params.getMap());
        //打印请求
        if (VolleyLog.DEBUG) logRequestUrl(url, params.getMap());
        //如果是get请求不会调用getParams
        if(method == Request.Method.GET && params != null)
            url = makeGetUrl(url,params.getMap());
        Request request = null;
        switch (requestType) {
            case REQUEST_GSON:
                request = new GsonRequest(method, url, type, listener, errorListener){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        if(params == null)
                            return super.getParams();
                        return params.getMap();
                    }
                };
                break;
            case REQUEST_STRING:
                request = new StringRequest(method, url, listener, errorListener){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        if(params == null)
                            return super.getParams();
                        return params.getMap();
                    }
                };
                break;
            default:
                Log.e(TAG,"no match request type");
                break;
        }
//        Log.v(TAG,isDebug+"");
        return request;
    }

    /**
     * 打印请求连接
     *
     * @param rootUrl
     * @param params
     */
    public static void logRequestUrl(String rootUrl, Map<String, String> params) {
        Log.i(TAG, makeGetUrl(rootUrl, params));
    }


    public static String makeGetUrl(String rootUrl, Map<String, String> params){
        StringBuilder url =new StringBuilder(rootUrl);
        if(!rootUrl.contains("?"))
             url.append("?");
        try {
            if (params != null) {
                for (String key : params.keySet()) {
                    String encodeParams = URLEncoder.encode(params.get(key), "utf-8");
                    url.append(key).append("=").append(encodeParams).append("&");
                }
                url.delete(url.length() - 1, url.length());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return url.toString();
    }

    /**
     * 忽略空值
     *
     * @param params
     */
    public static void ignoreNull(Map<String, String> params) {
        if(params == null) return;
        Iterator<String> iterator = params.keySet().iterator();
        String key = "";
        while (iterator.hasNext()) {
            key = iterator.next();
            if (params.get(key) == null) iterator.remove();
        }
    }

    public static final int REQUEST_STRING = 1;
    public static final int REQUEST_GSON = 2;

}
