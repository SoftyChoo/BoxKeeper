package com.example.boxkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.boxkeeper.call.CallActivity;
import com.example.boxkeeper.databinding.ActivityListBinding;
import com.example.boxkeeper.search.SearchActivity;

public class ListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityListBinding binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String slideKey = getIntent().getStringExtra(SlideKey.SLIDE_KEY);

        if (slideKey.equals(SlideKey.SLIDE_RIGHT)) {
            overridePendingTransition(R.anim.from_right_enter, R.anim.to_left_exit);
        } else if (slideKey.equals(SlideKey.SLIDE_LEFT)) {
            overridePendingTransition(R.anim.from_left_enter, R.anim.to_right_exit);
        }

        initTab(binding);
    }
    public void onBackPressed() {
        super.onBackPressed();

        if (isFinishing()) {
            // back 버튼으로 화면 종료가 야기되면 동작한다.
            overridePendingTransition(R.anim.from_left_enter, R.anim.to_right_exit);
        }
    }
    private void initTab(ActivityListBinding binding) {
        binding.btnHomeList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onBackPressed();
            }
        });
        binding.btnCallList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ListActivity.this, CallActivity.class);
                intent.putExtra(SlideKey.SLIDE_KEY, SlideKey.SLIDE_LEFT);
                startActivity(intent);
                finish();
            }
        });
        binding.btnSearchList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ListActivity.this, SearchActivity.class);
                intent.putExtra(SlideKey.SLIDE_KEY, SlideKey.SLIDE_LEFT);
                startActivity(intent);
                finish();
            }
        });
        binding.btnList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //
            }
        });
    }
}