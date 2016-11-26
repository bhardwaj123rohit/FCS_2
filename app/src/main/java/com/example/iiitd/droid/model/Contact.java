package com.example.iiitd.droid.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rohit on 26/11/2016.
 */

public class Contact {

    String name;
    String number;
    String lastTimeContact;

    public Contact(){

    }
    public Contact(String name, String number, String lastTimeContact) {
        this.name = name;
        this.number = number;
        this.lastTimeContact = lastTimeContact;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("number", number);
        result.put("lastTimeContact", lastTimeContact);
        return result;
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

    public String getLastTimeContact() {
        return lastTimeContact;
    }

    public void setLastTimeContact(String lastTimeContact) {
        this.lastTimeContact = lastTimeContact;
    }
}
