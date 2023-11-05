package com.example.boxkeeper.search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.boxkeeper.ListActivity;
import com.example.boxkeeper.R;
import com.example.boxkeeper.SlideKey;
import com.example.boxkeeper.call.CallActivity;
import com.example.boxkeeper.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding binding = ActivitySearchBinding.inflate(getLayoutInflater());
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