package com.excellenceict.queuematebigscreen.service.backgroundService;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.excellenceict.queuematebigscreen.R;
import com.excellenceict.queuematebigscreen.view.ui.MainActivity;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class VoiceTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "VoiceTask";
    String attention_message = "স্যাম্পল প্রদান শেষে কাউন্টার ত্যাগ করুন। ধন্যবাদ ";
    private Context mcontext;
    private TextToSpeech textToSpeech;
    private String message;
    private boolean status = false;

    public VoiceTask(Context context, TextToSpeech speech, String message) {
        this.mcontext = context;
        this.textToSpeech = speech;
        this.message = message;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            SocketAddress sockaddr = new InetSocketAddress("www.google.com", 443);
            Socket sock = new Socket();
            int timeoutMs = 2000;   // 2 seconds
            sock.connect(sockaddr, timeoutMs);
            status = true;
        } catch (IOException e) {
            status = false;
            System.out.println("===========" + e);
            // Handle exception
        }
        if (status) {
            onlineVoiceTask(mcontext, message, attention_message);
        } else {
            offLineVoiceTask(textToSpeech, mcontext, message, attention_message);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    public void onlineVoiceTask(Context context, String message, String attention_message) {
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
        Python python = Python.getInstance();
        PyObject pyObject = python.getModule("script");
        String audioUri = pyObject.callAttr("genetareTTSFile", message, attention_message).toString();


        Log.d(TAG, "Audio Uri: " + audioUri);
        MediaPlayer mp = new MediaPlayer();
        try {

            MediaPlayer doorbell = null;
            if (doorbell == null) {
                doorbell = MediaPlayer.create(context, R.raw.doorbell);
            }
            doorbell.start();
            while (doorbell.isPlaying()) {

            }

            mp.setDataSource(audioUri);//Write your location here
            mp.prepare();
            mp.start();

            while (mp.isPlaying()) {

            }
            Log.d(TAG, "Online Play done...........");
            File file = new File(audioUri);
            if (file.exists()) {
                if (file.delete()) {
                    Log.d(TAG, "audio file Deleted :");
                } else {
                    Log.d(TAG, "audio file not Deleted :");
                }
            } else {
                Log.d(TAG, "audio file not Exist :");
            }
        } catch (Exception e) {
            Log.d(TAG, "Audio Play exception " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void offLineVoiceTask(TextToSpeech textToSpeech, Context context, String message, String additional_message) {
        String msg = message + " । " + additional_message;
        try {
            Log.d(TAG, "Offline Play data....... " + message);
            MediaPlayer doorbell = null;
            if (doorbell == null) {
                doorbell = MediaPlayer.create(context, R.raw.doorbell);
            }
            doorbell.start();
            while (doorbell.isPlaying()) {

            }

            textToSpeech.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
            Log.d(TAG, "Offline Play end.............. ");

        } catch (Exception e) {
            Log.d(TAG, "Offline Play exception " + e.getMessage());
            e.printStackTrace();
        }
    }
}
