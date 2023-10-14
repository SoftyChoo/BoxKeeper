package com.example.boxkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import android.view.View;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView sttext;
    private ImageButton infobtn;
    private TextView Weights;
    private TextView Ab;
    private String changeState;
    private ImageButton sirenBox1;
    private ImageButton sirenBox2;
    private ImageButton sirenBox3;
    private ImageButton sirenBox4;

    private ImageButton homeButton;
    private ImageButton callButton;
    private ImageButton searchButton;
    private ImageButton listButton;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference state_of_the_box = mRootRef.child("State_of_the_Box");
    DatabaseReference Weight = mRootRef.child("Weight");
    DatabaseReference Abs = mRootRef.child("Abs");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sttext = (TextView) findViewById(R.id.tv_box1_real_weight);
        infobtn = (ImageButton) findViewById(R.id.btn_info);
        Weights = (TextView) findViewById(R.id.tv_box1_plus_minus);
        Ab = (TextView) findViewById(R.id.tv_box1_abs);
        sirenBox1 = (ImageButton) findViewById(R.id.btn_siren1);
        sirenBox2 = (ImageButton) findViewById(R.id.btn_siren2);
        sirenBox3 = (ImageButton) findViewById(R.id.btn_siren3);
        sirenBox4 = (ImageButton) findViewById(R.id.btn_siren4);

        homeButton = (ImageButton) findViewById(R.id.btn_home_main);
        callButton= (ImageButton) findViewById(R.id.btn_call_main);
        searchButton = (ImageButton) findViewById(R.id.btn_search_main);
        listButton = (ImageButton) findViewById(R.id.btn_list_main);

        findViewById(R.id.btn_menu).setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                final PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.action_menu1) {
                            Intent intent = new Intent(MainActivity.this, UserCommand.class);
                            startActivity(intent);
                        } else if (menuItem.getItemId() == R.id.action_menu2) {
                            Intent intent = new Intent(MainActivity.this, Delivery.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(MainActivity.this, BoxLog.class);
                            startActivity(intent);
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }

        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Channel Name";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("CHANNEL_ID", channelName, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initSiren();
        initTab();

        state_of_the_box.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String state = dataSnapshot.getValue(String.class);
                String state = String.valueOf(dataSnapshot.getValue());
                Log.e("osslog", dataSnapshot.getValue().toString());


                //Minsu's code : 만약 state값이 -라면 사용자가 볼 수 있게 0으로 값 초기화
                //-> 값을 측정할 때 값이 튈 때 -값이 나오는 문제를 해결하기 위한 코드
                if (state.contains("-")) {
                    sttext.setText("0.0");
                } else {
                    sttext.setText(state);
                }
                changeState = state;
                showNotification();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        infobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 팝업 액티비티를 시작하는 코드
                Intent intent = new Intent(MainActivity.this, InfoDialog.class);
                startActivity(intent);
            }
        });

        Weight.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String state = dataSnapshot.getValue(String.class);
                String state = String.valueOf(dataSnapshot.getValue());
                Log.e("osplog", dataSnapshot.getValue().toString());
                Weights.setText(state);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Abs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String state = dataSnapshot.getValue(String.class);
                String state = String.valueOf(dataSnapshot.getValue());
                Log.e("osblog", dataSnapshot.getValue().toString());
                Ab.setText(state);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void initTab() {
        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               //동작없음
            }
        });
        callButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, CallActivity.class);
                startActivity(intent);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        listButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initSiren() {
        sirenBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sirenBox1.isSelected()){
                    sirenBox1.setSelected(false);
                }
                else{
                    sirenBox1.setSelected(true);
                }
                Toast.makeText(MainActivity.this, "siren", Toast.LENGTH_SHORT).show();
            }
        });
        sirenBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sirenBox2.isSelected()){
                    sirenBox2.setSelected(false);
                }
                else{
                    sirenBox2.setSelected(true);
                }
                Toast.makeText(MainActivity.this, "siren", Toast.LENGTH_SHORT).show();
            }
        });
        sirenBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sirenBox3.isSelected()){
                    sirenBox3.setSelected(false);
                }
                else{
                    sirenBox3.setSelected(true);
                }
                Toast.makeText(MainActivity.this, "siren", Toast.LENGTH_SHORT).show();
            }
        });
        sirenBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sirenBox4.isSelected()){
                    sirenBox4.setSelected(false);
                }
                else{
                    sirenBox4.setSelected(true);
                }
                Toast.makeText(MainActivity.this, "siren", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("BOXKEEPER값이 변경됨")
                .setContentText("BOXKEEPER값이 " + changeState + "로 변경되었습니다.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // 알림 표시
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

}