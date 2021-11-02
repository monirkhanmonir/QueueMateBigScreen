package com.excellenceict.queuematebigscreen.viewModel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.excellenceict.queuematebigscreen.service.model.QueueModel;
import com.excellenceict.queuematebigscreen.service.network.DBConnector;
import com.excellenceict.queuematebigscreen.service.repository.QueueRepository;
import com.excellenceict.queuematebigscreen.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class QueueInfoViewMoodel extends AndroidViewModel implements Runnable{
    private QueueRepository queueRepository;
    private MutableLiveData<List<QueueModel>> liveData;
    private List<QueueModel> queueInfoList;
//    private boolean isExit = false;
    private Context context;

    public QueueInfoViewMoodel(@NonNull Application application) {
        super(application);
        context = application;
        new DBConnector(application);
        queueRepository = QueueRepository.getQueueRepositoryInstance(application);
        queueInfoList = new ArrayList<>();
        Constants.IS_EXIT =true;
        new Thread(this).start();

    }

    public MutableLiveData<List<QueueModel>> getAllQueueInfo(){
        if(liveData==null){
            liveData = new MutableLiveData<>();
            queueInfoList =  queueRepository.createQueueFromDb(context);
            Log.d("TAG","SIZE========="+queueInfoList.size());
            liveData.postValue(queueInfoList);
        }
        return liveData;
    }

    public void getQueueFromRepo(){
        queueInfoList =  queueRepository.createQueueFromDb(context);
        liveData.postValue(queueInfoList);

    }

    public String getVoiceData(List<QueueModel> queueList){
        String voiceData = "";
        for(QueueModel queueModel :queueList){
            if(queueModel.getNextQueueId()=="" || queueModel.getNextPersionName()==null){
                System.out.println("========1===="+queueModel.getNextQueueId());
            }else {
                System.out.println("=======2====="+queueModel.getNextQueueId());
                voiceData += queueModel.getCalloutText()+"। ";
                queueRepository.updateQueueToDb(context,queueModel.getPk_id());
            }
        }
        return voiceData;
    }


    @Override
    public void run() {
        System.out.println("New thread Start.....============2");
        while (Constants.IS_EXIT){
            try {

                Thread.sleep(100*1000);

            }catch (Exception e){

            }
            getQueueFromRepo();
            System.out.println("do something===========");

        }
        System.out.println("============Stop Thread==================");
    }
}
