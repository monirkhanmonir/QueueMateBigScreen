package com.excellenceict.queuematebigscreen.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.sql.Connection;

public class QueueMateHelper {

    public static boolean validateIpAddress(String ipAddress){
        int count = 0;
        boolean ipStatus = false;
        for (int i=0; i<ipAddress.length(); i++){
            if(ipAddress.charAt(i)=='.'){
                count++;
            }
        }

        if(count==3){
            ipStatus = true;
        }else {
            ipStatus = false;
        }
        return ipStatus;
    }




    public static boolean serverHostCheck(String ipAddress, int portNumber) {
        boolean exists = false;

        try {
//            SocketAddress sockaddr = new InetSocketAddress("202.125.75.236", 1522);
            SocketAddress sockaddr = new InetSocketAddress(ipAddress, portNumber);
            Socket sock = new Socket();
            int timeoutMs = 2000;   // 2 seconds
            sock.connect(sockaddr, timeoutMs);
            exists = true;
//            System.out.println("===========" + exists);
        } catch (IOException e) {
            System.out.println("===========" + e);

            // Handle exception
        }
        return exists;
    }


}
