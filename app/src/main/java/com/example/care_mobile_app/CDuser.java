package com.example.care_mobile_app;

public class CDuser {


    private String Uid;
    private  String email;
    private String password;
    private int udType;

    public CDuser() {

    }

    public CDuser(String uid, String email, String password, int udType) {
        this.Uid = uid;
        this.email = email;
        this.password = password;
        this.udType = udType;

    }

    public int getUdType() {
        return udType;
    }

    public void setUdType(int udType) {
        this.udType = udType;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
