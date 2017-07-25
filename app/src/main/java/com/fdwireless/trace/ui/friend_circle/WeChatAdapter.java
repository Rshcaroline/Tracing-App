package com.fdwireless.trace.ui.friend_circle;

/**
 * Created by 汪励颢 on 2017/2/24.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fdwireless.trace.infoclass.UserInfo;
import com.fdwireless.trace.mapmodule.R;

import java.util.List;


public class WeChatAdapter extends BaseAdapter {

    private List<UserInfo> mList;
    private Context mContext;

    public WeChatAdapter(Context _context) {
        this.mContext = _context;
    }

    public void setData(List<UserInfo> _list) {
        this.mList = _list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public UserInfo getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserInfo mUserInfo = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.friends_circle_item, parent, false);
            ImageView fruitImage = (ImageView) convertView.findViewById(R.id.avatar);
            TextView fruitName = (TextView) convertView.findViewById(R.id.username);
            TextView fruitName1 = (TextView) convertView.findViewById(R.id.content);
            //fruitImage.setImageResource(mUserInfo.getImageId());
            Glide.with(this.mContext).load(mUserInfo.getImageId()).crossFade().into(fruitImage);
            fruitName.setText(mUserInfo.getName());
            fruitName1.setText(mUserInfo.getContent());
            holder.gridView = (NoScrollGridView) convertView
                    .findViewById(R.id.gridView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();


        if (mList != null && mList.size() > 0) {
            holder.gridView.setVisibility(View.VISIBLE);
            holder.gridView.setAdapter(new MyGridAdapter(mUserInfo.getUi(),
                    mContext));
            holder.gridView
                    .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent,
                                                View view, int position, long id) {
                            //   imageBrower(position,bean.urls);
                        }
                    });
        }
        return convertView;
    }

    public class ViewHolder {
        LinearLayout mContentimg;
        NoScrollGridView gridView;
    }

}
