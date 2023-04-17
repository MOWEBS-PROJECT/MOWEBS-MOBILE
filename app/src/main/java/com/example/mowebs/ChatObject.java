package com.example.mowebs;

public class ChatObject {

    public final static String CUSTOMER = "CUSTOMER";
    public final static String CS = "CS";
    private String value, _from, date;
    private boolean isupdated = false;
    private boolean isUpdating = false;
    private long _id;
//                CUSTOMER // CS
    public ChatObject() {
    }

    public String getDate() {
        return date;
    }

    public String getValue() {
        return value;
    }

    public String get_from() {
        return _from;
    }

    public boolean getIsUpdated() {
        return isupdated;
    }

    public long get_id() {
        return _id;
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

    public void set_from(String _from) {
        switch (_from) {
            case CUSTOMER:
                this._from = CUSTOMER;
                break;
            case CS:
                this._from = CS;
                break;
        }
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public void setIsUpdated(int isUpdate) {
        this.isupdated = (isUpdate == 1)? true : false;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
