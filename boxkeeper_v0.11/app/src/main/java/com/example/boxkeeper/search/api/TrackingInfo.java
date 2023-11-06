package com.example.boxkeeper.search.api;

import com.google.gson.annotations.SerializedName;

// API Response(Call)를 파싱할 모델 클래스
import com.google.gson.annotations.SerializedName;

public class TrackingInfo {
    @SerializedName("adUrl")
    private String adUrl;

    @SerializedName("complete")
    private boolean complete;

    @SerializedName("completeYN")
    private String completeYN;

    @SerializedName("estimate")
    private String estimate;

    @SerializedName("invoiceNo")
    private String invoiceNo;

    @SerializedName("itemImage")
    private String itemImage;

    @SerializedName("itemName")
    private String itemName;

    @SerializedName("level")
    private int level;

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