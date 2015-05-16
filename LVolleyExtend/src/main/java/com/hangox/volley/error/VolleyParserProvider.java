package com.hangox.volley.error;

import android.content.Context;

/**
 * Created With Android Studio
 * User hangox
 * Date 15/3/31
 * Time 下午4:10
 * Volley 解析器提供者
 */
public class VolleyParserProvider {
    private static VolleyErrorParser INSTANCE;

    public static void init(Context context) {
        INSTANCE = new VolleyErrorParser(context);
    }

    public static VolleyErrorParser getInstance() {
        if (INSTANCE == null) throw new IllegalThreadStateException("not init");
        return INSTANCE;
    }
}
