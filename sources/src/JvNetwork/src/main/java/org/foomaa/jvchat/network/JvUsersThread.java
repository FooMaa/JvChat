package org.foomaa.jvchat.network;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class JvUsersThread extends Thread {
    private Socket socketTread;
    private BufferedReader readFromServer;
    private PrintWriter sendToServer;
    private  List<String> buffMessages = new ArrayList<String>();

    public JvUsersThread(Socket fromSocketUser) throws IOException {
        this.socketTread = fromSocketUser;
        readFromServer = new BufferedReader(new InputStreamReader(socketTread.getInputStream()));
        sendToServer = new PrintWriter(new OutputStreamWriter(socketTread.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(readFromServer.readLine());
            }
        } catch (IOException exception) {}
    }

    public void send(byte[] messages) {
        sendToServer.write(messages + "\n");
        sendToServer.flush();
    }


}
