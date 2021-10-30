package com.excellenceict.queuematebigscreen.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.excellenceict.queuematebigscreen.R;
import com.excellenceict.queuematebigscreen.service.backgroundService.VoiceTask;
import com.excellenceict.queuematebigscreen.service.model.QueueModel;
import com.excellenceict.queuematebigscreen.service.model.ScreenInfo;
import com.excellenceict.queuematebigscreen.util.QueuePreference;
import com.excellenceict.queuematebigscreen.view.adapter.QueueAdapter;
import com.excellenceict.queuematebigscreen.viewModel.QueueInfoViewMoodel;
import com.excellenceict.queuematebigscreen.viewModel.ScreenInfoViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView jQueueRecyclerView;
    private TextView jSelectedScreenNameId, jMarqueeTextId;
     TextToSpeech textToSpeech;
    private QueueInfoViewMoodel queueViewMoodel;
    private ScreenInfoViewModel screenInfoViewModel;
    private ArrayList<ScreenInfo> screenList;
    private QueueAdapter adapter;
    private Context context;
    private String voiceData, selectedScreenId, selectedScreen;
    private int selectedItemIndex;
    String attention_message = "স্যাম্পল প্রদান শেষে কাউন্টার ত্যাগ করুন। ধন্যবাদ ";
    String attention_message2 = " করোনা প্রতিরোধে মাক্স পরিধান করুন। নিয়মিত সাবান দিয়ে হাত পরিষ্কার করুন। " +
            "নিরাপদ সামাজিক দূরত্ব বজায় রাখুন। । ধন্যবাদ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            textToSpeech = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if (i != TextToSpeech.ERROR) {
                    // To Choose language of speech
                    textToSpeech.setLanguage(new Locale("bn-bd"));
                    textToSpeech.setSpeechRate(0.5f);
                    Log.d(TAG, " ofline data- bind............"+1);
                } else {

                    Log.d(TAG, " ofline data- bind fail............"+TextToSpeech.ERROR);
                }
            }
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        jQueueRecyclerView = findViewById(R.id.queueRecyclerView);
        jSelectedScreenNameId = findViewById(R.id.selectedScreenNameId);
        jMarqueeTextId = findViewById(R.id.marqueeTextId);
        jMarqueeTextId.setText(attention_message + attention_message2);
        jMarqueeTextId.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        jMarqueeTextId.setSelected(true);


        context = MainActivity.this;
        screenList = new ArrayList<>();
        jSelectedScreenNameId.setText(QueuePreference.getSelectedScreenName(context));

        //jQueueRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));


        queueViewMoodel = new ViewModelProvider(this).get(QueueInfoViewMoodel.class);
        queueViewMoodel.getAllQueueInfo().observe(this, new Observer<List<QueueModel>>() {
            @Override
            public void onChanged(List<QueueModel> queueModels) {
                if (queueModels.size() != 0) {
                    jQueueRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                    adapter = new QueueAdapter(queueModels);
                    Log.d("TAG", "Change live data..................." + queueModels.size());
                    jQueueRecyclerView.setAdapter(adapter);
                }

                if (queueModels.size() != 0) {
                    voiceData = queueViewMoodel.getVoiceData(queueModels);
                    Log.d(TAG, "Voice Data-............ "+voiceData);
                    if(voiceData!=null || !voiceData.isEmpty()){
                        new VoiceTask(context,textToSpeech,voiceData.toLowerCase()).execute();
                    }
                }

            }
        });
        screenInfoViewModel = new ViewModelProvider(this).get(ScreenInfoViewModel.class);
        screenInfoViewModel.getAllScreenInfo().observe(this, new Observer<List<ScreenInfo>>() {
            @Override
            public void onChanged(List<ScreenInfo> screenInfos) {
                screenList = (ArrayList<ScreenInfo>) screenInfos;

            }
        });


    }


    public void selectScreen(View view) {

        selectedItemIndex = QueuePreference.getSelectedIscreenIndex(context);
        selectedScreenId = QueuePreference.getSelectedScreenId(context);
        selectedScreen = QueuePreference.getSelectedScreenName(context);

        ArrayAdapter<ScreenInfo> adapter = new ArrayAdapter<ScreenInfo>(MainActivity.this, android.R.layout.select_dialog_singlechoice, screenList);
        MaterialAlertDialogBuilder builder =
                new MaterialAlertDialogBuilder(MainActivity.this)
                        .setTitle("Select Screen")
                        .setSingleChoiceItems(adapter, selectedItemIndex, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedItemIndex = which;
                                selectedScreen = screenList.get(selectedItemIndex).getScreenName();
                                selectedScreenId = screenList.get(selectedItemIndex).getScreenId();
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                QueuePreference.setSelectedScreenIndex(context, selectedItemIndex);
                                QueuePreference.setSelectedScreenName(context, selectedScreen);
                                QueuePreference.setSelectedScreenId(context, selectedScreenId);
                                jSelectedScreenNameId.setText(QueuePreference.getSelectedScreenName(context));
                                Toast.makeText(context, selectedScreen, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "Canceled..", Toast.LENGTH_SHORT).show();
                            }
                        });
        builder.show();
    }

    private class VoiceCallTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (!Python.isStarted()) {
                Python.start(new AndroidPlatform(MainActivity.this));
            }
            Python python = Python.getInstance();
            PyObject pyObject = python.getModule("script");
            String audioUri = pyObject.callAttr("genetareTTSFile", voiceData, attention_message).toString();


            Log.d("TAG", "Audio Uri: " + audioUri);
            MediaPlayer mp = new MediaPlayer();
            try {

                MediaPlayer doorbell = null;
                if (doorbell == null) {
                    doorbell = MediaPlayer.create(MainActivity.this, R.raw.doorbell);
                }
                doorbell.start();
                while (doorbell.isPlaying()) {

                }

                mp.setDataSource(audioUri);//Write your location here
                mp.prepare();
                mp.start();

                while (mp.isPlaying()) {

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

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }


}