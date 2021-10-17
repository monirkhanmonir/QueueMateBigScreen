package com.excellenceict.queuematebigscreen.service.backgroundService;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.excellenceict.queuematebigscreen.service.repository.QueueRepository;

import java.io.File;

public class QueueService extends Service{
    String attention_message = " | স্যাম্পল প্রদান শেষে কাউন্টার ত্যাগ করুন। ধন্যবাদ";
    public QueueService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG", "Service created...........");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
       String voiceData = intent.getStringExtra("voice_data");
        Log.d("TAG", "Voice Data: " + voiceData);

       if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
        Python python = Python.getInstance();
        PyObject pyObject = python.getModule("script");
        String audioUri = pyObject.callAttr("genetareTTSFile",voiceData,attention_message).toString();


        Log.d("TAG", "Audio Uri: " + audioUri);
        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(audioUri);//Write your location here
            mp.prepare();
            mp.start();

            while (mp.isPlaying()){

            }

            Log.d("TAG", "Play done...........");
            File file = new File(audioUri);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("file Deleted :");
                } else {
                    System.out.println("file not Deleted :");
                }
            } else {
                System.out.println("file not Exist :");
            }
        } catch (Exception e) {
            Log.d("TAG", "Play exception " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("TAG","Service destroy.......");
    }
}