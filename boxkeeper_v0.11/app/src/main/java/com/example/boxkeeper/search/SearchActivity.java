package com.example.boxkeeper.search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.boxkeeper.ListActivity;
import com.example.boxkeeper.R;
import com.example.boxkeeper.SlideKey;
import com.example.boxkeeper.call.CallActivity;
import com.example.boxkeeper.databinding.ActivitySearchBinding;
import com.example.boxkeeper.search.api.SweetTrackerService;
import com.example.boxkeeper.search.api.TrackingInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    private SweetTrackerService sweetTrackerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView(binding);
        initTab(binding);
    }

    private void initView(ActivitySearchBinding binding) {
        String slideKey = getIntent().getStringExtra(SlideKey.SLIDE_KEY);
        if (slideKey.equals(SlideKey.SLIDE_RIGHT)) {
            overridePendingTransition(R.anim.from_right_enter, R.anim.to_left_exit);
        } else if (slideKey.equals(SlideKey.SLIDE_LEFT)) {
            overridePendingTransition(R.anim.from_left_enter, R.anim.to_right_exit);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://info.sweettracker.co.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sweetTrackerService = retrofit.create(SweetTrackerService.class);

        binding.btnSearchBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자로부터 입력 받은 값
                String apiKey = "bHNwKJ4c1DiuHhXJ6pf1dg";
                String courierCode = "04";
                String invoiceNumber = "581884889182";

                // API 호출
                Call<TrackingInfo> call = sweetTrackerService.getTrackingInfo(apiKey, courierCode, invoiceNumber);
                call.enqueue(new Callback<TrackingInfo>() {
                    @Override
                    public void onResponse(Call<TrackingInfo> call, Response<TrackingInfo> response) {
                        if (response.isSuccessful()) {
                            TrackingInfo trackingInfo = response.body();
                            // 택배 배송 정보를 사용할 수 있습니다.
                            Log.d("searchActivitytiitititiit","trackingInfo"+trackingInfo );
                            String result = "배송 상태: " + trackingInfo.getCompleteYN() + "\n";
                            result += "배송 예정 시간: " + trackingInfo.getEstimate() + "\n";
                            result += "getAdUrl: " + trackingInfo.getAdUrl() + "\n";
                            result += "getResult: " + trackingInfo.getResult() + "\n";
                            result += "getProductInfo: " + trackingInfo.getProductInfo() + "\n";
                            result += "getOrderNumber: " + trackingInfo.getOrderNumber() + "\n";
                            // 필요한 정보를 가져와서 result에 추가

                            binding.testTextview.setText(result);
                        } else {
                            // API 호출 실패 처리
                            Toast.makeText(SearchActivity.this, "API 호출 실패", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TrackingInfo> call, Throwable t) {
                        // 네트워크 오류 처리
                        Log.e("SearchActivity API", "API 호출 실패: " + t.getMessage());
                        Toast.makeText(SearchActivity.this, "네트워크 오류: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    public void onBackPressed() {
        super.onBackPressed();

        if (isFinishing()) {
            // back 버튼으로 화면 종료가 야기되면 동작한다.
            overridePendingTransition(R.anim.from_left_enter, R.anim.to_right_exit);
        }
    }
    private void initTab(ActivitySearchBinding binding) {
        binding.btnHomeSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });
        binding.btnCallSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SearchActivity.this, CallActivity.class);
                intent.putExtra(SlideKey.SLIDE_KEY, SlideKey.SLIDE_LEFT);
                startActivity(intent);
                finish();
            }
        });
        binding.btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //
            }
        });
        binding.btnListSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SearchActivity.this, ListActivity.class);
                intent.putExtra(SlideKey.SLIDE_KEY, SlideKey.SLIDE_RIGHT);
                startActivity(intent);
                finish();
            }
        });
    }
}