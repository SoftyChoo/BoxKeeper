package com.example.boxkeeper.ui.common;

import static com.example.boxkeeper.ui.common.Key.REQUEST_PHONE_CALL;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

public class Utils {
    private static String TAG = "Utils";
    public static void callPhoneNumber(Activity activity, String phoneNumber) {
        Log.d(TAG, "phone = " + phoneNumber);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            return;
        }
        activity.startActivity(callIntent);
    }
}
