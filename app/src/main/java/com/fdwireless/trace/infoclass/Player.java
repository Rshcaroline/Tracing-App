package com.fdwireless.trace.infoclass;

// Author: Jin Xisen
// Purpose: Player data storage during the game.
// 0212更新：用于涟漪颜色功能可以精简掉
//          Sight变量代表用户的视野，默认为50米（即，用户接近AR、信息、目标点50M以内这些标签才会变得有效）
//          当初留下这个变量的原因是以为软件更倾向于“游戏向”，想把视野弄成升级技能来着= =
//          比如，赢一局比赛视野+5什么的……这种东西没有也没关系……

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.amap.api.location.AMapLocation;

import java.util.ArrayList;

public class Player
{
    //是否正在定向游戏过程中
    protected boolean Gaming = false;
    protected ArrayList<Clip> GameARDestList;
    public ArrayList<Clip> getGameARDestList(){return GameARDestList;}
    public void setGameARDestList(ArrayList<Clip> GameARDestList){this.GameARDestList=GameARDestList;}
    protected int step;
    public void stepInc(){step++;}
    public void stepClear(){step = 0;}
    public int getStep(){return step;}
    public boolean isFinish(){return step == GameARDestList.size();}
    public Clip currentItem()
    {
        if(step<GameARDestList.size())
            return GameARDestList.get(step);
        else
            return null;
    }

    public void setGaming(boolean b){Gaming = b;}
    public boolean isGaming(){return Gaming;}
    //用户名
    protected String usrName;
    protected int usrId;
    //用户当前颜色（用于显示地图涟漪效果）
    protected int usrColor;
    protected int usrColor2;
    //使用默认头像（调试），自定义头像的ID（类型可更改）
    //此处需要修改图片变量的类型
    protected Bitmap Portrait = null;
    public Bitmap getPortrait(){return Portrait;}

    public void setPortriatRescale(Bitmap bitmapRaw)
    {
        int size = 80;
       // float heightScale = (float)size / bitmapRaw.getHeight();
        //float widthScale = (float)size / bitmapRaw.getWidth();
        float scale = (float)size / Math.min(bitmapRaw.getHeight(),bitmapRaw.getWidth());
        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale);
        Bitmap bitmap = Bitmap.createBitmap(bitmapRaw,0,0,bitmapRaw.getWidth(),bitmapRaw.getHeight(),matrix,true);
        int wh = Math.min(bitmap.getHeight(),bitmap.getWidth());
        Bitmap circleBitmap = Bitmap.createBitmap(wh,wh, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, wh,wh);
        final RectF rectF = new RectF(new Rect(0, 0, wh,wh));
        float roundPx = 0.0f;
        if (bitmap.getWidth() < bitmap.getHeight()) {
            roundPx = bitmap.getHeight() / 2.0f;
        } else {
            roundPx = bitmap.getWidth() / 2.0f;
        }
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.WHITE);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        final Rect src = new Rect(0, 0, wh,wh);
        canvas.drawBitmap(bitmap, src, rect, paint);
        Portrait = circleBitmap;
    }

    //队伍名字，队伍颜色，个人颜色
    protected String teamName;

    protected int personalColor;
    protected int personalColor2;
    public int getUsrColor(){return usrColor;}
    public int getUsrColor2(){return usrColor2;}

    //用户位置
    protected double usrLat;
    protected double usrLng;
    public double getUsrLat(){return usrLat;}
    public double getUsrLng(){return usrLng;}

    public double sight; //视野，表示可搜索地图物件的范围，默认50m
    public double getSight(){return sight;}

    public Player()
    {
        init();
    }

    protected void init()
    {
        usrName = "TraceUser";
        usrId = 1000;
        personalColor = usrColor = Color.argb(100, 135, 206, 255);
        personalColor2 =usrColor2 = Color.argb(255, 30, 144, 255);
        Gaming = false;
        usrLat = 0;
        usrLng = 0;
        sight = 80;
    }

    public void updatePlayerLoc(AMapLocation location)
    {
        usrLat = location.getLatitude();
        usrLng = location.getLongitude();
    }

    //给颜色@param color设定透明度alpha并返回
    public int getAlphaColor(int alpha,int color)
    {
        return color & 0x00FFFFFF | (alpha << 24);
    }
}
