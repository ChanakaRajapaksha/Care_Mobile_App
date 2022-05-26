package com.example.care_mobile_app;

public class TipsModel {
    String title, description, turl;

    TipsModel(){

    }

    public TipsModel(String title, String description, String turl) {
        this.title = title;
        this.description = description;
        this.turl = turl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTurl() {
        return turl;
    }

    public void setTurl(String turl) {
        this.turl = turl;
    }
}
