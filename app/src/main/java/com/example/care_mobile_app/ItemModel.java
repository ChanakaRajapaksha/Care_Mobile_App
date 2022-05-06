package com.example.care_mobile_app;

public class ItemModel {

    String title,category,con,img;

    ItemModel()
    {

    }

    public ItemModel(String title, String category, String con, String img) {
        this.title = title;
        this.category = category;
        this.con = con;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
