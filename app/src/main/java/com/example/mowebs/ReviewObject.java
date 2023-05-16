package com.example.mowebs;

public class ReviewObject {

    private String uid;
    private long date;
    private String comments;
    private int rate;

    ReviewObject() {}

    public String getComments() {
        return comments;
    }

    public int getRate() {
        return rate;
    }

    public long getDate() {
        return date;
    }

    public String getUid() {
        return uid;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setUid(String id) {
        this.uid = id;
    }
}
