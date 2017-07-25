package com.fdwireless.trace.ui.friend_circle;

/**
 * Created by 汪励颢 on 2017/2/24.
 */

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fdwireless.trace.infoclass.UserImgs;
import com.fdwireless.trace.mapmodule.R;

import java.util.List;




public class MyGridAdapter extends BaseAdapter {
    private List<UserImgs> mUI;

    private LayoutInflater mLayoutInflater;
    private  Context context;

    public MyGridAdapter(List<UserImgs> ui, Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mUI = ui;
        this.context=context;

    }

    @Override
    public int getCount() {
        return mUI == null ? 0 : mUI.size();
    }

    @Override
    public String getItem(int position) {
        return mUI.get(position).urls;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyGridViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MyGridViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.user_img_item,
                    parent, false);
            viewHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.iv_user_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyGridViewHolder) convertView.getTag();
        }
        final String url = getItem(position);
        //ImageLoader.getInstance().displayImage(url, viewHolder.imageView);
       Glide.with(this.context).load(url).crossFade().placeholder(R.drawable.default_image).into(viewHolder.imageView);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View layout = inflater.inflate(R.layout.diolog_img, null);
                ImageView imgView = (ImageView)layout.findViewById(R.id.dio_img);//注意这一句
                    //有背景图
                    final AlertDialog dialog = new AlertDialog.Builder(context).create();

                    dialog.setView(layout);
                    dialog.show();
                Glide.with(context).load(url).placeholder(R.drawable.default_image).into(imgView);

                    // 全屏显示的方法
//     final Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//     ImageView imgView = getView();
//     dialog.setContentView(imgView);
//     dialog.show();

                    // 点击图片消失
                    imgView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });
                }




        });

        return convertView;
    }

    private static class MyGridViewHolder {
        ImageView imageView;
    }
}
