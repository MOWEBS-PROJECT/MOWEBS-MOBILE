package com.example.mowebs;

public class ChatObject {

    private String value, from, date;
    private int isUpdate = 0;
    private long id;
//                CUSTOMER // CS
    public ChatObject() {
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

    public int getIsUpdate() {
        return isUpdate;
    }

    public long getId() {
        return id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
