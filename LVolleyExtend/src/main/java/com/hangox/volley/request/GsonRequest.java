package com.hangox.volley.request;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

/**
 * Wrapper for Volley requests to facilitate parsing of json responses. 
 * 
 * @param <T>
 */
public class GsonRequest<T> extends Request<T> {
    public static final String TAG = GsonRequest.class.getSimpleName();

	/**
	 * Gson parser 
	 */
	private final Gson mGson;
	
	/**
	 * Class type for the response
	 */
	private final Class<T> mClass;
	
	
	/**
	 * Callback for response delivery 
	 */
	private final Listener<T> mListener;
	
	/**
	 * @param method
	 * 		Request type.. Method.GET etc
	 * @param url
	 * 		path for the requests
	 * @param objectClass
	 * 		expected class type for the response. Used by gson for serialization.
	 * @param listener
	 * 		handler for the response
	 * @param errorListener
	 * 		handler for errors
	 */
	public GsonRequest(int method
            , String url
            , Class<T> objectClass
            , Listener<T> listener
            , ErrorListener errorListener) {
		
		super(method, url, errorListener);
		this.mClass = objectClass;
		this.mListener = listener;
		mGson = new Gson();
		
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			if (VolleyLog.DEBUG) Log.i(TAG, json);
			return Response.success(mGson.fromJson(json, mClass),
                    HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}catch (Exception e){
            return Response.error(new ParseError(e));
        }
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}


	@Override
	public void deliverError(VolleyError error) {
		super.deliverError(error);
		if (VolleyLog.DEBUG && error != null) Log.i(TAG, error.getLocalizedMessage() +"");
	}
}
