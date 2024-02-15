package org.foomaa.jvchat.ctrl;

import java.io.*;
import java.net.Socket;

public class JvServersSocketThreadCtrl extends Thread
{
    private Socket socketTread;
    private BufferedReader readFromUser;
    private BufferedWriter writeToUser;

    public JvServersSocketThreadCtrl(Socket fromSocketUser) throws IOException {
        this.socketTread = fromSocketUser;
        readFromUser = new BufferedReader(new InputStreamReader(socketTread.getInputStream()));
        writeToUser = new BufferedWriter(new OutputStreamWriter(socketTread.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (readFromUser.readLine() == null) {
                    break;
                }
                System.out.println("The message: " + readFromUser.readLine());
                JvNetworkCtrl.takeMessage(readFromUser.readLine(), currentThread());
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

    public void send(String message) {
        try {
            writeToUser.write(message + "\n");
            writeToUser.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
