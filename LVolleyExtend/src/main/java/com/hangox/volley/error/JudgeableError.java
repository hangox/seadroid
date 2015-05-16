package com.hangox.volley.error;

import com.android.volley.VolleyError;

/**
 * Created With Android Studio
 * User hangox
 * Date 15/4/8
 * Time 上午10:34
 * 业务上的错误
 */
public class JudgeableError extends VolleyError {
    private int mResultCode = 0;

    public JudgeableError(String message, int resultCode) {
        super(message);
        mResultCode = resultCode;
    }

    public int getResultCode() {
        return mResultCode;
    }

    @Override
    public String toString() {
        return super.toString() + " : " +mResultCode;
    }
}
