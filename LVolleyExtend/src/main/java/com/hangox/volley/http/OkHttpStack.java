package com.hangox.volley.http;

import com.android.volley.toolbox.HurlStack;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created With Android Studio
 * User hangox
 * Date 15/5/5
 * Time 下午12:06
 */
public class OkHttpStack extends HurlStack {

    private final OkHttpClient mClient;
    private final OkUrlFactory mOkUrlFactory;

    public OkHttpStack() {
        this(new OkHttpClient());
    }
    public OkHttpStack(OkHttpClient client){
        if(client == null){
            throw new NullPointerException("client cannot be null");
        }
        mClient = client;
        mOkUrlFactory = new OkUrlFactory(mClient);
    }


    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        return mOkUrlFactory.open(url);
    }
}