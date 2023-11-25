package com.example.boxkeeper.ui.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.boxkeeper.data.repository.SearchRepository;
import com.example.boxkeeper.data.repository.SearchRepositoryImpl;
import com.example.boxkeeper.databinding.ActivitySearchBinding;
import com.example.boxkeeper.R;
import com.example.boxkeeper.data.model.TrackingDetail;
import com.example.boxkeeper.data.model.TrackingInfo;
import com.example.boxkeeper.ui.common.SlideKey;
import com.example.boxkeeper.ui.call.CallActivity;
import com.example.boxkeeper.ui.list.ImageListActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class SearchActivity extends AppCompatActivity {
    private SearchViewModel searchViewModel;
    private final SearchListAdapter listAdapter = new SearchListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // spinner
        List<String> spinnerArray = Arrays.asList(getResources().getStringArray(R.array.box_company));
        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), R.layout.spinner_search_item, spinnerArray);
        adapter.setDropDownViewResource(R.layout.spinner_search_item);
        binding.spinner.setAdapter(adapter);

        // slide
        String slideKey = getIntent().getStringExtra(SlideKey.SLIDE_KEY);
        if (slideKey.equals(SlideKey.SLIDE_RIGHT)) {
            overridePendingTransition(R.anim.from_right_enter, R.anim.to_left_exit);
        } else if (slideKey.equals(SlideKey.SLIDE_LEFT)) {
            overridePendingTransition(R.anim.from_left_enter, R.anim.to_right_exit);
        }

        // Create an instance of SearchViewModel
        SearchRepository searchRepository = new SearchRepositoryImpl();
        SearchViewModelFactory factory = new SearchViewModelFactory(searchRepository);
        searchViewModel = new ViewModelProvider(this, factory).get(SearchViewModel.class);


        initView(binding);
        initTab(binding);
        initViewModel(binding);
    }

    private void initViewModel(ActivitySearchBinding binding) {

    }

    private void initView(ActivitySearchBinding binding) {
        binding.rvSearchList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvSearchList.setAdapter(listAdapter);

        binding.btnRefresh.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setVisibilityRefresh(binding);
            }
        });


        binding.btnSearchBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apiKey = "bHNwKJ4c1DiuHhXJ6pf1dg";
                String courierCode = "";
                String invoiceNumber = binding.etBox.getText().toString();
                int spinnerPosition = binding.spinner.getSelectedItemPosition();

                if (spinnerPosition == 0 || invoiceNumber.equals("")) {
                    Toast.makeText(getBaseContext(), "빈칸을 모두 채워주세요", Toast.LENGTH_SHORT).show();
                } else {
                    // 사용자로부터 입력 받은 값

                    switch (spinnerPosition) {
                        case 1:
                            courierCode = "01";
                            break;
                        case 2:
                            courierCode = "04";
                            break;
                        case 3:
                            courierCode = "05";
                            break;
                        case 4:
                            courierCode = "06";
                            break;
                        case 5:
                            courierCode = "08";
                            break;
                        case 6:
                            courierCode = "46";
                            break;
                    }

                    // 초기 설정
                    binding.ivLevel1.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_level1));
                    binding.ivLevel2.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_level2));
                    binding.ivLevel3.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_level3));
                    binding.ivLevel4.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_level4));
                    binding.ivLevel5.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_level5));
                    binding.ivLevel6.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_level6));
                    binding.tvStateDeliveryTime.setText("");
                    binding.tvStateDelivery.setText("");


                    searchViewModel.getTrackingInfo(apiKey, courierCode, invoiceNumber).observe(SearchActivity.this, new Observer<TrackingInfo>() {
                        @Override
                        public void onChanged(TrackingInfo trackingInfo) {
                            handleTrackingInfoResponse(binding, trackingInfo);
                        }
                    });

                }
            }
        });
    }

    private void handleTrackingInfoResponse(ActivitySearchBinding binding, TrackingInfo trackingInfo) {
        String spinnerText = binding.spinner.getSelectedItem().toString();
        if (trackingInfo == null) {
            Toast.makeText(getBaseContext(), "API 호출 실패", Toast.LENGTH_SHORT).show();
        } else if (trackingInfo.getCompleteYN() == null) {
            Toast.makeText(getBaseContext(), "잘못된 데이터를 입력하셨습니다.\n다시 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            setVisibilitySearch(binding);
            binding.tvDeliveryNumber.setText(trackingInfo.getInvoiceNo());
            binding.tvDeliveryCompany.setText(spinnerText); // Set your company name here

            switch (trackingInfo.getLevel()) {
                case 1:{
                    binding.ivLevel1.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_level1_color));
                    binding.ivState.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_level1_color));
                    binding.tvStateDelivery.setText("배송준비중");
                    break;
                }
                case 2: {
                    binding.ivLevel2.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_level2_color));
                    binding.ivState.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_level2_color));
                    binding.tvStateDelivery.setText("집화완료");
                    break;
                }
                case 3: {
                    binding.ivLevel3.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_level3_color));
                    binding.ivState.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_level3_color));
                    binding.tvStateDelivery.setText("배송중");
                    break;
                }
                case 4: {
                    binding.ivLevel4.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_level4_color));
                    binding.ivState.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_level4_color));
                    binding.tvStateDelivery.setText("지점도착");
                    break;
                }
                case 5: {
                    binding.ivLevel5.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_level5_color));
                    binding.ivState.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_level5_color));
                    binding.tvStateDelivery.setText("배송출발");
                    break;
                }
                case 6: {
                    binding.ivLevel6.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_level6_color));
                    binding.ivState.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_level6_color));
                    binding.tvStateDelivery.setText("배송완료");
                    break;
                }
            }

            if (trackingInfo.getEstimate() == null){
                binding.tvStateDeliveryTime.setVisibility(View.INVISIBLE);
            }
            else {
                binding.tvStateDeliveryTime.setText(trackingInfo.getEstimate());
            }

            List<TrackingDetail> reversedList = new ArrayList<>(trackingInfo.getTrackingDetails());
            Collections.reverse(reversedList);
            listAdapter.submitList(reversedList);
        }
    }
    private void setVisibilitySearch(ActivitySearchBinding binding){
        binding.spinner.setVisibility(View.INVISIBLE);
        binding.etBox.setVisibility(View.INVISIBLE);
        binding.btnSearchBox.setVisibility(View.INVISIBLE);

        binding.tvTitle.setVisibility(View.VISIBLE);
        binding.tvDeliveryNumber.setVisibility(View.VISIBLE);
        binding.tvDeliveryCompany.setVisibility(View.VISIBLE);

        binding.rvSearchList.setVisibility(View.VISIBLE);
        binding.ivLevel1.setVisibility(View.VISIBLE);
        binding.ivLevel2.setVisibility(View.VISIBLE);
        binding.ivLevel3.setVisibility(View.VISIBLE);
        binding.ivLevel4.setVisibility(View.VISIBLE);
        binding.ivLevel5.setVisibility(View.VISIBLE);
        binding.ivLevel6.setVisibility(View.VISIBLE);
        binding.tvLevel1.setVisibility(View.VISIBLE);
        binding.tvLevel2.setVisibility(View.VISIBLE);
        binding.tvLevel3.setVisibility(View.VISIBLE);
        binding.tvLevel4.setVisibility(View.VISIBLE);
        binding.tvLevel5.setVisibility(View.VISIBLE);
        binding.tvLevel6.setVisibility(View.VISIBLE);
        binding.btnRefresh.setVisibility(View.VISIBLE);

        binding.ivState.setVisibility(View.VISIBLE);
        binding.tvStateDelivery.setVisibility(View.VISIBLE);
        binding.tvStateDeliveryTime.setVisibility(View.VISIBLE);
        binding.tv1.setVisibility(View.VISIBLE);
        binding.tv2.setVisibility(View.VISIBLE);
    }

    private void setVisibilityRefresh(ActivitySearchBinding binding){
        binding.spinner.setVisibility(View.VISIBLE);
        binding.etBox.setVisibility(View.VISIBLE);
        binding.btnSearchBox.setVisibility(View.VISIBLE);

        binding.tvTitle.setVisibility(View.INVISIBLE);
        binding.tvDeliveryNumber.setVisibility(View.INVISIBLE);
        binding.tvDeliveryCompany.setVisibility(View.INVISIBLE);

        binding.rvSearchList.setVisibility(View.INVISIBLE);
        binding.ivLevel1.setVisibility(View.INVISIBLE);
        binding.ivLevel2.setVisibility(View.INVISIBLE);
        binding.ivLevel3.setVisibility(View.INVISIBLE);
        binding.ivLevel4.setVisibility(View.INVISIBLE);
        binding.ivLevel5.setVisibility(View.INVISIBLE);
        binding.ivLevel6.setVisibility(View.INVISIBLE);
        binding.tvLevel1.setVisibility(View.INVISIBLE);
        binding.tvLevel2.setVisibility(View.INVISIBLE);
        binding.tvLevel3.setVisibility(View.INVISIBLE);
        binding.tvLevel4.setVisibility(View.INVISIBLE);
        binding.tvLevel5.setVisibility(View.INVISIBLE);
        binding.tvLevel6.setVisibility(View.GONE);

        binding.ivState.setVisibility(View.INVISIBLE);
        binding.tvStateDelivery.setVisibility(View.INVISIBLE);
        binding.tvStateDeliveryTime.setVisibility(View.INVISIBLE);
        binding.tv1.setVisibility(View.INVISIBLE);
        binding.tv2.setVisibility(View.INVISIBLE);
    }


    public void onBackPressed() {
        super.onBackPressed();

        if (isFinishing()) {
            // back 버튼으로 화면 종료가 야기되면 동작한다.
            overridePendingTransition(R.anim.from_left_enter, R.anim.to_right_exit);
        }
    }

    private void initTab(ActivitySearchBinding binding) {
        binding.btnHomeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.btnCallSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, CallActivity.class);
                intent.putExtra(SlideKey.SLIDE_KEY, SlideKey.SLIDE_LEFT);
                startActivity(intent);
                finish();
            }
        });
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
        binding.btnListSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, ImageListActivity.class);
                intent.putExtra(SlideKey.SLIDE_KEY, SlideKey.SLIDE_RIGHT);
                startActivity(intent);
                finish();
            }
        });
    }
}