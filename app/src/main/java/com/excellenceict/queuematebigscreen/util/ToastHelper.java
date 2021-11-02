package com.excellenceict.queuematebigscreen.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.excellenceict.queuematebigscreen.R;
import com.google.android.material.snackbar.Snackbar;


public class ToastHelper {

    private ToastHelper() {
    }

    public static void tostView(Context context, int layoutID) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(layoutID, null);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    public static void tostViewOther(Context context, int layoutID, String tex) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(layoutID, null);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    public static void showToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showSnackBarToast(View view, String text, int textColor, int bgColor) {
        Snackbar snackbar = Snackbar.make(view,text, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        textView.setTextSize(20);
        textView.setTextColor(textColor);
        snackbar.setBackgroundTint(bgColor);
        snackbar.show();
    }
}
