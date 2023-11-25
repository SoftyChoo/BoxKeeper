package com.example.boxkeeper.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.boxkeeper.data.model.TrackingInfo;
import com.example.boxkeeper.data.repository.SearchRepository;

public class SearchViewModel extends ViewModel {
    private final SearchRepository searchRepository;
    private MutableLiveData<TrackingInfo> trackingInfoLiveData = new MutableLiveData<>();

    public SearchViewModel(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public LiveData<TrackingInfo> getTrackingInfo(String apiKey, String courierCode, String invoiceNumber) {
        trackingInfoLiveData = (MutableLiveData<TrackingInfo>) searchRepository.getTrackingInfo(apiKey, courierCode, invoiceNumber);
        return trackingInfoLiveData;
    }

}
