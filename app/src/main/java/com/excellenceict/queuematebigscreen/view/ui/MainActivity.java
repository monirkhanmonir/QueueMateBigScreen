package com.excellenceict.queuematebigscreen.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.excellenceict.queuematebigscreen.R;
import com.excellenceict.queuematebigscreen.service.backgroundService.QueueService;
import com.excellenceict.queuematebigscreen.service.model.QueueModel;
import com.excellenceict.queuematebigscreen.service.repository.QueueRepository;
import com.excellenceict.queuematebigscreen.view.adapter.QueueAdapter;
import com.excellenceict.queuematebigscreen.viewModel.QueueInfoViewMoodel;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView jQueueRecyclerView;
    private QueueRepository queueRepository;
    private QueueInfoViewMoodel viewMoodel;
    private QueueAdapter adapter;
    private Context context;
    String attention_message = "স্যাম্পল প্রদান শেষে কাউন্টার ত্যাগ করুন। ধন্যবাদ";

   String  queueData ="Serial nombor1001, ROOM-3. Serial nombor1001, ROOM-4. Serial nombor1001, ROOM-3. Serial nombor1002,ROOM-2."+
            " Serial nombor1002, ROOM-1. Serial nombor1003, ROOM-2. Serial nombor1003, ROOM-1. ।";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jQueueRecyclerView = findViewById(R.id.queueRecyclerView);
        context = MainActivity.this;


        jQueueRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));




        viewMoodel = new ViewModelProvider(this).get(QueueInfoViewMoodel.class);
        viewMoodel.getAllQueueInfo().observe(this, new Observer<List<QueueModel>>() {
            @Override
            public void onChanged(List<QueueModel> queueModels) {
                if(queueModels.size()!=0){
                    jQueueRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                    adapter = new QueueAdapter(queueModels);
                    Log.d("TAG","Change live data...................");
                    jQueueRecyclerView.setAdapter(adapter);
                    }

                if(queueModels.size()!=0){
                    String voiceData = viewMoodel.getVoiceData(queueModels);
                    Intent intent =new Intent(MainActivity.this,QueueService.class);
                    intent.putExtra("voice_data",voiceData);
                    startService(intent);
                }

            }
        });

//        if (!Python.isStarted()) {
//            Python.start(new AndroidPlatform(MainActivity.this));
//        }
//        Python python = Python.getInstance();
//        PyObject pyObject = python.getModule("script");
//        String audioUri = pyObject.callAttr("genetareTTSFile",queueData,attention_message).toString();
//
//
//        Log.d("TAG", "Audio Uri: " + audioUri);
//        MediaPlayer mp = new MediaPlayer();
//        try {
//            mp.setDataSource(audioUri);//Write your location here
//            mp.prepare();
//            mp.start();
//            Log.d("TAG", "Play done...........");
//            File file = new File(audioUri);
//            if (file.exists()) {
//                if (file.delete()) {
//                    System.out.println("file Deleted :");
//                } else {
//                    System.out.println("file not Deleted :");
//                }
//            } else {
//                System.out.println("file not Exist :");
//            }
//        } catch (Exception e) {
//            Uri uri;
//
//            Log.d("TAG", "Play exception " + e.getMessage());
//            e.printStackTrace();
//        }


    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        stopService(new Intent(this, QueueService.class));
    }

    public void chengeLiveData(View view) {
        QueueRepository.getQueueRepositoryInstance(this).createQueueFromDb();
    }
}