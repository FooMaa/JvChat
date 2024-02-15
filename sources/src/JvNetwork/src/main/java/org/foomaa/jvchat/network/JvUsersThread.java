package org.foomaa.jvchat.network;

import org.foomaa.jvchat.ctrl.JvNetworkCtrl;

import java.io.*;
import java.net.Socket;

public class JvUsersThread extends Thread {
    private Socket socketTread;
    private BufferedReader readFromServer;
    private PrintWriter sendToServer;

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
                JvNetworkCtrl.takeMessage(readFromServer.readLine(), currentThread());
            }
        } catch (IOException exception) {}
    }

    public void send(byte[] message) {
        sendToServer.write(message + "\n");
        sendToServer.flush();
    }
}
