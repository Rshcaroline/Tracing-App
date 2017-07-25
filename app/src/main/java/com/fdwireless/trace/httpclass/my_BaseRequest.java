package com.fdwireless.trace.httpclass;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

/**
 * Created by 汪励颢 on 2017/2/6.
 */

public class my_BaseRequest {
    protected static RequestQueue requestQueue;
    private Context mContext;

    protected my_BaseRequest(Context pContext) {
        this.mContext = pContext;
    }

    protected void addRequest(Request request) {

        com.fdwireless.trace.httpclass.my_Volley.getInstace(mContext).add(request);
    }
}