package com.fdwireless.trace.infoclass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 汪励颢 on 2017/2/9.
 */

public class User implements Parcelable{
    private int id;
    private  String name;
    private  String pass;

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest,int flags){
        dest.writeInt(id);
        dest.writeString(name);

        dest.writeString(pass);
    }
    public static  final Parcelable.Creator<User>CREATOR =new Parcelable.Creator<User>(){
        @Override
     public  User createFromParcel(Parcel source){
            User user =new User();
            user.id=source.readInt();
            user.name=source.readString();

            user.pass=source.readString();
            return user;
        }
        @Override
        public User[] newArray(int size){
            return  new User[size];
        }
    };



}
