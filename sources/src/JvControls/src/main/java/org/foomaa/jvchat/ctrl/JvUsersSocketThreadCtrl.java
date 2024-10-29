package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.logger.JvLog;

import java.io.*;
import java.net.Socket;


// TODO(VAD) : переписать с Runnable
public class JvUsersSocketThreadCtrl extends Thread {
    private DataInputStream readFromServer;
    private DataOutputStream sendToServer;

    JvUsersSocketThreadCtrl(Socket fromSocketUser) {
        try {
            sendToServer = new DataOutputStream(fromSocketUser.getOutputStream());
            readFromServer =  new DataInputStream(fromSocketUser.getInputStream());
        } catch (IOException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Ошибка в создании потоков отправки и принятия сообщений");
        }
        start();
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        try {
            while (true) {
                int length = readFromServer.readInt();
                if (length > 0) {
                    byte[] message = new byte[length];
                    readFromServer.readFully(message, 0, message.length);
                    JvGetterControls.getInstance().getBeanNetworkCtrl().takeMessage(message, currentThread());
                }
            }
        } catch (IOException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in network");
        }
    }

    public void send(byte[] message) {
        try {
            sendToServer.writeInt(message.length);
            sendToServer.write(message);
            sendToServer.flush();
        } catch (IOException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Error in network");
        }
    }
}