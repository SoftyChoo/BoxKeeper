package com.example.boxkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.boxkeeper.databinding.ActivityCallBinding;

public class CallActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCallBinding binding = ActivityCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initTab(binding);
    }
    private void initTab(ActivityCallBinding binding) {
        binding.btnHomeCall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        binding.btnCall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //
            }
        });
        binding.btnSearchCall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(CallActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });
        binding.btnListCall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(CallActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}