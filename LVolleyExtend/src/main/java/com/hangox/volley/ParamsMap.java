package com.hangox.volley;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HangoX
 * Created With Android Studio
 * Date 13-12-6
 * Time 下午4:00
 * 参数保存
 */
public class ParamsMap  {

    private Map<String,String> mParams = new HashMap<>();


    /**
     * 添加一个集合
     * @param map 集合
     */
    public void putAll(Map<String,String> map){
            mParams.putAll(map);
    }

    public ParamsMap put(String key,String value){
        mParams.put(key,value);
        return this;
    }

    public ParamsMap put(String key,int value){
        mParams.put(key,value+"");
        return this;
    }

    public ParamsMap put(String key,long value){
        mParams.put(key,value+"");
        return this;
    }


    public ParamsMap put(String key,float value){
        mParams.put(key,value+"");
        return this;
    }

    public ParamsMap put(String key,double value){
        mParams.put(key,value+"");
        return this;
    }
    public Map<String,String> getMap(){
        return mParams;
    }

    /**
     * 添加一个List
     * @param key 关键字
     * @param values 内容
     * @return 参数
     */
    public ParamsMap put(String key ,List values){
        put(key,values,",");
        return this;
    }

    /**
     * 添加一个集合,用split 划分
     * @param key 关键字
     * @param values 内容
     * @param split 划分符号
     * @return
     */
    public ParamsMap put(String key,List values,String split){
        if(values != null&& !values.isEmpty()){
            StringBuilder valueBuilder = new StringBuilder();
            for(Object object : values){
                valueBuilder.append(object.toString()).append(split);
            }
            valueBuilder.delete(valueBuilder.length() - split.length(),valueBuilder.length());
            mParams.put(key,valueBuilder.toString());
        }
        return this;
    }

    /**
     * 按照key url 的
     * @param key_value
     */
    public void putAll(Object... key_value){
        if(key_value.length % 2 != 0 ) return;
        for(int i = 0 ;i < key_value.length ; i += 2)
            put(key_value[i].toString(),key_value[i+1].toString());
    }

    @Override
    public String toString() {
        Iterator<Map.Entry<String,String>> iterator= mParams.entrySet().iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (iterator.hasNext()){
            Map.Entry<String,String> entry =  iterator.next();
            stringBuilder.append(entry.getKey()).append("=").append(entry.getValue())
                    .append("&");
        }
        stringBuilder.delete(stringBuilder.length() - 1,stringBuilder.length());
        return stringBuilder.toString();
    }
}
