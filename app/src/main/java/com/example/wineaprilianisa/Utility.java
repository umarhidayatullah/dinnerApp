package com.example.wineaprilianisa;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utility {

    public static void showMyToast(String toastText, Context appContext) {
        Toast.makeText(appContext, toastText, Toast.LENGTH_SHORT).show();
    }

    public static String[] combine(String[] a, String[] b) {
        int length = a.length + b.length;
        String[] result = new String[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public static String[] combine(String[] a, String[] b, String[] c, String[] d) {
        return combine(combine(a, b), combine(c, d ));
    }

    public static String getDinnerId(String dinner) {
        return dinner.substring(0,2);
    }

    public static String getCurrentTime() {
        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-M hh:mm:ss");
        return format.format(curDate);
    }

    public static String getUniqueTransactionId(String productId) {
        return ("T-" + getCurrentTime() + productId);
    }
}
