package com.example.boxkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.boxkeeper.databinding.ActivityListBinding;

public class ListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityListBinding binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        initTab(binding);
    }
    private void initTab(ActivityListBinding binding) {
        binding.btnHomeList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        binding.btnCallList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ListActivity.this, CallActivity.class);
                startActivity(intent);
                finish();
            }
        });
        binding.btnSearchList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ListActivity.this, SearchActivity.class);
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