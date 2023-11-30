package com.example.boxkeeper.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageButton;

import com.example.boxkeeper.R;
import com.example.boxkeeper.databinding.ActivityMainBinding;
import com.example.boxkeeper.ui.search.SearchActivity;
import com.example.boxkeeper.ui.common.SlideKey;
import com.example.boxkeeper.ui.call.CallActivity;
import com.example.boxkeeper.ui.list.ImageListActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private String changeState;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference state_of_the_box = mRootRef.child("State_of_the_Box");
    DatabaseReference Weight = mRootRef.child("Weight");
    DatabaseReference Abs = mRootRef.child("Abs");

    private final Handler handler = new Handler();

    private static final String FIREBASE_DATABASE_URL = "https://boxkeeper-f0938-default-rtdb.firebaseio.com/";

    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.cvBox1Img.setOnClickListener(new View.OnClickListener() {
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

        initView(binding);
        initSiren(binding);
        initTab(binding);
    }

    private void initView(ActivityMainBinding binding) {
        state_of_the_box.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String state = dataSnapshot.getValue(String.class);
                String state = String.valueOf(dataSnapshot.getValue());
                Log.e("osslog", dataSnapshot.getValue().toString());


                //Minsu's code : 만약 state값이 -라면 사용자가 볼 수 있게 0으로 값 초기화
                //-> 값을 측정할 때 값이 튈 때 -값이 나오는 문제를 해결하기 위한 코드
                if (state.contains("-")) {
                    binding.tvBox1RealWeight.setText("0.0");
                } else {
                    binding.tvBox1RealWeight.setText(state);
                }
                changeState = state;
                showNotification();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        binding.btnInfo.setOnClickListener(new View.OnClickListener() {
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
                binding.tvBox1PlusMinus.setText(state);
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
                binding.tvBox1Abs.setText(state);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    private void initTab(ActivityMainBinding binding) {

        binding.btnHomeMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //동작없음
            }
        });
        binding.btnCallMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CallActivity.class);
                intent.putExtra(SlideKey.SLIDE_KEY, SlideKey.SLIDE_RIGHT);
                startActivity(intent);
            }
        });
        binding.btnSearchMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra(SlideKey.SLIDE_KEY, SlideKey.SLIDE_RIGHT);
                startActivity(intent);
            }
        });
        binding.btnListMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImageListActivity.class);
                intent.putExtra(SlideKey.SLIDE_KEY, SlideKey.SLIDE_RIGHT);
                startActivity(intent);
            }
        });
    }

    private void initSiren(ActivityMainBinding binding) {
        binding.btnSiren1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sirenDialog(1,binding);
            }
        });
        binding.btnSiren2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sirenDialog(2,binding);
            }
        });
        binding.btnSiren3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sirenDialog(3,binding);
            }
        });
        binding.btnSiren4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sirenDialog(4,binding);
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

    void sirenDialog(Integer num,ActivityMainBinding binding) {
        AlertDialog.Builder menu = new AlertDialog.Builder(MainActivity.this);
        menu.setIcon(R.mipmap.ic_launcher);
        menu.setTitle("BOXKEEPER"); // 제목
        menu.setMessage(num + "번째 상자 사이렌을 울리시겠습니까?"); // 문구
        menu.setIcon(R.drawable.box);

        // 확인 버튼
        menu.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (num) {
                    case 1: {
                        if (binding.btnSiren1.isSelected()) updateBuzzerValue(0);
                        else updateBuzzerValue(1);
                        binding.btnSiren1.setSelected(!binding.btnSiren1.isSelected());

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (binding.btnSiren1.isSelected()) updateBuzzerValue(0);
                                else updateBuzzerValue(1);
                                binding.btnSiren1.setSelected(!binding.btnSiren1.isSelected());
                            }
                        }, 12000);
                        break;
                    }
                    case 2:
                        binding.btnSiren2.setSelected(!binding.btnSiren2.isSelected());
                        break;
                    case 3:
                        binding.btnSiren3.setSelected(!binding.btnSiren3.isSelected());
                        break;
                    case 4:
                        binding.btnSiren4.setSelected(!binding.btnSiren4.isSelected());
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


    // 알림 권한이 있는지 확인
    private boolean hasNotificationPermission() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        return notificationManager.areNotificationsEnabled();
    }

    // 알림 권한 요청
    private void requestNotificationPermission() {
        Intent intent = new Intent();
        intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");

        // Android 5-7용
        intent.putExtra("app_package", getPackageName());
        intent.putExtra("app_uid", getApplicationInfo().uid);

        // Android 8 이상용
        intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());

        startActivity(intent);
    }

    // 권한 확인 및 필요 시 권한 요청 후 알림 표시
    private void showNotificationWithPermissionCheck() {
        if (hasNotificationPermission()) {
            showNotification();
        } else {
            requestNotificationPermission();
        }
    }



}