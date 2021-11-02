package com.excellenceict.queuematebigscreen.service.network;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.excellenceict.queuematebigscreen.util.Constants;
import com.excellenceict.queuematebigscreen.util.QueuePreference;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnector {
    private static QueuePreference queuePreference;
    private static final String DEFAULT_DRIVER = "oracle.jdbc.driver.OracleDriver";
//    private static final String DEFAULT_URL = "jdbc:oracle:thin:@202.125.75.236:1522:ORCL1";
//    private static final String DEFAULT_USERNAME = "QUEUEMATE";
//    private static final String DEFAULT_PASSWORD = "QUEUEMATE";
    private Context context;

    public DBConnector(Context context){
        this.context = context;
        queuePreference = new QueuePreference(context);
    }

    public static Connection createConnection(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException {

        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public static Connection createConnection() throws ClassNotFoundException, SQLException {
         String DEFAULT_URL = "jdbc:oracle:thin:@"+queuePreference.getString(Constants.KEY_DATABASE_IP_ADDRESS,null)
                 +":"+queuePreference.getString(Constants.KEY_DATABASE_PORT_NUMBER,null)
                 +":"+queuePreference.getString(Constants.KEY_DATABASE_NAME,null);
         String DEFAULT_USERNAME = queuePreference.getString(Constants.KEY_DATABASE_USERNAME,null);
         String DEFAULT_PASSWORD = queuePreference.getString(Constants.KEY_DATABASE_PASSWORD,null);

        Log.d("TAG","Url"+DEFAULT_URL+"\n username: "+DEFAULT_USERNAME +"\n Password: "+DEFAULT_PASSWORD);

        return createConnection(DEFAULT_DRIVER, DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }
}
