package org.foomaa.jvchat.ctrl;

import java.io.*;
import java.net.Socket;

public class JvServersSocketThreadCtrl extends Thread
{
    private Socket socketTread;
    private DataInputStream readFromUser;
    private DataOutputStream sendToUser;

    public JvServersSocketThreadCtrl(Socket fromSocketUser) {
        this.socketTread = fromSocketUser;
        try {
            sendToUser = new DataOutputStream(socketTread.getOutputStream());
            readFromUser =  new DataInputStream(socketTread.getInputStream());
        } catch (IOException exception) {
            System.out.println("Ошибка в создании потоков отправки и принятия сообщений");
        }
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                int length = readFromUser.readInt();
                if (length > 0) {
                    byte[] message = new byte[length];
                    readFromUser.readFully(message, 0, message.length);
                    JvNetworkCtrl.takeMessage(message, currentThread());
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void send(byte[] message) {
        try {
            sendToUser.writeInt(message.length);
            sendToUser.write(message);
            sendToUser.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
