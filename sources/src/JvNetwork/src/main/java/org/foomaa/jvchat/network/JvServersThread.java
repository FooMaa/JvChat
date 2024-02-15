package org.foomaa.jvchat.network;

import java.io.*;
import java.net.Socket;
import java.util.function.BiConsumer;

public class JvServersThread extends Thread {
    private Socket socketTread;
    private BufferedReader readFromUser;
    private BufferedWriter writeToUser;
    private BiConsumer<String,Thread> connectFunction;

    public JvServersThread(Socket fromSocketUser, BiConsumer<String,Thread> func) throws IOException {
        this.socketTread = fromSocketUser;
        readFromUser = new BufferedReader(new InputStreamReader(socketTread.getInputStream()));
        writeToUser = new BufferedWriter(new OutputStreamWriter(socketTread.getOutputStream()));
        connectFunction = func;
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("The message: " + readFromUser.readLine());
                if (readFromUser.readLine() == null) {
                    break;
                }
                System.out.println("The message: " + readFromUser.readLine());
//                JvNetworkCtrl.takeMessage(readFromUser.readLine(), currentThread());
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void send(byte[] message) {
        try {
            writeToUser.write(message + "\n");
            writeToUser.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
