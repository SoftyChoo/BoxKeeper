package com.example.boxkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class BoxLog extends AppCompatActivity {

    private ImageButton infobtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boxlog);

        infobtn = (ImageButton) findViewById(R.id.btn_info);

    }
    @Override
    protected void onStart() {
        super.onStart();

        infobtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 팝업 액티비티를 시작하는 코드
                Intent intent = new Intent(BoxLog.this, InfoDialog.class);
                startActivity(intent);
            }
        });
    }
}
