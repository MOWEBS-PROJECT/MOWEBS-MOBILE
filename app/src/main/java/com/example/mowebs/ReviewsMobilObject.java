package com.example.mowebs;

public class ReviewsMobilObject {

    private String id;
    private String comments;
    private long date;
    private int rate;

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public int getRate() {
        return rate;
    }

    public long getDate() {
        return date;
    }

    public String getComments() {
        return comments;
    }
}
