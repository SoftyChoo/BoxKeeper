package com.example.boxkeeper.call;

public class CallModel {
    private String title;
    private String description;
    private String phoneNumber;

    public CallModel(String title, String description, String phoneNumber) {
        this.title = title;
        this.description = description;
        this.phoneNumber = phoneNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}