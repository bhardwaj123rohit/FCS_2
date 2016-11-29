package com.example.iiitd.droid.model;

/**
 * Created by Rohit on 28/11/2016.
 */

public class MyCallLog {
    String name;
    String number;
    String date;
    String duration;
    String type;

    public void MyCallLog(){

    }

    public MyCallLog(String name, String number, String date, String duration,String type) {
        this.name = name;
        this.number = number;
        this.date = date;
        this.duration = duration;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
