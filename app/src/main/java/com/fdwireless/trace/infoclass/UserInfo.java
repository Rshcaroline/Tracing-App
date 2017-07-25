package com.fdwireless.trace.infoclass;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {

    private String name;
    private List<UserImgs> ui = new ArrayList<UserImgs>();
    private String ImageId;
    private String Content;

    public String getName() {
        return name;
    }
    public String getImageId() { return ImageId; }
    public String getContent() { return Content; }

    public void setName(String name) {
        this.name = name;
    }
    public void setContent(String Content) {
        this.Content = Content;
    }

    public List<UserImgs> getUi() {
        return ui;
    }

    public void setUi(List<UserImgs> ui) {
        this.ui = ui;
    }

    public UserInfo(String name,String ImageId,String Content){
        this.name=name;
        this.ImageId=ImageId;
        this.Content = Content;
    }

}
