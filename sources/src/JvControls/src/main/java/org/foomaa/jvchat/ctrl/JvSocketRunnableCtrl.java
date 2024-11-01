package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.models.JvGetterModels;
import org.foomaa.jvchat.models.JvSocketStreamsModel;
import org.foomaa.jvchat.structobjects.JvSocketStreamsStructObject;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;


public class JvSocketRunnableCtrl implements Runnable {
    private final JvSocketStreamsStructObject socketStreamsStructObject;
    private final int limitErrorsConnection;
    private int errorsConnection;

    JvSocketRunnableCtrl(Socket socket) {
        JvSocketStreamsModel socketStreamsModel = JvGetterModels.getInstance().getBeanSocketStreamsModel();
        socketStreamsStructObject = socketStreamsModel.createSocketStreams(socket);
        errorsConnection = 0;
        limitErrorsConnection = 3;
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
            errorsConnection++;
            JvLog.write(JvLog.TypeLog.Error, "Error in network");
        }
    }

    public void send(byte[] message) {
        try {
            socketStreamsStructObject.sendStreamProcess(message);
        } catch (IOException exception) {
            errorsConnection++;
            JvLog.write(JvLog.TypeLog.Error, "Error in network");
        }
    }

    public boolean isErrorsExceedsLimit() {
        return (errorsConnection >= limitErrorsConnection);
    }

    public UUID getUuidStreamsStructObject() {
        return socketStreamsStructObject.getUuid();
    }
}
