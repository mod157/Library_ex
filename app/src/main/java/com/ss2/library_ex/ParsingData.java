package com.ss2.library_ex;

/**
 * Created by SunJae on 2017-01-23.
 */

public class ParsingData {
    private String id;
    private String title;
    private String img_url;
    private String web_url;

    ParsingData(String id, String title, String img_url, String web_url){
        this.id = id;
        this.title = title;
        this.img_url = img_url;
        this.web_url = web_url;
    }
    public void setId(String id){
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
    public String getId(){
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
