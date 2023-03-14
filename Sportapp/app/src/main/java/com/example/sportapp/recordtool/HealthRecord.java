package com.example.sportapp.recordtool;

public class HealthRecord {

    private String username;
    private String date;
    private String time;
    private String place;
    private String state;
    private String tem;

    public HealthRecord(String id, String da, String ti,String pla,String sta,String te) {
        super();
        this.username = id;
        this.date = da;
        this.time = ti;
        this.place=pla;
        this.state = sta;
        this.tem = te;
    }

    public String getuseid() {
        return username;
    }
    public void setuserid(String id) {
        this.username = id;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String da) {
        this.date = da;
    }

    public String getTime(){
        return time;
    }
    public void setTime(String ti){
        this.time = ti;
    }

    public String getPlace(){
        return place;
    }
    public void setPlace(String pla){
        this.place = pla;
    }

    public String getState(){
        return state;
    }

    public String getTem(){
        return tem;
    }

}
