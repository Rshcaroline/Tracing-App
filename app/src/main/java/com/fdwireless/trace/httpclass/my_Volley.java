package com.fdwireless.trace.httpclass;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by 汪励颢 on 2017/2/6.
 */

public class my_Volley {
    private static RequestQueue instance;
    public static RequestQueue getInstace(Context pContext){
        if(instance==null){
            instance= Volley.newRequestQueue(pContext);
        }
        return instance;
    }
}
