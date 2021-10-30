package com.excellenceict.queuematebigscreen.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.excellenceict.queuematebigscreen.service.model.QueueModel;
import com.excellenceict.queuematebigscreen.service.model.ScreenInfo;
import com.excellenceict.queuematebigscreen.service.repository.ScreenInfoRepository;

import java.util.ArrayList;
import java.util.List;

public class ScreenInfoViewModel extends AndroidViewModel {
    private ScreenInfoRepository screenRepository;
    private MutableLiveData<List<ScreenInfo>> screenLiveData;
    private List<ScreenInfo> screenList;

    public ScreenInfoViewModel(@NonNull Application application) {
        super(application);
        screenRepository = ScreenInfoRepository.getScreenRepositoryInstance(application);
        screenList = new ArrayList<>();
    }


    public MutableLiveData<List<ScreenInfo>> getAllScreenInfo(){
        Log.d("TAG","SCREEn SIZE=========1");
        if(screenLiveData==null){
            Log.d("TAG","SCREEn SIZE=========");
            screenLiveData = new MutableLiveData<>();
            screenList =  screenRepository.getScreenInfoFromDb();
            Log.d("TAG","SIZE========="+screenList.size());
            screenLiveData.postValue(screenList);
        }
        return screenLiveData;
    }



}
