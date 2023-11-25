package com.example.boxkeeper.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.boxkeeper.data.remote.SearchRemoteDataSource;
import com.example.boxkeeper.data.model.TrackingInfo;
import com.example.boxkeeper.data.api.retrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepositoryImpl implements SearchRepository {
    private final SearchRemoteDataSource sweetTrackerService;

    public SearchRepositoryImpl() {
        sweetTrackerService = retrofitClient.getService();
    }

    @Override
    public LiveData<TrackingInfo> getTrackingInfo(String apiKey, String courierCode, String invoiceNumber) {
        MutableLiveData<TrackingInfo> data = new MutableLiveData<>();

        sweetTrackerService.getTrackingInfo(apiKey, courierCode, invoiceNumber)
                .enqueue(new Callback<TrackingInfo>() {
                    @Override
                    public void onResponse(Call<TrackingInfo> call, Response<TrackingInfo> response) {
                        if (response.isSuccessful()) {
                            TrackingInfo trackingInfo = response.body();
                            data.setValue(trackingInfo);
                        } else {
                            data.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<TrackingInfo> call, Throwable t) {
                        data.setValue(null);
                    }
                });

        return data;
    }
}