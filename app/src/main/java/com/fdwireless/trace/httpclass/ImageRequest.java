package com.fdwireless.trace.httpclass;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.fdwireless.trace.mapmodule.R;

/**
 * Created by 汪励颢 on 2017/2/11.
 */

public class ImageRequest {
    public  static RequestQueue requestQueue;
    private Context mContext;
    private ImageLoader imageLoader;
    public ImageRequest(Context pContext){
        this.mContext=pContext;
        requestQueue=com.fdwireless.trace.httpclass.my_Volley.getInstace(mContext);
         imageLoader = new ImageLoader(requestQueue, new BitmapCache());

    }
    public void get(ImageView imageView,String url){
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
                R.drawable.default_image, R.drawable.failed_image);

        this.imageLoader.get(url,listener);
    }
    public void get(ImageView imageView,String url,int maxWidth,int maxHeight){
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
                R.drawable.default_image, R.drawable.failed_image);
        this.imageLoader.get(url,listener,maxWidth,maxHeight);


    }
    public void get(NetworkImageView imageView,String url){
        imageView.setDefaultImageResId(R.drawable.default_image);
        imageView.setErrorImageResId(R.drawable.failed_image);
        imageView.setImageUrl(url,this.imageLoader);
    }


}
