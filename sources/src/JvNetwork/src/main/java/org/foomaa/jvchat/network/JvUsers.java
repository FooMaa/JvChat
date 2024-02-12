package org.foomaa.jvchat.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;

public class JvUsers {
    private static JvUsers instance;

    private JvUsers() throws IOException {
    }

    public static JvUsers getInstance() throws IOException {
        if(instance == null){
            instance = new JvUsers();
        }
        return instance;
    }
}
