package com.example.boxkeeper.ui.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

//    private static final String INPUT_DATE_FORMAT = "MMddyy";
    private static final String INPUT_DATE_FORMAT = "yyyymmdd";
    private static final String INPUT_TIME_FORMAT = "HHmmss";
    private static final String OUTPUT_DATE_FORMAT = "yyyy년 mm월 dd일";
    private static final String OUTPUT_TIME_FORMAT = "HH시 mm분 ss초";

    public static String formatDate(String inputDate) {
        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat(INPUT_DATE_FORMAT, Locale.getDefault());
            Date parsedDate = inputDateFormat.parse(inputDate);

            SimpleDateFormat outputDateFormat = new SimpleDateFormat(OUTPUT_DATE_FORMAT, Locale.getDefault());
            return outputDateFormat.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the exception according to your application's requirements
            return "Error formatting date";
        }
    }

    public static String formatTime(String inputTime) {
        try {
            SimpleDateFormat inputTimeFormat = new SimpleDateFormat(INPUT_TIME_FORMAT, Locale.getDefault());
            Date parsedTime = inputTimeFormat.parse(inputTime);

            SimpleDateFormat outputTimeFormat = new SimpleDateFormat(OUTPUT_TIME_FORMAT, Locale.getDefault());
            return outputTimeFormat.format(parsedTime);
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the exception according to your application's requirements
            return "Error formatting time";
        }
    }
}
