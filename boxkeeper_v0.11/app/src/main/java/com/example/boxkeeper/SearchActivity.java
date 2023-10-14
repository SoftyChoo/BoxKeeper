package com.example.boxkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.boxkeeper.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initTab(binding);
    }
    private void initTab(ActivitySearchBinding binding) {
        binding.btnHomeSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        binding.btnCallSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(SearchActivity.this, CallActivity.class);
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
                startActivity(intent);
                finish();
            }
        });
    }
}