package com.example.boxkeeper.search.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// api 요청용 interface
public interface SweetTrackerService {
    @GET("/api/v1/trackingInfo")
    Call<TrackingInfo> getTrackingInfo(
            @Query("t_key") String apiKey,
            @Query("t_code") String courierCode, // 택배사 코드
            @Query("t_invoice") String invoiceNumber // 운송장 번호
    );
}
