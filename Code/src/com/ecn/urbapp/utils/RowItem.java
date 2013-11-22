package com.ecn.urbapp.utils;

public class RowItem {
    private String imagePath;
    private String title;
    private String desc;
 
    public RowItem(String imagePath, String title, String desc) {
        this.imagePath = imagePath;
        this.title = title;
        this.desc = desc;
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return title + "\n" + desc;
    }
}