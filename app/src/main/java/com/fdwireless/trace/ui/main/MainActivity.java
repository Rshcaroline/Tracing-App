package com.fdwireless.trace.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.StringSignature;
import com.fdwireless.trace.infoclass.User;
import com.fdwireless.trace.mapmodule.ClipOverlay;
import com.fdwireless.trace.mapmodule.LocOverlay;
import com.fdwireless.trace.mapmodule.NewClipActivity;
import com.fdwireless.trace.infoclass.Player;
import com.fdwireless.trace.mapmodule.R;
import com.fdwireless.trace.infoclass.Route;
import com.fdwireless.trace.ui.menu.MenuGroupPeople;
import com.fdwireless.trace.ui.menu.MenuSearch;
import com.fdwireless.trace.ui.nav.NavFriends;
import com.fdwireless.trace.ui.nav.NavSettings;
import com.fdwireless.trace.ui.nav.NavTeam;
import com.fdwireless.trace.ui.nav.NavUser;
import com.fdwireless.trace.ui.friend_circle.NearbyActivity;
import com.xc.xcskin.view.XCArcMenuView;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.height;
import static android.R.attr.width;
//Author: Jin Xisen
//Purpose: The mapviewer module of the app.

public class MainActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {

    //mapview object
    public MapView mapview = null;
    //mapview controller
    public AMap amap = null;
    public UiSettings amapSettings = null;
    //location controller
    public AMapLocationClient locationClient= null;
    public AMapLocationClientOption locationClientOption = null;
    //located point displayer's listener
    public LocationSource.OnLocationChangedListener dispLocationListener = null;
    //located point displayer's config
    public LatLng dispLatLng;

    public static AMapLocation getDispLocation() {
        return dispLocation;
    }

    public static AMapLocation dispLocation;
    public LocOverlay dispLocationoverlay;
    //Clip over-layer
    public ClipOverlay dispClipoverlay;
    //player
    public Player player = new Player();
    public User userData;
    //initview
    static boolean initialized = false;
    static int initialize_portrait_trial = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        this.userData=getIntent().getParcelableExtra("user_data");
        initMap(savedInstanceState);
        gpsCheck();
        activate(null);
        drawUI();
    }

    protected void initMap(Bundle savedInstanceState)
    {
        mapview = (MapView)findViewById(R.id.map);
        mapview.onCreate(savedInstanceState);
        amap = mapview.getMap();
        amapSettings = amap.getUiSettings();
        dispLocationoverlay = LocOverlay.getInstance(getApplicationContext(),amap,player,userData);
        dispClipoverlay = ClipOverlay.getInstance(getApplicationContext(),amap,player,MainActivity.this,userData);
        loadMapConfig();
    }

    protected void initView()
    {
        amap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(dispLocation.getLatitude(),dispLocation.getLongitude()), 15, 0, 3 )));
        amap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(dispLocation.getLatitude(),dispLocation.getLongitude()),
                17, 45, 3)), 2000, null);
    }

    protected void loadMapConfig() //地图显示模式的设置
    {
        amap.setTrafficEnabled(false);
        amap.setMapType(AMap.MAP_TYPE_NORMAL);// 普通地图模式
        //amapSettings.setTiltGesturesEnabled(false); //不允许修改角度
        amapSettings.setZoomControlsEnabled(false); //不显示缩放按钮
        amap.setMinZoomLevel(13);
        amapSettings.setLogoPosition(AMapOptions.LOGO_MARGIN_RIGHT);
        amapSettings.setScaleControlsEnabled(true);

    }

    protected void loadLocationConfig() //定位模式的设置（设置为高精度），当目前活动切换至活动状态时调用
    {
        locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationClientOption.setInterval(5000);
        locationClientOption.setGpsFirst(true);
        //   locationClientOption.setLocationCacheEnable(false);
    }

    protected void gpsCheck() // 检测当前gps状态
    {
        LocationManager gpsChecker = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(!gpsChecker.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.dialog_style);
            builder.setPositiveButton("前往设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setTitle("GPS服务未开启...").setMessage("为了更好的使用体验，需要打开GPS服务").show();
        }
    }

    @Override
    public void activate(OnLocationChangedListener listener) //LocationSource接口必须实现
    {
        if(listener != null)
            dispLocationListener = listener;
        if(locationClient == null)
        {
            locationClient = new AMapLocationClient(getApplicationContext());
            locationClientOption = new AMapLocationClientOption();
            loadLocationConfig();
            locationClient.setLocationListener(this);
            locationClient.setLocationOption(locationClientOption);
            locationClient.startLocation();
        }
    }

    //所有当玩家位置变化时（位置更新，间隔为5秒）行为都在下面函数进行
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        dispLocation = aMapLocation;
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                dispLatLng = new LatLng(aMapLocation.getLatitude(),
                        aMapLocation.getLongitude());
                player.updatePlayerLoc(aMapLocation);
                dispLocationoverlay.locationChanged(aMapLocation);//设置定位图标、精度圈以及移动效果
                dispClipoverlay.locationChanged(aMapLocation);//ClipOverlay中对位置变化的反应
                if(!initialized)
                {
                    initialized = true;
                    initView();
                }
                if(initialize_portrait_trial == 1)
                {
                    initialize_portrait_trial = dispLocationoverlay.setMarkerPortrait();
                }
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": "
                        + aMapLocation.getErrorInfo();
                Toast.makeText(MainActivity.this, errText, Toast.LENGTH_SHORT).show();
                if(aMapLocation.getErrorCode() == 12)
                    Toast.makeText(MainActivity.this, "请在设备权限管理中心允许定位权限", Toast.LENGTH_SHORT).show();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void deactivate()
    {
        dispLocationListener = null;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mapview.onDestroy();
        if(locationClient!=null)
        {
            locationClient.stopLocation();
            locationClient.onDestroy();
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        mapview.onResume();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        mapview.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outstate)
    {
        super.onSaveInstanceState(outstate);
        mapview.onSaveInstanceState(outstate);
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.dialog_style);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setTitle("确认退出").setMessage("你真的要退出Tracing了吗?").show();
    }


    //====================下面是小花的UI代码，原封不动粘贴========================
    private DrawerLayout mDrawerLayout;
    private static int cheaterMode = 0;

    protected void drawUI() {

        //标题栏的创建
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

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


        //0222：加载图片后增加了回调
        Glide.with(MainActivity.this)
                .load("http://115.159.198.209/Tracing/img/usericon/"+userData.getId()+".jpg")
                .asBitmap().signature(new StringSignature("01"))
                .error(R.drawable.failed_image)
                .into(new SimpleTarget<Bitmap>(width, height) {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                        imageView.setImageBitmap(bitmap);
                        player.setPortriatRescale(bitmap);
                        initialize_portrait_trial = dispLocationoverlay.setMarkerPortrait();
                    } });
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                switch (item.getItemId()){
                    case R.id.user:
                        Intent intent1 = new Intent(MainActivity.this, NavUser.class);
                        startActivity(intent1);
                        break;
                    case R.id.friends:
                        Intent intent2 = new Intent(MainActivity.this, NavFriends.class);
                        startActivity(intent2);
                        break;
                    case R.id.team:
                        Intent intent3 = new Intent(MainActivity.this, NavTeam.class);
                        startActivity(intent3);
                        break;
                    case R.id.settings:
                        cheaterMode ++;
                        if(cheaterMode %6 == 4) {
                            player.sight = 1500;
                            Toast.makeText(MainActivity.this,"上帝模式已开启",Toast.LENGTH_SHORT).show();
                        }
                        if(cheaterMode %6 == 5)
                        {
                            player.sight = 80;
                        }
                        Intent intent4 = new Intent(MainActivity.this, NavSettings.class);
                        startActivity(intent4);
                        break;
                    default:
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        satelliteMenu();
    }

    //标题栏的菜单的按钮的点击事件 search group nearby
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.search:
                Intent intent1 = new Intent(MainActivity.this, MenuSearch.class);
                startActivity(intent1);
                break;
            case R.id.group:
                if(!player.isGaming()) {
                    Intent intent = new Intent(MainActivity.this,MenuGroupPeople.class);
                    startActivityForResult(intent,1);
                }
                else
                {
                    quitGame();
                }
                break;
            case R.id.nearby:
                Intent intent3 = new Intent(MainActivity.this, NearbyActivity.class);


                intent3.putExtra("user_data",userData);

                intent3.putParcelableArrayListExtra("clip_list",dispClipoverlay.getClipList());
                startActivity(intent3);
                break;
            default:
        }
        return true;
    }

    //标题栏菜单栏点开之后的按钮的创建 search group nearby
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    //卫星菜单的设置
    public void satelliteMenu()
    {
        XCArcMenuView view = (XCArcMenuView) findViewById(R.id.arcmenu2);
        view.setOnMenuItemClickListener(new XCArcMenuView.OnMenuItemClickListener() {

            @Override
            public void onClick(View view, int pos) {

                String tag = (String) view.getTag();
                //Toast.makeText(MainActivity.this, "test: " + tag, Toast.LENGTH_SHORT).show();
                if(tag.equals("search"))
                {
                    Intent intent1 = new Intent(MainActivity.this, MenuSearch.class);
                    startActivity(intent1);
                }
                else if(tag.equals("group"))
                {
                    if(!player.isGaming()) {
                        //testGame();
                        Intent intent = new Intent(MainActivity.this,MenuGroupPeople.class);
                        startActivityForResult(intent,1);
                    }
                    else
                    {
                        quitGame();
                    }
                }
                else if(tag.equals("nearby"))
                {
                    Intent intent3 = new Intent(MainActivity.this, NearbyActivity.class);
                     intent3.putExtra("user_data",userData);
                    intent3.putParcelableArrayListExtra("clip_list",dispClipoverlay.getClipList());
                    startActivity(intent3);
                }
                else if(tag.equals("where am i"))
                {
                    amap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            new LatLng(player.getUsrLat(),player.getUsrLng()),
                            18, 45, 3)), 1000, null);
                }
                else if(tag.equals("write something"))
                {
                    Intent intent4 = new Intent(MainActivity.this, NewClipActivity.class);
                    intent4.putExtra("Lon",player.getUsrLng());
                    intent4.putExtra("Lat",player.getUsrLat());
                    intent4.putExtra("user_data",userData);
                    startActivity(intent4);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        switch(requestCode)
        {
            case 1: //组队信息
                if(resultCode == RESULT_OK)
                {
                    String route = data.getStringExtra("route");
                    if(route.equals("ZJ"))
                        testGame();
                }
                break;
            case 2: //从CardActivity来的 AR扫描成功与否信息
                if(resultCode == RESULT_OK)
                {
                    boolean sucess = data.getBooleanExtra("Success",false);
                    if(sucess) {
                        player.stepInc();
                        if(player.isFinish())
                            gameFinish();
                        else {
                            Toast.makeText(MainActivity.this, "成功啦！按照指示继续前进吧！", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(MainActivity.this,""+player.getStep()+player.currentItem().intro+player.currentItem().clipId,Toast.LENGTH_SHORT).show();
                            dispClipoverlay.updateMarkers();
                        }
                    }
                    else
                        Toast.makeText(MainActivity.this,"未知错误",Toast.LENGTH_SHORT).show();
                }
        }
    }

    protected void testGame()
    {
        player.setGaming(true);
        player.setGameARDestList(new Route().selectRoute("ZJ"));
        dispClipoverlay.loadClipfromLocal(player.getGameARDestList());
        player.stepClear();
        dispClipoverlay.updateMarkers();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,
                R.style.dialog_style);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setTitle("成员已到齐").setMessage(
                "定向已开始，开始探寻故事的足迹吧！").show();
    }

    protected void quitGame()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,
                R.style.dialog_style);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                player.setGaming(false);
                player.setGameARDestList(null);
                player.stepClear();
                Toast.makeText(MainActivity.this,"游戏已结束",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setTitle("确认退出队伍").setMessage(
                "你真的要退出队伍了吗?退出后必须重新开始定向游戏").show();
    }

    protected void gameFinish()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,
                R.style.dialog_style);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                player.setGaming(false);
                player.setGameARDestList(null);
                player.stepClear();
                dispClipoverlay.updateMarkers();
                dialog.dismiss();
            }
        });
        builder.setTitle("完成定向！").setMessage(
                "你成功完成了定向！记得领取奖励哦~").show();
    }
}
