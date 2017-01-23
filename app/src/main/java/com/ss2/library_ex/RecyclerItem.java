package com.ss2.library_ex;

/**
 * Created by SunJae on 2017-01-21.
 */

public class RecyclerItem {
    String bitmap;
    String title;

    public void setTitle(String str){
        title = str;
    }
    public void setBitmap(String bitmap){
        this.bitmap = bitmap;
    }

    public String getTitle(){
        return title;
    }

    public String getBitmap(){
        return bitmap;
    }

}
