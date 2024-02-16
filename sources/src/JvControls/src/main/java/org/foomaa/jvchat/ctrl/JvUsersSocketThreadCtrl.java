package org.foomaa.jvchat.ctrl;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class JvUsersSocketThreadCtrl extends Thread {
    private Socket socketTread;
    private DataInputStream readFromServer;
    private DataOutputStream sendToServer;

    public JvUsersSocketThreadCtrl(Socket fromSocketUser) {
        this.socketTread = fromSocketUser;
        try {
            sendToServer = new DataOutputStream(socketTread.getOutputStream());
            readFromServer =  new DataInputStream(socketTread.getInputStream());
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
                    System.out.println(Arrays.toString(message));
                    JvNetworkCtrl.takeMessage(message, null);
                }
            }
        } catch (IOException exception) {}
    }

    public void send(byte[] message) {
        try {
            sendToServer.writeInt(message.length);
            sendToServer.write(message);
            sendToServer.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
