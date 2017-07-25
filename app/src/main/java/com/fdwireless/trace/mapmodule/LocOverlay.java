package com.fdwireless.trace.mapmodule;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.SystemClock;
import android.util.Log;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.fdwireless.trace.infoclass.Player;
import com.fdwireless.trace.infoclass.User;

import java.util.Timer;
import java.util.TimerTask;
// Author: Jin Xisen
// Purpose: Located point displaying layer of the AMap.

public class LocOverlay {
    //该类只允许存在一个实例
    private static LocOverlay mlocoverlay;
    private LatLng point;
    private float radius;
    private Marker locMarker;
    private Circle locCircle;
    private Circle c = null;
    private long start;
    private final Interpolator interpolator = new CycleInterpolator(1);
    private final Interpolator interpolator1 = new LinearInterpolator();
    private TimerTask mTimerTask;
    private Timer mTimer = new Timer();

    private AMap aMap;
    private Player player;
    private User userData;
    private Context context;
    private Bitmap headBmp;

    private LocOverlay(Context context, AMap amap, Player player,User user) {
        this.aMap = amap;
        this.player = player;
        this.context = context;
        this.userData = user;
    }

    public static LocOverlay getInstance(Context context,AMap amap,Player player,User userData){
        if (mlocoverlay == null) {
            mlocoverlay = new LocOverlay(context,amap,player,userData);
        }
        return mlocoverlay;
    }

    //位置变化时调用这个方法，实现marker位置变化

    public void locationChanged (AMapLocation aMapLocation) {
        LatLng location = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
        Log.e("TEST",location.latitude+"  "+location.longitude);
        this.point = location;
        this.radius = aMapLocation.getAccuracy();

        if (locMarker == null) {
            addMarker();
        }
        if(c == null)
        {
            addAnimationCircle();
        }
        if (locCircle == null) {
            addCircle();
        }
        moveLocationMarker();

        locCircle.setRadius(radius);
    }

    public int setMarkerPortrait(){
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(player.getPortrait());
        if(locMarker == null)
            return 1;
        else {
            locMarker.setIcon(des);
            return -1;
        }
    }
    //平滑移动动画

    private void moveLocationMarker() {
        final LatLng startPoint  = locMarker.getPosition();
        final LatLng endPoint  = point;
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        anim.addUpdateListener(new AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LatLng target = (LatLng) valueAnimator.getAnimatedValue();
                locCircle.setCenter(target);
                locMarker.setPosition(target);
                c.setCenter(target);
            }
        });
        anim.setDuration(1000);
        anim.start();
    }

    private void addMarker() {
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(player.getPortrait());
        locMarker = aMap.addMarker(new MarkerOptions().position(point).icon(des)
                .anchor(0.5f, 0.5f));

    }
    //添加定位精度圈

    private void addCircle() {
        locCircle = aMap.addCircle(new CircleOptions().center(point).radius(radius)
                .fillColor(player.getUsrColor())
                .strokeColor(player.getUsrColor2())
                .strokeWidth(2));
    }

    private void addAnimationCircle()
    {
        c = aMap.addCircle(new CircleOptions().center(point)
                .fillColor(player.getAlphaColor(50,player.getUsrColor()))
                .radius(player.getSight()/2).strokeColor(player.getAlphaColor(100,player.getUsrColor2()))
                .strokeWidth(0));
       // Toast.makeText(context,""+player.getSight()/2,Toast.LENGTH_SHORT).show();

        Scalecircle(c);
    }

    public void remove(){
        if (locMarker != null){
            locMarker.remove();
        }
        if (locCircle != null){
            locCircle.remove();
        }
    }

    public class PointEvaluator implements TypeEvaluator {
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            LatLng startPoint = (LatLng) startValue;
            LatLng endPoint = (LatLng) endValue;
            double x = startPoint.latitude + fraction * (endPoint.latitude - startPoint.latitude);
            double y = startPoint.longitude + fraction * (endPoint.longitude - startPoint.longitude);
            LatLng point = new LatLng(x, y);
            return point;
        }
    }

    public void Scalecircle(final Circle circle) {
        start = SystemClock.uptimeMillis();
        mTimerTask = new circleTask(circle, 1500);
        mTimer.schedule(mTimerTask, 0, 30);
    }



    private  class circleTask extends TimerTask {
        private double r;
        private Circle circle;
        private long duration = 1500;

        public circleTask(Circle circle, long rate){
            this.circle = circle;
            this.r = circle.getRadius();
            if (rate > 0 ) {
                this.duration = rate;
            }
        }
        @Override
        public void run() {
            try {
                long elapsed = SystemClock.uptimeMillis() - start;
                float input = (float)elapsed / duration;
                //外圈放大后消失
                float t = interpolator1.getInterpolation(input) * 0.5f;
                double r1 = (t+1) * r;
                int alpha = (int)((1-t) * 100+30);
                int oldRGB = circle.getFillColor() & 0x00FFFFFF;
                circle.setRadius(r1);
                circle.setFillColor(Color.argb(alpha,(oldRGB >> 16)&0xFF,(oldRGB>>8)&0xFF,oldRGB & 0xFF));
                if (input > 2){
                    start = SystemClock.uptimeMillis();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
