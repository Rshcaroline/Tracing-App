package com.fdwireless.trace.infoclass;

import android.os.Parcel;
import android.os.Parcelable;

// Author: Jin Xisen
// Purpose: 一个独立的便签类型的定义，只包含需要在地图界面显示、或者简短的过渡界面需要呈现的信息
// 修改：去掉了一些没用的变量；MessageInfo类由于在地图界面中没有作用，故删除

public class Clip implements Parcelable
{
    public static final int MSG = 0; //留言点，在地图显示为[...]
    public static final int DEST =1; //目标点（无AR）显示为[!]
    public static final int AR = 2; //AR点，在地图显示为[AR]

    public String id;
    public int type; //只可以是 MSG DEST AR中的一种
    public double Lon;
    public double Lat;
    public  String name;
    public String getTime() {
    return time;
}

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getLon() {
        return Lon;
    }

    public void setLon(double lon) {
        Lon = lon;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String time;
    public boolean onlyForGame = false; //设置为true，则只有在游戏中 && 正确的顺序时才会显示该标记

    public String text = null; //用于在过渡界面显示的简短的说明
    public int owner;

    public String question = null; //小花以前说定向还要加个问答的框吗
    public String answer = null; //同上
    public String direction = null;
    public int num;
    public int imgResource=-1;
    public Clip(int type,double Lat,double Lon, String id)
    {
        this.id = id;
        this.type = type;
        this.Lon = Lon;
        this.Lat = Lat;
    }
    public Clip(int type,double Lat,double Lon,String intro,boolean onlyForGame,String id)
    {
        this.id = id;
        this.type = type;
        this.Lon = Lon;
        this.Lat = Lat;
        this.text = intro;
        this.onlyForGame = onlyForGame;
    }
    public Clip(){}

    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(id);
        dest.writeDouble(Lat);
        dest.writeDouble(Lon);
        dest.writeInt(type);
        dest.writeString(text);
        dest.writeString(question);
        dest.writeString(answer);
        dest.writeString(direction);
        dest.writeString(name);
        dest.writeInt(owner);
        dest.writeInt(num);
        dest.writeString(time);

    }
    public static final Parcelable.Creator<Clip>CREATOR =new Parcelable.Creator<Clip>(){
        @Override
        public Clip createFromParcel(Parcel source){
            Clip clip = new Clip();

            clip.id = source.readString();
            clip.Lat = source.readDouble();
            clip.Lon = source.readDouble();
            clip.type = source.readInt();
            clip.text = source.readString();
            clip.question = source.readString();
            clip.answer = source.readString();
            clip.direction = source.readString();
            clip.name=source.readString();
            clip.owner=source.readInt();
            clip.num=source.readInt();
            clip.time =source.readString();
            return clip;
        }

        @Override
        public Clip[] newArray(int size){
            return  new Clip[size];
        }
    };

    public Clip setQAD(String ques,String ans,String direction)
    {
        this.question = ques;
        this.answer = ans;
        this.direction = direction;
        return this;
    }

    public Clip setImg(int img)
    {
        this.imgResource = img;
        return this;
    }
    public int getImg()
    {
        return this.imgResource;
    }

    public boolean isDuplicated(Clip another)
    {
        return another.id.equals(this.id);
    }

    public boolean isNot(Clip another)
    {
        return !another.id .equals(this.id);
    }
}
