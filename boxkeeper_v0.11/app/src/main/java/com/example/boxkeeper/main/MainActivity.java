package com.example.boxkeeper.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.boxkeeper.BoxLog;
import com.example.boxkeeper.CameraActivity;
import com.example.boxkeeper.InfoDialog;
import com.example.boxkeeper.ListActivity;
import com.example.boxkeeper.R;
import com.example.boxkeeper.databinding.ActivityMainBinding;
import com.example.boxkeeper.databinding.ActivitySearchBinding;
import com.example.boxkeeper.search.SearchActivity;
import com.example.boxkeeper.SlideKey;
import com.example.boxkeeper.UserCommand;
import com.example.boxkeeper.call.CallActivity;
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

    private CardView boxStateCamera1;

    private ImageButton homeButton;
    private ImageButton callButton;
    private ImageButton searchButton;
    private ImageButton listButton;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference state_of_the_box = mRootRef.child("State_of_the_Box");
    DatabaseReference Weight = mRootRef.child("Weight");
    DatabaseReference Abs = mRootRef.child("Abs");

    private static final String FIREBASE_DATABASE_URL = "https://boxkeeper-f0938-default-rtdb.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sttext = (TextView) findViewById(R.id.tv_box1_real_weight);
        infobtn = (ImageButton) findViewById(R.id.btn_info);
        Weights = (TextView) findViewById(R.id.tv_box1_plus_minus);
        Ab = (TextView) findViewById(R.id.tv_box1_abs);
        sirenBox1 = (ImageButton) findViewById(R.id.btn_siren1);
        sirenBox2 = (ImageButton) findViewById(R.id.btn_siren2);
        sirenBox3 = (ImageButton) findViewById(R.id.btn_siren3);
        sirenBox4 = (ImageButton) findViewById(R.id.btn_siren4);

        homeButton = (ImageButton) findViewById(R.id.btn_home_main);
        callButton = (ImageButton) findViewById(R.id.btn_call_main);
        searchButton = (ImageButton) findViewById(R.id.btn_search_main);
        listButton = (ImageButton) findViewById(R.id.btn_list_main);

        boxStateCamera1 = (CardView) findViewById(R.id.cv_box1_img);

        boxStateCamera1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CameraActivity.class);
                startActivity(intent);
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

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //동작없음
            }
        });
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CallActivity.class);
                intent.putExtra(SlideKey.SLIDE_KEY, SlideKey.SLIDE_RIGHT);
                startActivity(intent);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra(SlideKey.SLIDE_KEY, SlideKey.SLIDE_RIGHT);
                startActivity(intent);
            }
        });
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra(SlideKey.SLIDE_KEY, SlideKey.SLIDE_RIGHT);
                startActivity(intent);
            }
        });
    }

    private void initSiren() {
        sirenBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sirenDialog(1);
            }
        });
        sirenBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sirenDialog(2);
            }
        });
        sirenBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sirenDialog(3);
            }
        });
        sirenBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sirenDialog(4);
            }
        });
    }

    public static void updateBuzzerValue(int newValue) {
        // Firebase 데이터베이스에 연결
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference buzzerRef = database.getReference("Buzzer");

        // "Buzzer" 테이블의 값을 변경
        buzzerRef.setValue(newValue);
    }

    void sirenDialog(Integer num) {
        AlertDialog.Builder menu = new AlertDialog.Builder(MainActivity.this);
        menu.setIcon(R.mipmap.ic_launcher);
        menu.setTitle("BOXKEEPER"); // 제목
        menu.setMessage(num + "번째 상자 사이렌의 상태를 변경하시겠습니까?"); // 문구
        menu.setIcon(R.drawable.box);

        // 확인 버튼
        menu.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (num) {
                    case 1: {
                        if(sirenBox1.isSelected()) updateBuzzerValue(0); else updateBuzzerValue(1);
                        sirenBox1.setSelected(!sirenBox1.isSelected());
                        break;
                    }
                    case 2:
                        sirenBox2.setSelected(!sirenBox2.isSelected());
                        break;
                    case 3:
                        sirenBox3.setSelected(!sirenBox3.isSelected());
                        break;
                    case 4:
                        sirenBox4.setSelected(!sirenBox4.isSelected());
                        break;
                }
                dialog.dismiss();
            }
        });

        // 취소 버튼
        menu.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // dialog 제거
                dialog.dismiss();
            }
        });
        menu.show();
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