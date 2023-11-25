package com.example.boxkeeper.data.repository;

import androidx.lifecycle.LiveData;

import com.example.boxkeeper.data.api.TrackingInfo;

public interface SearchRepository {
    LiveData<TrackingInfo> getTrackingInfo(String apiKey, String courierCode, String invoiceNumber);
}