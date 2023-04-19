package com.example.mowebs;

public class ChatObject {

    public final static String CUSTOMER = "CUSTOMER";
    public final static String CS = "CS";
    private String value, _from, date;
    private boolean isupdated = false;
    private boolean isUpdating = false;
    private long _id;

    public ChatObject() {
    }

    // mengembalikan date
    public String getDate() {
        return date;
    }

    // mengembalikan value
    public String getValue() {
        return value;
    }

    // mengembalikan from
    public String get_from() {
        return _from;
    }

    // mengembalikan isupdated
    public boolean getIsUpdated() {
        return isupdated;
    }

    // mengembalikan id
    public long get_id() {
        return _id;
    }

    public void setIsUpdating(boolean updating) {
        isUpdating = updating;
    }

    public boolean getIsUpdating() {
        return isUpdating;
    }

    /** *
     * the date to set
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /***
     * the form to set
     * @param _from
     */
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

    /***
     * the id to set
     * @param _id
     */
    public void set_id(long _id) {
        this._id = _id;
    }

    /***
     * the isUpdated to set
     * @param isUpdate
     */
    public void setIsUpdated(int isUpdate) {
        this.isupdated = (isUpdate == 1)? true : false;
    }

    /***
     * the value to set
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
