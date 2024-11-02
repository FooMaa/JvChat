package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.models.JvGetterModels;
import org.foomaa.jvchat.structobjects.JvSocketStreamsStructObject;

import java.io.IOException;
import java.net.Socket;


public class JvSocketRunnableCtrl implements Runnable {
    private final JvSocketStreamsStructObject socketStreamsStructObject;

    JvSocketRunnableCtrl(Socket socket) {
        socketStreamsStructObject = JvGetterModels.getInstance().getBeanSocketStreamsModel().createSocketStreams(socket);
        JvGetterModels.getInstance().getBeanSocketRunnableCtrlModel().createRunnableCtrl(this);
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        try {
            while (true) {
                byte[] message = socketStreamsStructObject.readStreamProcess();
                if (message != null) {
                    JvGetterControls.getInstance().getBeanNetworkCtrl().takeMessage(message, Thread.currentThread());
                }
            }
        } catch (IOException exception) {
            increaseErrorCounter();
            JvLog.write(JvLog.TypeLog.Error, "Error in network");
        }
    }

    public void send(byte[] message) {
        try {
            socketStreamsStructObject.sendStreamProcess(message);
        } catch (IOException exception) {
            increaseErrorCounter();
            JvLog.write(JvLog.TypeLog.Error, "Error in network");
        }
    }

    private void increaseErrorCounter() {
        int currentErrors = socketStreamsStructObject.getErrorsConnection();
        currentErrors++;
        socketStreamsStructObject.setErrorsConnection(currentErrors);
    }
}
