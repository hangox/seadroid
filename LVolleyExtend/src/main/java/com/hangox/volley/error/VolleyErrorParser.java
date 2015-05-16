package com.hangox.volley.error;

import android.content.Context;
import android.support.annotation.StringRes;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.hangox.volley.R;


/**
 * Created With Android Studio
 * User hangox
 * Date 15/3/23
 * Time 上午11:17
 * volley 项目解析器
 */
public class VolleyErrorParser {
    private Context mContext;

    public VolleyErrorParser(Context context) {
        mContext = context;
    }

    public String getErrorParse(VolleyError volleyError) {
        if (volleyError == null) return getString(R.string.volley_unknow_error);
        return parserException(volleyError);
    }


    public String parserException(VolleyError error) {
        String errorStr;

        if (error instanceof AuthFailureError) {
            errorStr = getString(R.string.volley_error_auth_failure);
        } else if (error instanceof NoConnectionError) {
            errorStr = getString(R.string.volley_error_no_connection);
        }else if (error instanceof NetworkError) {
            errorStr = getString(R.string.volley_error_network);
        }  else if (error instanceof ParseError) {
            errorStr = getString(R.string.volley_error_parse);
        } else if (error instanceof ServerError) {
            errorStr = getString(R.string.volley_error_service);
        } else if (error instanceof TimeoutError) {
            errorStr = getString(R.string.volley_error_timeout);
        } else errorStr = error.getLocalizedMessage();
        return errorStr;
    }


    /**
     * 获取新的ID
     *
     * @param resId
     * @return
     */
    private String getString(@StringRes int resId) {
        return mContext.getString(resId);
    }


}
