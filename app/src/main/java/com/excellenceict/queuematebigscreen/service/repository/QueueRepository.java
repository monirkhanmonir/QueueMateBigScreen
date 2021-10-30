package com.excellenceict.queuematebigscreen.service.repository;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.excellenceict.queuematebigscreen.service.model.QueueModel;
import com.excellenceict.queuematebigscreen.service.network.DBConnector;
import com.excellenceict.queuematebigscreen.util.QueuePreference;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueueRepository implements Runnable{
    private final static String TAG= "QueueRepository";
    private Context context;
    private static QueueRepository queueRepository;
    private Connection connection;
    private List<QueueModel> queueInfoList;


    public static QueueRepository getQueueRepositoryInstance(Context context) {
        if (queueRepository == null) {
            context = context;
            queueRepository = new QueueRepository();
        }
        return queueRepository;
    }

    public List<QueueModel> createQueueFromDb(Context context) {
        queueInfoList = new ArrayList<>();

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Log.d(TAG,"Call Connection ============"+QueuePreference.getSelectedScreenId(context));
        try {
            connection = DBConnector.createConnection();
            if (connection != null) {
                Log.d(TAG,"DB Connection success============");
                String query = "select NVL(bs.V_WS_CODE, ''), \n" +
                        "bs.N_QUEUE_ID1, bs.V_PERSON_NAME1,\n" +
                        "NVL(bs.N_QUEUE_ID2, ''), NVL(bs.V_PERSON_NAME2, ''), NVL(bs.N_PK_ID, ''), \n" +
                        "NVL(bs.N_QUEUE_ID3, ''), NVL(bs.V_PERSON_NAME3, '')\n" +
                        "From V_QMS_BIG_SCREEN bs, QMS_STATION_SCREEN_MAP s\n" +
                        "where bs.N_WS_ID = s.N_WS_ID\n" +
                        "and s.N_SCREEN_ID = '"+Integer.parseInt(QueuePreference.getSelectedScreenId(context))+"'";

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                 //   System.out.println("Result data:======================= " + resultSet.getString(3));

                    queueInfoList.add(new QueueModel(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            resultSet.getString(6)
                    ));

                }

                //liveData.postValue(queueInfoList);
                Log.d(TAG,"Queue info executed success============"+queueInfoList.size());
                Log.d(TAG, "Data: "+queueInfoList.toString());
                connection.close();
            } else {
                Log.d(TAG,"DB Connection fail============");
            }
        } catch (Exception e) {
            Log.d(TAG,"Connection Exception===========1=" + e.getMessage());
        }
        return queueInfoList;
    }

    public void updateQueueToDb(Context context, String pk_id) {

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Log.d(TAG,"Call Connection ============"+QueuePreference.getSelectedScreenId(context));
        try {
            connection = DBConnector.createConnection();
            if (connection != null) {
                Log.d(TAG,"DB Connection success============");
                String query = "update QMS_QUEUE\n" +
                        "set N_CALL_COUNT = N_CALL_COUNT + 1,\n" +
                        "    D_CALL_TIME = sysdate\n" +
                        "where D_QUEUE_DATE = trunc(sysdate)\n" +
                        "and N_PK_ID = '"+pk_id+"'";

                Statement statement = connection.createStatement();
                statement.executeUpdate(query);

                //liveData.postValue(queueInfoList);
                Log.d(TAG, "Data:=========== Query updaate execure success"+pk_id);
                connection.close();
            } else {
                Log.d(TAG,"DB Connection fail============");
            }
        } catch (Exception e) {
            Log.d(TAG,"Connection Exception===========1=" + e.getMessage());
        }
    }

    @Override
    public void run() {
        Log.d(TAG,"New thread Start.....============");
        System.out.println();
        while (true){
            try {

                Thread.sleep(60000);
            }catch (Exception e){

            }
            createQueueFromDb(context);
            Log.d(TAG,"Do something....============");
        }
    }

}
