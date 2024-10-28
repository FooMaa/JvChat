package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.logger.JvLog;

import java.io.*;
import java.net.Socket;


public class JvServersSocketThreadCtrl extends Thread {
    private DataInputStream readFromUser;
    private DataOutputStream sendToUser;
    private int errorsConnection;
    private final int limitErrorsConnection;

    JvServersSocketThreadCtrl(Socket fromSocketServer) {
        try {
            sendToUser = new DataOutputStream(fromSocketServer.getOutputStream());
            readFromUser =  new DataInputStream(fromSocketServer.getInputStream());
        } catch (IOException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Ошибка в создании потоков отправки и принятия сообщений");
        }

        errorsConnection = 0;
        limitErrorsConnection = 3;

        start();
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        try {
            while (true) {
                int length = readFromUser.readInt();
                if (length > 0) {
                    byte[] message = new byte[length];
                    readFromUser.readFully(message, 0, message.length);
                    JvGetterControls.getInstance().getBeanNetworkCtrl().takeMessage(message, currentThread());
                }
            }
        } catch (IOException exception) {
            errorsConnection++;
            JvLog.write(JvLog.TypeLog.Error, "Error in network");
        }
    }

    public void send(byte[] message) {
        try {
            sendToUser.writeInt(message.length);
            sendToUser.write(message);
            sendToUser.flush();
        } catch (IOException exception) {
            errorsConnection++;
            JvLog.write(JvLog.TypeLog.Error, "Error in network");
        }
    }

    public boolean isErrorsExceedsLimit() {
        return (errorsConnection >= limitErrorsConnection);
    }
}