package com.ss2.library_ex;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by SunJae on 2017-01-23.
 */

public class Info_Data extends RealmObject {
    @PrimaryKey
    private int id;
    private String title;
    private String img_url;
    private String web_url;
    public void setId(int id){
        this.id = id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setImg_url(String img_url){
        this.img_url = img_url;
    }
    public void setWeb_url(String web_url){
        this.web_url = web_url;
    }
    public int getId(){
        return id;
    }
    public  String getTitle(){
        return  title;
    }
    public  String getImg_url(){
        return img_url;
    }
    public  String getWeb_url(){
        return web_url;
    }
}
