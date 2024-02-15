package org.foomaa.jvchat.ctrl;

import java.io.*;
import java.net.Socket;

public class JvUsersSocketThreadCtrl extends Thread {
    private Socket socketTread;
    private BufferedReader readFromServer;
    private PrintWriter sendToServer;

    public JvUsersSocketThreadCtrl(Socket fromSocketUser) throws IOException {
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

    public void send(String message) {
        sendToServer.write(message + "\n");
        sendToServer.flush();
    }
}
