package com.example.boxkeeper.data.model;

import java.io.Serializable;

public class TrackingDetail implements Serializable {
    private String kind;
    private String telno;
    private String timeString;
    private String where;

    public TrackingDetail(String kind, String telno, String timeString, String where) {
        this.kind = kind;
        this.telno = telno;
        this.timeString = timeString;
        this.where = where;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }
}
