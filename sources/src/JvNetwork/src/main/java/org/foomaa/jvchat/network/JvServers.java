package org.foomaa.jvchat.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class JvServers extends Thread {
    private static JvServers instance;
    private static ServerSocket serverSocket;
    private static BufferedReader fromSocket;
    private static BufferedWriter inSocket;

    private JvServers() throws IOException {
        serverSocket = new ServerSocket(4004);
        System.out.println("Open socket");
//        serverSocket.setSoTimeout(10000);
    }

    public static JvServers getInstance() throws IOException {
        if(instance == null){
            instance = new JvServers();
        }
        return instance;
    }
}
