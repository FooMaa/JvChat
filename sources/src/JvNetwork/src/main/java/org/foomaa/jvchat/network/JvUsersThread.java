package org.foomaa.jvchat.network;

import java.io.*;
import java.net.Socket;
import java.util.function.BiConsumer;

public class JvUsersThread extends Thread {
    private Socket socketTread;
    private BufferedReader readFromServer;
    private PrintWriter sendToServer;
    private BiConsumer<String,Thread> connectFunction;

    public JvUsersThread(Socket fromSocketUser, BiConsumer<String,Thread> func) throws IOException {
        this.socketTread = fromSocketUser;
        readFromServer = new BufferedReader(new InputStreamReader(socketTread.getInputStream()));
        sendToServer = new PrintWriter(new OutputStreamWriter(socketTread.getOutputStream()));
        connectFunction = func;
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println(readFromServer.readLine());
                connectFunction.accept(readFromServer.readLine(), currentThread());
            }
        } catch (IOException exception) {}
    }

    public void send(byte[] message) {
        sendToServer.write(message + "\n");
        sendToServer.flush();
    }
}
