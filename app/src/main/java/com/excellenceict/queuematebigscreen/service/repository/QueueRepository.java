package com.excellenceict.queuematebigscreen.service.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.excellenceict.queuematebigscreen.service.model.QueueModel;
import com.excellenceict.queuematebigscreen.service.network.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueueRepository implements Runnable{

    private static Context context;
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

//    public MutableLiveData<List<QueueModel>> getQueueInfoList() {
//        if (liveData == null) {
//             createQueueFromDb();
//         //   new QueueTask().execute();
//          //  getDataWithThread();
//
//          //  new QueueRepository().start();
//
//        }
//        return liveData;
//    }


//    public void getDataWithThread() {
//        while (true) {
//            try {
//               new QueueTask().execute();
//                Thread.sleep(5000);
//            } catch (Exception e) {
//
//            }
//        }
//    }

    public List<QueueModel> createQueueFromDb() {
        int screenNo = 1;
        queueInfoList = new ArrayList<>();

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        System.out.println("Call Connection ============");
        try {
            connection = DBConnector.createConnection();
            System.out.println("Call Connection ============ 2");
            if (connection != null) {
                System.out.println("DB Connection success============");
                String query = "select NVL(bs.V_WS_CODE, ''), \n" +
                        "bs.N_QUEUE_ID1, bs.V_PERSON_NAME1,\n" +
                        "NVL(bs.N_QUEUE_ID2, ''), NVL(bs.V_PERSON_NAME2, ''), NVL(bs.N_PK_ID, ''), \n" +
                        "NVL(bs.N_QUEUE_ID3, ''), NVL(bs.V_PERSON_NAME3, '')\n" +
                        "From V_QMS_BIG_SCREEN bs, QMS_STATION_SCREEN_MAP s\n" +
                        "where bs.N_WS_ID = s.N_WS_ID\n" +
                        "and s.N_SCREEN_ID = '"+screenNo+"'";

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    System.out.println("Result data:======================= " + resultSet.getString(3));

                    queueInfoList.add(new QueueModel(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7)
                    ));

                }
                //liveData.postValue(queueInfoList);

                connection.close();
            } else {
                System.out.println("DB Connection fail============");
            }
        } catch (Exception e) {
            System.out.println("Connection Exception===========1=" + e.getMessage());
        }
        return queueInfoList;
    }

    @Override
    public void run() {
        System.out.println("New thread Start.....============");
        while (true){
            try {

                Thread.sleep(60000);
            }catch (Exception e){

            }
            createQueueFromDb();
            System.out.println("do something===========");

        }
    }


//    private class QueueTask extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//
//            if (liveData == null) {
//                liveData = new MutableLiveData();
//            }
//            queueInfoList = new ArrayList<>();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            if (Build.VERSION.SDK_INT > 9) {
//                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                StrictMode.setThreadPolicy(policy);
//            }
//            System.out.println("Call Connection ============");
//            try {
//                connection = DBConnector.createConnection();
//                System.out.println("Call Connection ============ 2");
//                if (connection != null) {
//                    System.out.println("DB Connection success============");
//                    String query = "SELECT D_QUEUE_TIME, N_QUEUE_ID,\n"
//                            + "decode(substr(V_PERSON_NAME,1,instr(V_PERSON_NAME,' ')-1),null,V_PERSON_NAME,substr(V_PERSON_NAME,1,instr(V_PERSON_NAME,' ')-1)) V_PERSON_NAME, \n"
//                            + "N_CALL_NOW, nvl(V_WS_CODE,'UNKNOWN') V_WS_CODE\n"
//                            + "FROM V_QMS_QUEUE_C l \n"
//                            + "WHERE l.D_QUEUE_DATE = trunc(sysdate)\n"
//                            + "and l.D_QUEUE_END_DATE is null\n"
//                            + "and l.D_QUEUE_TIME >= sysdate-1/1\n"
//                            + "order by D_QUEUE_TIME, N_QUEUE_ID";
//
//                    Statement statement = connection.createStatement();
//                    ResultSet resultSet = statement.executeQuery(query);
//                    while (resultSet.next()) {
//                        System.out.println("Result data:======================= " + resultSet.getString(3));
//
//                        queueInfoList.add(new QueueModel(
//                                resultSet.getString(1),
//                                resultSet.getString(2),
//                                resultSet.getString(3),
//                                resultSet.getInt(4),
//                                resultSet.getString(5)
//                        ));
//
//                    }
//                    liveData.postValue(queueInfoList);
//
//                    connection.close();
//                } else {
//                    System.out.println("DB Connection fail============");
//                }
//            } catch (Exception e) {
//                System.out.println("Connection Exception===========1=" + e.getMessage());
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//
//        }
//    }


}
