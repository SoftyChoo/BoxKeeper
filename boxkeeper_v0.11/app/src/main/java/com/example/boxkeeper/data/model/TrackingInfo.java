package com.example.boxkeeper.data.model;

import com.google.gson.annotations.SerializedName;

// API Response(Call)를 파싱할 모델 클래스

import java.util.List;

public class TrackingInfo {
    @SerializedName("adUrl")
    private String adUrl;

    @SerializedName("complete")
    private boolean complete;

    @SerializedName("completeYN") // 배송 상태
    private String completeYN;

    @SerializedName("estimate") // 배송시간
    private String estimate;

    @SerializedName("level") // 배송 상태 level
    private int level;

    @SerializedName("trackingDetails") // 디테일들 구현
    private List<TrackingDetail> trackingDetails;

    @SerializedName("invoiceNo")
    private String invoiceNo;

    @SerializedName("itemImage")
    private String itemImage;

    @SerializedName("itemName")
    private String itemName;


    @SerializedName("orderNumber")
    private String orderNumber;

    @SerializedName("productInfo")
    private String productInfo;

    @SerializedName("receiverAddr")
    private String receiverAddr;

    @SerializedName("receiverName")
    private String receiverName;

    @SerializedName("recipient")
    private String recipient;

    @SerializedName("result")
    private String result;

    @SerializedName("senderName")
    private String senderName;

    // 이하 getter 및 setter 메서드도 정의해야 합니다.

    public String getAdUrl() {
        return adUrl;
    }

    public boolean isComplete() {
        return complete;
    }

    public String getCompleteYN() {
        return completeYN;
    }

    public String getEstimate() {
        return estimate;
    }
    public List<TrackingDetail> getTrackingDetails() {return trackingDetails;}

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public String getItemImage() {
        return itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public int getLevel() {
        return level;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public String getReceiverAddr() {
        return receiverAddr;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getResult() {
        return result;
    }

    public String getSenderName() {
        return senderName;

    }
}