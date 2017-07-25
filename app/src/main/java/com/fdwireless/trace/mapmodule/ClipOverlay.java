package com.fdwireless.trace.mapmodule;

//Author: Jin Xisen
//Purpose: Clip-board  over-layer of the aMap.
//update0212: 之前markerclicklistener有误，现在开始不使用Markerlist，而使用一个marker对应clip的HashMap.
//          Clip 类被踢出去了，变成了一个独立的public类。

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.android.volley.VolleyError;
import com.fdwireless.trace.httpclass.my_CallBackListener;
import com.fdwireless.trace.httpclass.my_GsonRequest;
import com.fdwireless.trace.infoclass.Clip;
import com.fdwireless.trace.infoclass.Player;
import com.fdwireless.trace.infoclass.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ClipOverlay {
    //该类型仅允许有一个实例
    private static ClipOverlay mClipOverlay;
    //常量声明
    private final int MSG = 0;
    private final int DEST =1;
    private final int AR = 2;

    private AMap amap;
    private Context context;
    private AppCompatActivity mMainActivity;
    private Player player;
    private User userData;
 //private AMapLocation location;
    //地图标签信息 & 已添加的标记的集合，服务器只需与标签信息交互，Marker类程序会确定
    private ArrayList<Clip> ClipList = new ArrayList<Clip>();
    private Map<Marker,Clip> markerClipMap = new HashMap<>();
    //服务器通信
    private final String URL = "http://115.159.198.209/Tracing/clipHandler.php";
    private my_GsonRequest<Clip[]> request =new my_GsonRequest<Clip[]>(context);

    public ArrayList<Clip> getClipList() {
        return ClipList;
    }
    private ClipOverlay(Context context,AMap amap,Player player,AppCompatActivity mMainActivity,User userData)
    {
        this.context = context;
        this.amap = amap;
        this.player = player;
        this.mMainActivity = mMainActivity;
        this.userData=userData;
        test();
        loadClipfromServer();
    }

    public static ClipOverlay getInstance(Context context,AMap amap,Player player,AppCompatActivity mMainActivity,User userData)
    {
        if(mClipOverlay == null)
            mClipOverlay = new ClipOverlay(context,amap,player,mMainActivity,userData);
        return mClipOverlay;
    }

    private void loadClipfromServer()
    {
        //我不知道一下子把所有标签信息下载过来怎么写啊，我也很绝望啊，于是就先这么写了，感觉不怎么靠谱（逃
        //location.getLatitude();
        //location.getLongitude();
        Log.d("loadClipfromServer",""+ClipList.size());
        double r=0.1;
        double Lat=this.player.getUsrLat();

        double Lon=this.player.getUsrLng();



        Map<String,String > params=new HashMap();

        params.put("Lat",Lat+"");
        params.put("Lon",Lon+"");
        params.put("r",r+"");

        //this.ClipList.clear(); //这样搞貌似不行
        request.post(URL, Clip[].class, new my_CallBackListener<Clip[]>() {
            @Override
            public void onSuccessResponse(Clip[] response) {

                    if (response!=null) {
                        for(int i=0;i<response.length;i++){
                             //  if(i<ClipList.size())
                            //ClipList.set(i,response[i]); //
                            //else

                            //Added by Season : 用来查重
                            boolean hasSame = false;
                            for(int j=0;j<ClipList.size();++j)
                            {
                                if(ClipList.get(j).isDuplicated(response[i]))
                                {
                                    hasSame =true;
                                    break;
                                }
                            }
                            if(!hasSame)
                                ClipList.add(response[i]);
                            Log.d("onlyforgame",""+response[i].onlyForGame +response[i].getId());
                        }

                    }
                else{
                        Log.e("TEST4","fail");
                    }

            }
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"载入标签信息错误2：请检查网络连接"+error.toString(),Toast.LENGTH_SHORT).show();
            }
        },params);
        Log.e("time",ClipList.size()+"");
    }

    public void loadClipfromLocal(ArrayList<Clip> newClips)
    {
        for(Clip c:newClips)
        {
            boolean hasSame = false;
            for(int i=0;i<ClipList.size();++i)
            {
                if(ClipList.get(i).isDuplicated(c))
                {
                    hasSame =true;
                    break;
                }
            }
            if(!hasSame)
                ClipList.add(c);
        }
    }

    // 每次位置更新应做出的反应
    public void locationChanged(AMapLocation location)
    {

       // Log.e("TEST2",Lat+"  "+Lon);
        loadClipfromServer();
        updateMarkers();
    }

    // 刷新显示的标记点的属性
    public void updateMarkers()
    {
        //test
        Log.d("updateMarkers",""+markerClipMap.size() + " "+ClipList.size());
        //add Markers if necessary
        int itrTail = markerClipMap.size();
        //int itrTail = MarkerList.size();
        while(itrTail < ClipList.size())
        {
            Clip c = ClipList.get(itrTail);
            Marker m = amap.addMarker(new MarkerOptions().position(new LatLng(c.Lat,c.Lon)).anchor(0.5f,0.5f));
            markerClipMap.put(m,c);
            AMap.OnMarkerClickListener l = new AMap.OnMarkerClickListener(){
                @Override
                public boolean onMarkerClick(Marker marker)
                {
                    Clip c = markerClipMap.get(marker);

                     Intent intent;
                    intent = new Intent(context,CardActivity.class);
                    intent.putExtra("info",c);
                    intent.putExtra("user_data",userData);
                    intent.putParcelableArrayListExtra("clip_list",getClipList());
                    intent.putExtra("reachable",euclidDist(
                            player.getUsrLat(),player.getUsrLng(),c.Lat,c.Lon) <= player.getSight());
                    Log.d("type",""+c.type);
                    mMainActivity.startActivityForResult(intent,2);
                    return false;
                }
            };
            amap.setOnMarkerClickListener(l);
            itrTail++;
        }
        //update options of the markers

        for(Map.Entry<Marker,Clip> mc: markerClipMap.entrySet())
        {
            Marker m = mc.getKey();
            Clip c = mc.getValue();
            double dist = euclidDist(player.getUsrLat(),player.getUsrLng(),c.Lat,c.Lon);
            switch(c.type)
            {
                case MSG:
                    if(dist > player.getSight())
                    {
                        BitmapDescriptor des = BitmapDescriptorFactory.fromResource(R.drawable.here3_grey);
                        m.setIcon(des);
                    }
                    else
                    {
                        BitmapDescriptor des = BitmapDescriptorFactory.fromResource(R.drawable.here3_blue);
                        m.setIcon(des);
                    }
                    break;
                case DEST:
                    if(c.onlyForGame && (dist > player.getSight() ||
                            !player.isGaming() || player.currentItem().isNot(c)))
                    {
                        m.setVisible(false); //目标点在范围外或者非游戏或者不是下一定向目标点时会不可见
                    }
                    else if(dist > player.getSight())
                    {
                        BitmapDescriptor des = BitmapDescriptorFactory.fromResource(R.drawable.here4_grey);
                        m.setIcon(des);
                        m.setVisible(true);
                    }
                    else
                    {
                        BitmapDescriptor des = BitmapDescriptorFactory.fromResource(R.drawable.here4_green);
                        m.setIcon(des);
                        m.setVisible(true);
                    }
                    break;
                case AR:
                    if(c.onlyForGame && (dist > player.getSight() ||
                            !player.isGaming() || player.currentItem().isNot(c)))
                    {
                        m.setVisible(false); //AR点在范围外或者非游戏或者不是下一定向目标点时会不可见
                    }
                    else if(dist > player.getSight())
                    {
                        BitmapDescriptor des = BitmapDescriptorFactory.fromResource(R.drawable.ar2_grey);
                        m.setIcon(des);
                        m.setVisible(true);
                    }
                    else
                    {
                        BitmapDescriptor des = BitmapDescriptorFactory.fromResource(R.drawable.ar2_active);
                        m.setIcon(des);
                        m.setVisible(true);
                    }
                    break;
                default:
            }
        }

    }

    // 欧几里得距离的计算
    private double euclidDist(double lat1, double lon1, double lat2, double lon2) {
        return AMapUtils.calculateLineDistance(new LatLng(lat1,lon1),new LatLng(lat2,lon2));
    }

    private void test()
    {

    }
}
