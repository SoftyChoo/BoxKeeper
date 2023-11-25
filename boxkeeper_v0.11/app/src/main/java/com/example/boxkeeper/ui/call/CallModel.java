package com.example.boxkeeper.ui.call;

import android.os.Parcel;
import android.os.Parcelable;

public class CallModel implements Parcelable {
    private String title;
    private String description;
    private String phoneNumber;

    public CallModel(String title, String description, String phoneNumber) {
        this.title = title;
        this.description = description;
        this.phoneNumber = phoneNumber;
    }

    // Parcelable 인터페이스를 구현한 생성자
    protected CallModel(Parcel in) {
        title = in.readString();
        description = in.readString();
        phoneNumber = in.readString();
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

    public static final Creator<CallModel> CREATOR = new Creator<CallModel>() {
        @Override
        public CallModel createFromParcel(Parcel in) {
            return new CallModel(in);
        }

        @Override
        public CallModel[] newArray(int size) {
            return new CallModel[size];
        }
    };

    // Getter 및 Setter 메서드

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(phoneNumber);
    }
}

//
//public class CallModel {
//    private String title;
//    private String description;
//    private String phoneNumber;
//
//    public CallModel(String title, String description, String phoneNumber) {
//        this.title = title;
//        this.description = description;
//        this.phoneNumber = phoneNumber;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//}