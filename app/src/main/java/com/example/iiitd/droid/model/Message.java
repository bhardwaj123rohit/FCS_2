package com.example.iiitd.droid.model;

/**
 * Created by Rohit on 26/11/2016.
 */

public class Message {
    String name ;
    String contactNo;
    String body;
    String date;

    public Message(){

    }

    public Message(String name, String contactNo, String body, String date) {
        this.name = name;
        this.contactNo = contactNo;
        this.body = body;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
