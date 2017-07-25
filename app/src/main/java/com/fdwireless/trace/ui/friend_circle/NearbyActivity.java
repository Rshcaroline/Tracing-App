package com.fdwireless.trace.ui.friend_circle;

/**
 * Created by 汪励颢 on 2017/2/24.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fdwireless.trace.httpclass.my_CallBackListener;
import com.fdwireless.trace.httpclass.my_GsonRequest;
import com.fdwireless.trace.infoclass.Clip;
import com.fdwireless.trace.infoclass.User;
import com.fdwireless.trace.infoclass.UserImgs;
import com.fdwireless.trace.infoclass.UserInfo;
import com.fdwireless.trace.mapmodule.R;
import com.fdwireless.trace.ui.main.MainActivity;
import com.fdwireless.trace.ui.nav.NavFriends;
import com.fdwireless.trace.ui.nav.NavSettings;
import com.fdwireless.trace.ui.nav.NavTeam;
import com.fdwireless.trace.ui.nav.NavUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.height;
import static android.R.attr.width;

public class NearbyActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private DrawerLayout mDrawerLayout;
    private ListView mListView = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public User userData;
    private ArrayList<Clip> clips=new ArrayList<>();
    private my_GsonRequest request=new my_GsonRequest(this);

    private final String URL = "http://115.159.198.209/Tracing/clipHandler.php";
    private AMapLocation location;
    private boolean flag=false;
private static  Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_main);
        this.userData=getIntent().getParcelableExtra("user_data");
        this.clips=this.getIntent().getParcelableArrayListExtra("clip_list");
        mListView = (ListView) findViewById(R.id.lv_main);
        mListView.addHeaderView(getheadView());
        // mListView.setDividerHeight(0);
        setData();
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout)this.findViewById(R.id.id_swipe_ly);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        //0303 Jinxisen: 增加用户名显示
        TextView usrname = (TextView)findViewById(R.id.usrname);
        usrname.setText(userData.getName());

        //侧滑菜单栏的创建
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setItemIconTintList(null); //让图标的颜色显示本身的颜色

        //侧滑菜单栏的item的点击事件 user friends team settings
        navView.setCheckedItem(R.id.nav_view);
        View headerLayout = navView.inflateHeaderView(R.layout.nav_header);
        TextView navUserName=(TextView)headerLayout.findViewById(R.id.username) ;
        navUserName.setText(userData.getName());

        final CircleImageView imageView=(CircleImageView)headerLayout.findViewById(R.id.icon_image);
        Glide.with(NearbyActivity.this)
                .load("http://115.159.198.209/Tracing/img/usericon/"+userData.getId()+".jpg").crossFade()
                .error(R.drawable.failed_image)
               .into(imageView);
//        Glide.with(NearbyActivity.this)
//                .load("http://115.159.198.209/Tracing/img/usericon/"+userData.getId()+".jpg")
//                .asBitmap()
//                .error(R.drawable.failed_image)
//                .into(new SimpleTarget<Bitmap>(width, height) {
//                    @Override
//                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
//                        imageView.setImageBitmap(bitmap);

                        //大封印之术 dispLocationoverlay.setMarkerPortrait();
//                    } });
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                switch (item.getItemId()){
                    case R.id.user:
                        Intent intent1 = new Intent(NearbyActivity.this, NavUser.class);
                        startActivity(intent1);
                        break;
                    case R.id.friends:
                        Intent intent2 = new Intent(NearbyActivity.this, NavFriends.class);
                        startActivity(intent2);
                        break;
                    case R.id.team:
                        Intent intent3 = new Intent(NearbyActivity.this, NavTeam.class);
                        startActivity(intent3);
                        break;
                    case R.id.settings:
                        Intent intent4 = new Intent(NearbyActivity.this, NavSettings.class);
                        startActivity(intent4);
                        break;
                    default:
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);

        (new Handler()).postDelayed(new Runnable() {

            @Override
            public void run() {

              handler = new Handler()
                {
                    public void handleMessage(android.os.Message msg) {
                        if(msg.what==0x123)
                        {
                            setData();
                        }
                    };
                };
                getClips();
                mSwipeRefreshLayout.setRefreshing(false);

                Toast.makeText(NearbyActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
            }
        }, 3000);
    }
private void getClips(){
    location= MainActivity.getDispLocation();
    double r=0.1;
    double Lat=location.getLatitude();

    double Lon=location.getLongitude();



    Map<String,String > params=new HashMap();

    params.put("Lat",Lat+"");
    params.put("Lon",Lon+"");
    params.put("r",r+"");
    clips.clear();

    request.post(URL, Clip[].class, new my_CallBackListener<Clip[]>() {
        @Override
        public void onSuccessResponse(Clip[] response) {

            if (response!=null) {
//int count
                for(int i=0;i<response.length;i++){
                    clips.add(response[i]);
                     if(clips.size()==response.length){
                         handler.sendEmptyMessage(0x123);
                     }

                }



            }
            else{
                Log.e("TEST4","fail");
            }

        }
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(NearbyActivity.this,"载入标签信息错误：请检查网络连接",Toast.LENGTH_SHORT).show();
        }
    },params);

}
    private void setData() {
        List<UserInfo> mList = new ArrayList<>();
        int number=clips.size();
        Log.e("playtest",clips.size()+"");
        for(int i=0;i<number;i++)
        {

            if(clips.get(i).getType()!=Clip.MSG)continue;
            UserInfo mUserInfo = new UserInfo(clips.get(i).getName(),"http://115.159.198.209/Tracing/img/usericon/"+clips.get(i).getOwner()+".jpg",clips.get(i).getText());
           // int n=3*(i+1);
            UserImgs[] m = new UserImgs[9];
            for(int k=0;k<9;k++) m[k] = new UserImgs();
            for(int j=0;j<clips.get(i).getNum();j++) {
                m[j].setUrls("http://115.159.198.209/Tracing/img/items/"+clips.get(i).getId()+"_"+j+".jpg");
                mUserInfo.getUi().add(m[j]);
            }
            mList.add(mUserInfo);
        }
		/*UserInfo mUserInfo = new UserInfo();
		UserImgs m = new UserImgs();
		m.setUrls("http://m1.img.srcdd.com/farm2/d/2011/0817/01/5A461954F44D8DC67A17838AA356FE4B_S64_64_64.JPEG");
		mUserInfo.getUi().add(m);
		mList.add(mUserInfo);
		//---------------------------------------------
		UserInfo mUserInfo2 = new UserInfo();
		UserImgs m2 = new UserImgs();
		m2.setUrls("http://m1.img.srcdd.com/farm2/d/2011/0817/01/5A461954F44D8DC67A17838AA356FE4B_S64_64_64.JPEG");
		mUserInfo2.getUi().add(m2);
		UserImgs m21 = new UserImgs();
		m21.setUrls("http://m1.img.srcdd.com/farm2/d/2011/0817/01/5A461954F44D8DC67A17838AA356FE4B_S64_64_64.JPEG");
		mUserInfo2.getUi().add(m21);
		mList.add(mUserInfo2);*/

        WeChatAdapter mWeChatAdapter = new WeChatAdapter(this);
        mWeChatAdapter.setData(mList);
        mListView.setAdapter(mWeChatAdapter);
    }

    private View getheadView() {
        View view = LayoutInflater.from(NearbyActivity.this).inflate(
                R.layout.friends_circle_head, null);
       final ImageView imageView=(ImageView)view.findViewById(R.id.siv_img);
        //Glide.with(NearbyActivity.this).load("http://115.159.198.209/Tracing/img/usericon/"+userData.getId()+".jpg").into(imageView);
        Glide.with(NearbyActivity.this)
                .load("http://115.159.198.209/Tracing/img/usericon/"+userData.getId()+".jpg")
                .asBitmap()
                .error(R.drawable.failed_image)
                .into(new SimpleTarget<Bitmap>(width, height) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        imageView.setImageBitmap(bitmap);

                        //大封印之术 dispLocationoverlay.setMarkerPortrait();
                    } });
        return view;
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.nearby_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:

                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            default:
        }
        return true;
    }

}
