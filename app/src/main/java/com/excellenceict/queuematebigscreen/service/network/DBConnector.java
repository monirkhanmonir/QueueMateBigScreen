package com.excellenceict.queuematebigscreen.service.network;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnector {
    private static final String DEFAULT_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DEFAULT_URL = "jdbc:oracle:thin:@202.125.75.236:1522:ORCL1";
    private static final String DEFAULT_USERNAME = "QUEUEMATE";
    private static final String DEFAULT_PASSWORD = "QUEUEMATE";

    public static Connection createConnection(String driver, String url, String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public static Connection createConnection() throws ClassNotFoundException, SQLException {
        return createConnection(DEFAULT_DRIVER, DEFAULT_URL, DEFAULT_USERNAME, DEFAULT_PASSWORD);
    }
}
