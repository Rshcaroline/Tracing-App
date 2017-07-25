package com.fdwireless.trace.httpclass;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.Map;


/**
 * Created by 汪励颢 on 2017/2/6.
 */

public class my_GsonRequest<T> extends my_BaseRequest {
    public my_GsonRequest(Context pContext){
        super(pContext);
    }
    public void get(String url, Class<T> mclass,final com.fdwireless.trace.httpclass.my_CallBackListener<T> listener){
        GsonRequest gsonRequest=new GsonRequest(Request.Method.GET, url,mclass,new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                if(null != listener){
                    listener.onSuccessResponse(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(null != listener){
                    listener.onErrorResponse(error);
                }
            }
        });
        addRequest(gsonRequest);
    }
    public void post(String url, Class<T> mclass, final com.fdwireless.trace.httpclass.my_CallBackListener<T> listener, Map<String,String> params){
        Post_GsonRequest gsonRequest=new Post_GsonRequest(url,mclass,new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                if(null != listener){
                    listener.onSuccessResponse(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(null != listener){
                    listener.onErrorResponse(error);
                }
            }
        },params);
        addRequest(gsonRequest);
    }
}
