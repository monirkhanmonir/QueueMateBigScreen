package com.excellenceict.queuematebigscreen.util;

import android.content.Context;
import android.content.SharedPreferences;

public class QueuePreference {

    private SharedPreferences sharedPreferences;

    public QueuePreference(Context context){
        sharedPreferences  = context.getSharedPreferences(Constants.KEY_PREFERANCE_NAME, Context.MODE_PRIVATE);
    }


    public void putBoolean(String key, Boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public Boolean getBoolean(String key,boolean defValue){
        return sharedPreferences.getBoolean(key,defValue);
    }

    public void putString(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defValue){
        return sharedPreferences.getString(key,defValue);
    }

    public void putInt(String key, int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int defValue){
        return sharedPreferences.getInt(key,defValue);
    }

    public void clearPreferance(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }



//    public static void setSelectedScreenIndex(final Context context, final int data){
//        final SharedPreferences prefs = context.getSharedPreferences(
//                QueuePreference.SELECTED_SCREEN_INDEX, Context.MODE_PRIVATE);
//        final SharedPreferences.Editor editor = prefs.edit();
//        editor.putInt(QueuePreference.SELECTED_SCREEN_INDEX, data);
//        editor.commit();
//    }
//    public static int getSelectedIscreenIndex(final Context context){
//        return context.getSharedPreferences(QueuePreference.SELECTED_SCREEN_INDEX, context.MODE_PRIVATE)
//                .getInt(QueuePreference.SELECTED_SCREEN_INDEX,0);
//    }

//    public static void setSelectedScreenCode(final Context context, final String data){
//        final SharedPreferences prefs = context.getSharedPreferences(
//                QueuePreference.SELECTED_SCREEN_CODE, Context.MODE_PRIVATE);
//        final SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(QueuePreference.SELECTED_SCREEN_CODE, data);
//        editor.commit();
//    }

//    public static String getSelectedIscreenCode(final Context context){
//        return context.getSharedPreferences(QueuePreference.SELECTED_SCREEN_CODE, context.MODE_PRIVATE)
//                .getString(QueuePreference.SELECTED_SCREEN_CODE,"SCREEN 1");
//    }
//
//    public static void setSelectedScreenName(final Context context, final String data){
//        final SharedPreferences prefs = context.getSharedPreferences(
//                QueuePreference.SELECTED_SCREEN_NAME, Context.MODE_PRIVATE);
//        final SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(QueuePreference.SELECTED_SCREEN_NAME, data);
//        editor.commit();
//    }

//    public static String getSelectedScreenName(final Context context){
//        return context.getSharedPreferences(QueuePreference.SELECTED_SCREEN_NAME, context.MODE_PRIVATE)
//                .getString(QueuePreference.SELECTED_SCREEN_NAME,"Select an screen");
//    }
//
//    public static void setSelectedScreenId(final Context context, final String data){
//        final SharedPreferences prefs = context.getSharedPreferences(
//                QueuePreference.SELECTED_SCREEN_ID, Context.MODE_PRIVATE);
//        final SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(QueuePreference.SELECTED_SCREEN_ID, data);
//        editor.commit();
//    }
//
//    public static String getSelectedScreenId(final Context context){
//        return context.getSharedPreferences(QueuePreference.SELECTED_SCREEN_ID, context.MODE_PRIVATE)
//                .getString(QueuePreference.SELECTED_SCREEN_ID,"1");
//    }

}
