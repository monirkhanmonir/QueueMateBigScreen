package com.excellenceict.queuematebigscreen.service.network;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class TestDBConnector {

    private  String DEFAULT_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private  String DEFAULT_URL;
    private  String DEFAULT_USERNAME;
    private  String DEFAULT_PASSWORD;
    private  String IP_ADDRESS;

    public TestDBConnector(String ipAddress, String portNumber, String dbName,String dbUsername, String dbPassword) {
        this.DEFAULT_URL = "jdbc:oracle:thin:@"+ipAddress+":"+portNumber+":"+dbName;
        this.DEFAULT_USERNAME = dbUsername;
        this.DEFAULT_PASSWORD = dbPassword;
        this.IP_ADDRESS = ipAddress;
        Log.d("TAG","Sql URL: ========== "+DEFAULT_URL);
    }

//    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//
//StrictMode.setThreadPolicy(policy);

    public static Connection createConnection(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public  Connection createConnection() throws ClassNotFoundException, SQLException {
        return createConnection(DEFAULT_DRIVER, DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }

//    public  Connection createConnection2() {
//        Connection connection = null;
//        try {
//            InetAddress inet = InetAddress.getByName(IP_ADDRESS);
//            if (inet.isReachable(5000)){
//              connection =   createConnection();
//            } else {
//                connection = null;
//                System.out.println("DATABASE  ("+ IP_ADDRESS + ") is NOT reachable.");
//            }
//        } catch (UnknownHostException e) {
//            connection = null;
//            e.printStackTrace();
//        } catch (IOException e) {
//            connection = null;
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            connection = null;
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            connection = null;
//            e.printStackTrace();
//        }
//        return connection;
//    }
}
