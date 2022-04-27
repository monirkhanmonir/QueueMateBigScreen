package com.excellenceict.queuematebigscreen.view.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.excellenceict.queuematebigscreen.R;
import com.excellenceict.queuematebigscreen.service.network.TestDBConnector;
import com.excellenceict.queuematebigscreen.util.Constants;
import com.excellenceict.queuematebigscreen.util.QueueMateHelper;
import com.excellenceict.queuematebigscreen.util.QueuePreference;
import com.excellenceict.queuematebigscreen.util.ToastHelper;
import com.excellenceict.queuematebigscreen.view.ui.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.SQLException;

public class ConfigActivity extends AppCompatActivity {
    private static final String TAG = "ConfigActivity";
    private QueuePreference queuePreference;
    private Context context;
    private TextInputEditText dbIpAddress, dbPortNumber, dbNameId, dbUserName, dbPassword;
    private Button btnLogin;
    private String ipMatcher = "^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        dbIpAddress = findViewById(R.id.dbIpId);
        dbPortNumber = findViewById(R.id.dbPortId);
        dbUserName = findViewById(R.id.dbUserNameId);
        dbPassword = findViewById(R.id.dbPasswordId);
        dbNameId = findViewById(R.id.dbNameId);
        btnLogin = findViewById(R.id.btnLogin);

        context = getApplicationContext();
        queuePreference = new QueuePreference(context);


        if (queuePreference.getBoolean(Constants.KEY_IS_CONFIG, false)) {
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }


        dbIpAddress.setText(queuePreference.getString(Constants.KEY_DATABASE_IP_ADDRESS, null));
        dbPortNumber.setText(queuePreference.getString(Constants.KEY_DATABASE_PORT_NUMBER, null));
        dbNameId.setText(queuePreference.getString(Constants.KEY_DATABASE_NAME, null));
        dbUserName.setText(queuePreference.getString(Constants.KEY_DATABASE_USERNAME, null));
        dbPassword.setText(queuePreference.getString(Constants.KEY_DATABASE_PASSWORD, null));

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       android.text.Spanned dest, int dstart, int dend) {
                if (end > start) {
                    String destTxt = dest.toString();
                    String resultingTxt = destTxt.substring(0, dstart) + source.subSequence(start, end) + destTxt.substring(dend);
                    if (!resultingTxt.matches(ipMatcher)) {
                        return "";
                    } else {
                        String[] splits = resultingTxt.split("\\.");
                        for (int i = 0; i < splits.length; i++) {
                            if (Integer.valueOf(splits[i]) > 255) {
                                return "";
                            }
                        }
                    }
                }
                return null;
            }
        };
        dbIpAddress.setFilters(filters);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queuePreference.putString(Constants.KEY_DATABASE_IP_ADDRESS, dbIpAddress.getText().toString());
                queuePreference.putString(Constants.KEY_DATABASE_PORT_NUMBER, dbPortNumber.getText().toString());
                queuePreference.putString(Constants.KEY_DATABASE_NAME, dbNameId.getText().toString());
                queuePreference.putString(Constants.KEY_DATABASE_USERNAME, dbUserName.getText().toString());
                queuePreference.putString(Constants.KEY_DATABASE_PASSWORD, dbPassword.getText().toString());
                queuePreference.putBoolean(Constants.KEY_IS_CONFIG, true);
                ToastHelper.showSnackBarToast(v, "Login Success!", Color.WHITE, Color.BLUE);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void checkDbConnection(View view) {
        String ipAddress = dbIpAddress.getText().toString().trim();
        String portNumber = dbPortNumber.getText().toString().trim();
        String dbName = dbNameId.getText().toString().trim();
        String userName = dbUserName.getText().toString().trim();
        String password = dbPassword.getText().toString().trim();

        if (ipAddress == null || ipAddress.isEmpty()) {
            ToastHelper.showSnackBarToast(view, "Please input an ip address!", Color.WHITE, Color.RED);
            Log.d(TAG, "Please Inter IP Address");
        } else if (!QueueMateHelper.validateIpAddress(ipAddress)) {
            ToastHelper.showSnackBarToast(view, "Please input valid ip address!", Color.WHITE, Color.RED);
            Log.d(TAG, "Please Inter Valid Ip Address");
        } else if (portNumber == null || portNumber.isEmpty()) {
            ToastHelper.showSnackBarToast(view, "Please input valid port number!", Color.WHITE, Color.RED);
            Log.d(TAG, "Please input port number ");
        } else if (dbName == null || dbName.isEmpty()) {
            ToastHelper.showSnackBarToast(view, "Please input database name!", Color.WHITE, Color.RED);
            Log.d(TAG, "Please input database name ");
        } else if (userName == null || userName.isEmpty()) {
            Log.d(TAG, "Please input database user name");
            ToastHelper.showSnackBarToast(view, "Please input database user name!", Color.WHITE, Color.RED);
        } else if (password == null || password.isEmpty()) {
            ToastHelper.showSnackBarToast(view, "Please input database password!", Color.WHITE, Color.RED);
            Log.d(TAG, "Please input port number ");
        } else {

            TestDBConnector connector = new TestDBConnector(
                    ipAddress, portNumber, dbName.toUpperCase(),
                    userName.toUpperCase(),
                    password.toUpperCase());
            Log.d(TAG, "Connection Ip......... " + ipAddress + " //" + portNumber);
            if (QueueMateHelper.serverHostCheck(ipAddress, Integer.parseInt(portNumber))) {
                Connection connection = null;
                try {
                    connection = connector.createConnection();
                    if (connection != null) {
                        Log.d(TAG, "Connection Success.......... ");
                        ToastHelper.showSnackBarToast(view, "Database connection success!", Color.WHITE, Color.BLUE);
                        btnLogin.setVisibility(View.VISIBLE);
                    } else {
                        btnLogin.setVisibility(View.INVISIBLE);
                        ToastHelper.showSnackBarToast(view, "Database not connected. Please check username and password!", Color.WHITE, Color.RED);
                        Log.d(TAG, "Connection fail.............. ");
                    }
                } catch (ClassNotFoundException e) {
                    ToastHelper.showSnackBarToast(view, "Database not connected. Please check username and password!", Color.WHITE, Color.RED);
                    btnLogin.setVisibility(View.INVISIBLE);
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    ToastHelper.showSnackBarToast(view, "Database not connected. Please check username and password!", Color.WHITE, Color.RED);
                    btnLogin.setVisibility(View.INVISIBLE);
                    throwables.printStackTrace();
                }

            } else {
                ToastHelper.showSnackBarToast(view, "Server is not rechable! Please check IP or Port", Color.WHITE, Color.RED);
                Log.d(TAG, "..............IP: " + ipAddress + " // Port number: " + portNumber + ". server is not rechable");
            }
        }
    }


}