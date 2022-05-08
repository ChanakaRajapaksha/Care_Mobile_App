package com.example.care_mobile_app;

public class CareAdminDocViewlRecycleModel {

    String name,specialization,hospital,workplace,whours,phonenumber,email,profilepictureurl;

    CareAdminDocViewlRecycleModel()
    {

    }

    public CareAdminDocViewlRecycleModel(String name, String specialization, String hospital, String workplace, String whours, String phonenumber, String email, String profilepictureurl) {
        this.name = name;
        this.specialization = specialization;
        this.hospital = hospital;
        this.workplace = workplace;
        this.whours = whours;
        this.phonenumber = phonenumber;
        this.email = email;
        this.profilepictureurl = profilepictureurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getWhours() {
        return whours;
    }

    public void setWhours(String whours) {
        this.whours = whours;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilepictureurl() {
        return profilepictureurl;
    }

    public void setProfilepictureurl(String profilepictureurl) {
        this.profilepictureurl = profilepictureurl;
    }





}

