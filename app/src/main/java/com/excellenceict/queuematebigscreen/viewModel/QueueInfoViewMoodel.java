package com.excellenceict.queuematebigscreen.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.excellenceict.queuematebigscreen.service.model.QueueModel;
import com.excellenceict.queuematebigscreen.service.repository.QueueRepository;

import java.util.ArrayList;
import java.util.List;

public class QueueInfoViewMoodel extends AndroidViewModel implements Runnable{
    private QueueRepository queueRepository;
    private MutableLiveData<List<QueueModel>> liveData;
    private List<QueueModel> queueInfoList;

    public QueueInfoViewMoodel(@NonNull Application application) {
        super(application);
        queueRepository = QueueRepository.getQueueRepositoryInstance(application);
        queueInfoList = new ArrayList<>();
        liveData = new MutableLiveData<>();
        new Thread(this).start();

    }

    public MutableLiveData<List<QueueModel>> getAllQueueInfo(){
        if(liveData==null){
            queueInfoList =  queueRepository.createQueueFromDb();
            Log.d("TAG","SIZE========="+queueInfoList.size());
            liveData.postValue(queueInfoList);
        }
        return liveData;
    }

    public void getQueueFromRepo(){
        queueInfoList =  queueRepository.createQueueFromDb();
        liveData.postValue(queueInfoList);

    }

    public String getVoiceData(List<QueueModel> queueList){
        String voiceData = "";
        for(QueueModel queueModel :queueList){
            voiceData += " Token Number "+queueModel.getNextQueueId()+" , "+queueModel.getRoomNumber()+". ";
        }
        return voiceData;
    }


    @Override
    public void run() {
        System.out.println("New thread Start.....============2");
        while (true){
            try {

                Thread.sleep(60000);
            }catch (Exception e){

            }
            getQueueFromRepo();
            System.out.println("do something===========");

        }
    }
}
