package com.fdwireless.trace.httpclass;

import com.android.volley.VolleyError;

/**
 * Created by 汪励颢 on 2017/2/6.
 */

public interface my_CallBackListener<T> {
    void onSuccessResponse(T response);
    void onErrorResponse(VolleyError error);
}
