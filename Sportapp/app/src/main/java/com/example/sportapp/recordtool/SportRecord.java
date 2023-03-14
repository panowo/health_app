package com.example.sportapp.recordtool;

public class SportRecord {
    private String userid;
    private String date;
    private String time;
    private String place;
    private String dur_time;
    public SportRecord() {
    }

    public SportRecord(String id) {
        this.userid = id;
    }
    public SportRecord(String id, String da) {
        super();
        this.userid = id;
        this.date = da;
    }
    public SportRecord(String id, String da, String ti) {
        super();
        this.userid = id;
        this.date = da;
        this.time = ti;

    }
    public SportRecord(String id, String da, String ti,String pla) {
        super();
        this.userid = id;
        this.date = da;
        this.time = ti;
        this.place = pla;
    }

    public SportRecord(String id, String da, String ti,String pla,String dur) {
        super();
        this.userid = id;
        this.date = da;
        this.time = ti;
        this.place = pla;
        this.dur_time = dur;
    }

    public String getuseid() {
        return userid;
    }
    public void setuserid(String id) {
        this.userid = id;
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

    public String getDur_time(){
        return dur_time;
    }
    public void setDur_time(String dur){
        this.dur_time = dur;
    }
}
