package com.fahrimz.friendconnect;

import android.util.Log;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

final public class Utils {
    public static String formatDateFromDatabaseString(String time) {
        String result = null;

        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);

            Date date = inputFormat.parse(time);
            result = outputFormat.format(date);
        } catch (Exception e) {
            Log.d("debug: timestamp", e.getLocalizedMessage());
            Log.d("debug: timestamp", time);
        }

        return result != null ? result : "-";
    }
}
