package com.example.boxkeeper.call;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.boxkeeper.ListActivity;
import com.example.boxkeeper.R;
import com.example.boxkeeper.SearchActivity;
import com.example.boxkeeper.SlideKey;
import com.example.boxkeeper.databinding.ActivityCallBinding;

import java.util.ArrayList;
import java.util.List;

public class CallActivity extends AppCompatActivity {

    private final CallListAdapter listAdapter = new CallListAdapter(item ->
//            Toast.makeText(getBaseContext(), "item: "+ item, Toast.LENGTH_SHORT).show()
            Toast.makeText(getBaseContext(), "item: "+ item.getPhoneNumber(), Toast.LENGTH_SHORT).show()
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCallBinding binding = ActivityCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initTab(binding);
        initView(binding);
    }

    private void initView(ActivityCallBinding binding){
        binding.rvCallList.setLayoutManager(new LinearLayoutManager(this));
        binding.rvCallList.setAdapter(listAdapter);

        listAdapter.submitList(createDummyData());
    }
    private List<CallModel> createDummyData() {
        // 여기에서 더미 데이터를 생성하고 반환합니다.
        List<CallModel> dummyData = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            dummyData.add(new CallModel("Title " + i, "Description " + i, "PhoneNumber " + i));
        }
        return dummyData;
    }

    public void onBackPressed() {
        super.onBackPressed();

        if (isFinishing()) {
            // back 버튼으로 화면 종료가 야기되면 동작한다.
            overridePendingTransition(R.anim.from_left_enter, R.anim.to_right_exit);
        }
    }

    private void initTab(ActivityCallBinding binding) {

        String slideKey = getIntent().getStringExtra(SlideKey.SLIDE_KEY);

        if (slideKey.equals(SlideKey.SLIDE_RIGHT)) {
            overridePendingTransition(R.anim.from_right_enter, R.anim.to_left_exit);
        } else if (slideKey.equals(SlideKey.SLIDE_LEFT)) {
            overridePendingTransition(R.anim.from_left_enter, R.anim.to_right_exit);
        }
        binding.btnHomeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
        binding.btnSearchCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CallActivity.this, SearchActivity.class);
                intent.putExtra(SlideKey.SLIDE_KEY, SlideKey.SLIDE_RIGHT);
                startActivity(intent);
                finish();
            }
        });
        binding.btnListCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CallActivity.this, ListActivity.class);
                intent.putExtra(SlideKey.SLIDE_KEY, SlideKey.SLIDE_RIGHT);
                startActivity(intent);
                finish();
            }
        });
    }
}