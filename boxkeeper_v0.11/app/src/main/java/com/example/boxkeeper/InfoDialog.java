package com.example.boxkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class InfoDialog extends AppCompatActivity {
    private Button infoexit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infodialog);


        infoexit = (Button) findViewById(R.id.info_exit);
    }
    @Override
    protected void onStart() {
        super.onStart();

        infoexit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
