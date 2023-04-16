package com.example.mowebs;

public class ChatObject {

    private final static String CUSTOMER = "CUSTOMER";
    private final static String CS = "CS";
    private String value, from, date;
    private boolean isUpdated = false;
    private boolean isUpdating = false;
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

    public boolean getIsUpdated() {
        return isUpdated;
    }

    public long getId() {
        return id;
    }

    public void setIsUpdating(boolean updating) {
        isUpdating = updating;
    }

    public boolean getIsUpdating() {
        return isUpdating;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFrom(String from) {
        switch (from) {
            case CUSTOMER:
                this.from = CUSTOMER;
                break;
            case CS:
                this.from = CS;
                break;
        }
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIsUpdated(boolean isUpdate) {
        this.isUpdated = isUpdate;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
