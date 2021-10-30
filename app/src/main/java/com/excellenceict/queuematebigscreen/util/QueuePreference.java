package com.excellenceict.queuematebigscreen.util;

import android.content.Context;
import android.content.SharedPreferences;

public class QueuePreference {
    private static final String SELECTED_SCREEN_INDEX = "screen_selected_index";
    private static final String SELECTED_SCREEN_ID = "screen_selected_id";
    private static final String SELECTED_SCREEN_CODE = "screen_selected_code";
    private static final String SELECTED_SCREEN_NAME = "screen_selected_name";

    public static void setSelectedScreenIndex(final Context context, final int data){
        final SharedPreferences prefs = context.getSharedPreferences(
                QueuePreference.SELECTED_SCREEN_INDEX, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(QueuePreference.SELECTED_SCREEN_INDEX, data);
        editor.commit();
    }
    public static int getSelectedIscreenIndex(final Context context){
        return context.getSharedPreferences(QueuePreference.SELECTED_SCREEN_INDEX, context.MODE_PRIVATE)
                .getInt(QueuePreference.SELECTED_SCREEN_INDEX,0);
    }

    public static void setSelectedScreenCode(final Context context, final String data){
        final SharedPreferences prefs = context.getSharedPreferences(
                QueuePreference.SELECTED_SCREEN_CODE, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(QueuePreference.SELECTED_SCREEN_CODE, data);
        editor.commit();
    }

    public static String getSelectedIscreenCode(final Context context){
        return context.getSharedPreferences(QueuePreference.SELECTED_SCREEN_CODE, context.MODE_PRIVATE)
                .getString(QueuePreference.SELECTED_SCREEN_CODE,"SCREEN 1");
    }

    public static void setSelectedScreenName(final Context context, final String data){
        final SharedPreferences prefs = context.getSharedPreferences(
                QueuePreference.SELECTED_SCREEN_NAME, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(QueuePreference.SELECTED_SCREEN_NAME, data);
        editor.commit();
    }

    public static String getSelectedScreenName(final Context context){
        return context.getSharedPreferences(QueuePreference.SELECTED_SCREEN_NAME, context.MODE_PRIVATE)
                .getString(QueuePreference.SELECTED_SCREEN_NAME,"Select an screen");
    }

    public static void setSelectedScreenId(final Context context, final String data){
        final SharedPreferences prefs = context.getSharedPreferences(
                QueuePreference.SELECTED_SCREEN_ID, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(QueuePreference.SELECTED_SCREEN_ID, data);
        editor.commit();
    }

    public static String getSelectedScreenId(final Context context){
        return context.getSharedPreferences(QueuePreference.SELECTED_SCREEN_ID, context.MODE_PRIVATE)
                .getString(QueuePreference.SELECTED_SCREEN_ID,"1");
    }

}
