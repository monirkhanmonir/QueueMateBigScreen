package com.excellenceict.queuematebigscreen.service.repository;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.excellenceict.queuematebigscreen.service.model.QueueModel;
import com.excellenceict.queuematebigscreen.service.model.ScreenInfo;
import com.excellenceict.queuematebigscreen.service.network.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ScreenInfoRepository {
    private final static String TAG= "ScreenInfoRepository";

    private static Context context;
    private Connection connection;
    private List<ScreenInfo> screenInfoList;
    private static ScreenInfoRepository screenRepository;


    public static ScreenInfoRepository getScreenRepositoryInstance(Context context) {
        if (screenRepository == null) {
            context = context;
            screenRepository = new ScreenInfoRepository();
        }
        return screenRepository;
    }


    public List<ScreenInfo> getScreenInfoFromDb() {
        screenInfoList = new ArrayList<>();
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Log.d(TAG,"Call Connection ============");
        try {
            connection = DBConnector.createConnection();
            //  System.out.println("Call Connection ============ 2");
            if (connection != null) {
                Log.d(TAG,"DB Connection success============");
                String query = "select distinct N_SCREEN_ID, V_SCREEN_CODE, V_SCREEN_NAME\n" +
                        "From V_QMS_STATION_SCREEN_MAP_C\n" +
                        "order by V_SCREEN_CODE, V_SCREEN_NAME";

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    screenInfoList.add(new ScreenInfo(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3)
                    ));

                }
                //liveData.postValue(queueInfoList);
                Log.d(TAG,"Screen execute success============");
                connection.close();
            } else {
                Log.d(TAG,"DB Connection fail============");
            }
        } catch (Exception e) {
            Log.d(TAG,"Connection Exception===========1=" + e.getMessage());
        }
        return screenInfoList;
    }
}
