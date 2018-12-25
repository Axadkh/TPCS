package com.police.entity;

import java.io.Serializable;

public class Challan implements Serializable {



    private int challanId;
    private String name;
    private String license;
    private String time;
    private String chtitle;
    private String location;
    private  String vid;

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getVid() {
        return vid;
    }

    private String userName;


    public Challan() {

    }


    public Challan(int challanId, String name, String license, String time, String chtitle, String location, String userName,String date,String vid) {
        this.challanId = challanId;
        this.name = name;
        this.license = license;
        this.time = time;
        this.chtitle = chtitle;
        this.location = location;
        this.userName = userName;
        this.date = date;
    }



    public int getChallanId() {
        return challanId;
    }

    public void setChallanId(int challanId) {
        this.challanId = challanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getChtitle() {
        return chtitle;
    }

    public void setChtitle(String chtitle) {
        this.chtitle = chtitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userid) {
        this.userName = userid;
    }
}