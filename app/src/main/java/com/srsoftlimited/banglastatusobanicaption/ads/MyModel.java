package com.srsoftlimited.banglastatusobanicaption.ads;


import java.util.ArrayList;

public class MyModel {

    String name, status;
    int position, imgRes;
    ArrayList<String > statusList;


    public MyModel(String name, String status, int position) {
        this.name = name;
        this.status = status;
        this.position = position;
    }

    public MyModel(String subject, int imgRes, ArrayList<String > statusList) {
        this.status = subject;
        this.imgRes = imgRes;
        this.statusList = statusList;

    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public String getStatus() {
        return status;
    }

    public int getImgRes() {
        return imgRes;
    }

    public ArrayList<String> getStatusList() {
        return statusList;
    }
}

