package com.example.mowebs;

public class ChatObject {

    String value;
    String date;
    String from; // customer && cs

    public ChatObject(String value, String date, String from) {
        this.value = value;
        this.date = date;
        this.from = from;
    }

    public String getDate() {
        return date;
    }

    public String getValue() {
        return value;
    }

    public String getFrom() {
        return from;
    }

}
