package com.hangox.volley.request;

import android.text.TextUtils;

import com.android.volley.Request;
import com.hangox.volley.ParamsMap;
import com.hangox.volley.annotaion.Delete;
import com.hangox.volley.annotaion.Put;
import com.hangox.volley.annotaion.Get;
import com.hangox.volley.annotaion.NotKey;
import com.hangox.volley.annotaion.Post;
import com.hangox.volley.annotaion.ParamKey;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by hangox on 15/5/5.
 * 参数处理类
 */
public class ParamProcessor {
    private static final Class[] REQUEST_METHOD = {
            Get.class,
            Post.class,
            Put.class,
            Delete.class
    };

    private static final int[] REQUEST_METHOD_CODE = {
            Request.Method.GET,
            Request.Method.POST,
            Request.Method.PUT,
            Request.Method.DELETE,
    };

    private Class mParser;
    private String mEndUrl;
    private ParamsMap mParamsMap;
    private int mMethod;

    public ParamProcessor (){

    }


    public Class getParser(){
        return mParser;
    }


    /**
     * 获取网址
     * @param host 根域名
     * @return
     */
    public String getUrl(String host){
        if(mEndUrl.startsWith("http")) return mEndUrl;
        return host + mEndUrl;
    }

    public ParamsMap getParams(){
        return mParamsMap;
    }

    public int getMethod(){
        return mMethod;
    }


    /**
     * 处理参数
     * @param requestParam
     * @return
     */
    public ParamProcessor process(RequestParam requestParam) {
        Class paramClass = requestParam.getClass();
        Annotation annotation = null;
        int i = 0 ;
        for (Class methodClass : REQUEST_METHOD) {
            annotation = paramClass.getAnnotation(methodClass);
            if (annotation != null) break;
            i++;
        }
        if (annotation == null) throw new IllegalStateException("Not Set Animation");
        //获取类反射后对象
        Class name = annotation.getClass();
        //获取后半部分url
        String endUrl = null;
        Class parserClass = null;
        try {
            Method methodValue = name.getMethod("url");
            endUrl = (String) methodValue.invoke(annotation);
            Method methodParserBean = name.getMethod("parser");
            parserClass = (Class) methodParserBean.invoke(annotation);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //获取参数
        Field[] fields = paramClass.getFields();
        ParamsMap paramsMap = new ParamsMap();
        for (Field field : fields) {//遍历属性添加参数
            NotKey notKey = field.getAnnotation(NotKey.class);
            if (notKey != null) continue;
            String key;
            ParamKey paramKey = field.getAnnotation(ParamKey.class);
            if (paramKey == null) key = field.getName();
            else key = paramKey.value();
            try {
                String fieldValue = field.get(requestParam).toString();
                if(TextUtils.isEmpty(fieldValue)) continue;//去除空的参数
                paramsMap.put(key, fieldValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        processParam(endUrl, REQUEST_METHOD_CODE[i], paramsMap,parserClass);
        return this;
    }


    /**
     * 处理参数
     * @param endUrl 网站不含有host 那一段
     * @param method 请求的方法
     * @param paramsMap 传递的参数
     * @param parser 映射的类
     */
    private void processParam(String endUrl, int method, ParamsMap paramsMap,Class parser){
        if(method == Request.Method.GET){
            mEndUrl = makeGetUrl(endUrl,paramsMap.getMap());
        }else{
            mEndUrl = endUrl;
        }
        mMethod = method;
        mParamsMap = paramsMap;
        mParser = parser;
    }


    private  String makeGetUrl( String endUrl,Map<String, String> params){
        String rootUrl =  endUrl;
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


}
