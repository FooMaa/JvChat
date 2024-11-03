package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.models.JvGetterModels;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class JvSocketRunnableCtrl implements Runnable {
    private DataOutputStream sendStream;
    private DataInputStream readStream;
    private final int limitErrorsConnection;
    private int errorsConnection;

    JvSocketRunnableCtrl(Socket socket) {
        JvGetterModels.getInstance().getBeanSocketRunnableCtrlModel().createSocketRunnableCtrlStructObject(this);

        try {
            sendStream = new DataOutputStream(socket.getOutputStream());
            readStream = new DataInputStream(socket.getInputStream());
        } catch (IOException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Ошибка в создании потоков отправки и принятия сообщений");
        }

        errorsConnection = 0;
        limitErrorsConnection = 3;
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        try {
            while (true) {
                int length = readStream.readInt();
                if (length > 0) {
                    byte[] message = new byte[length];
                    readStream.readFully(message, 0, message.length);
                    JvGetterControls.getInstance().getBeanNetworkCtrl().takeMessage(message, this);
                }
            }
        } catch (IOException exception) {
            errorsConnection++;
            JvLog.write(JvLog.TypeLog.Error, "Error in network");
        }
    }

    public void send(byte[] message) {
        try {
            sendStream.writeInt(message.length);
            sendStream.write(message);
            sendStream.flush();
        } catch (IOException exception) {
            errorsConnection++;
            JvLog.write(JvLog.TypeLog.Error, "Error in network");
        }
    }


    public boolean isErrorsExceedsLimit() {
        return (errorsConnection >= limitErrorsConnection);
    }
}
