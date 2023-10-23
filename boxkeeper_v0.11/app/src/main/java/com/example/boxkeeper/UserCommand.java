package com.example.boxkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserCommand extends AppCompatActivity {

    private TextView cmdtext;
    private EditText editText;
    private Button button;
    private ImageButton infobtn;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = mRootRef.child("usercommand");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercommand);

        cmdtext = (TextView) findViewById(R.id.commandtext);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        infobtn = (ImageButton) findViewById(R.id.btn_info);


    }
    @Override
    protected void onStart() {
        super.onStart();

        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                cmdtext.setText(text);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conditionRef.setValue(editText.getText().toString());
            }
        });

        infobtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // 팝업 액티비티를 시작하는 코드
                Intent intent = new Intent(UserCommand.this, InfoDialog.class);
                startActivity(intent);
            }
        });

    }


}
