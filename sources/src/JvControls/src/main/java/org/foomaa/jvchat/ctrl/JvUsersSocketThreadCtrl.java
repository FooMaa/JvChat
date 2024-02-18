package org.foomaa.jvchat.ctrl;

import java.io.*;
import java.net.Socket;

public class JvUsersSocketThreadCtrl extends Thread {
    private DataInputStream readFromServer;
    private DataOutputStream sendToServer;

    public JvUsersSocketThreadCtrl(Socket fromSocketUser) {
        try {
            sendToServer = new DataOutputStream(fromSocketUser.getOutputStream());
            readFromServer =  new DataInputStream(fromSocketUser.getInputStream());
        } catch (IOException exception) {
            System.out.println("Ошибка в создании потоков отправки и принятия сообщений");
        }
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                int length = readFromServer.readInt();
                if (length > 0) {
                    byte[] message = new byte[length];
                    readFromServer.readFully(message, 0, message.length);
                    System.out.println(message);
                    JvNetworkCtrl.takeMessage(message, currentThread());
                }
            }
        } catch (IOException exception) {
            System.out.println("Error in network");
        }
    }

    public void send(byte[] message) {
        try {
            sendToServer.writeInt(message.length);
            sendToServer.write(message);
            sendToServer.flush();
        } catch (IOException exception) {
            System.out.println("Error in network");
        }
    }
}
