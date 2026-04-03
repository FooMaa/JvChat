package org.foomaa.jvchat.ctrl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

import org.springframework.context.annotation.Lazy;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.models.SocketRunnableCtrlModel;

/*
 * NOTE(VAD): here it is done so that the tasks of the server and the user
 * solved by one class. There is a model of all connections SocketRunnableCtrlModel.
 * The main element of the model is SocketRunnableCtrlStructObject,
 * which contains a Runnable field. This field is the object
 * of this SocketRunnableCtrl class.
 */
@Slf4j
public class SocketRunnableCtrl implements Runnable {
    private DataOutputStream sendStream;
    private DataInputStream readStream;
    private final int limitErrorsConnection;
    private int errorsConnection;

    // DI ↓
    private final NetworkCtrl networkCtrl;

    @Builder
    SocketRunnableCtrl(SocketRunnableCtrlModel socketRunnableCtrlModel, @Lazy NetworkCtrl networkCtrl) {
        Objects.requireNonNull(socketRunnableCtrlModel, "socketRunnableCtrlModel is mandatory");
        this.networkCtrl = Objects.requireNonNull(networkCtrl, "networkCtrl is mandatory");

        socketRunnableCtrlModel.createSocketRunnableCtrlStructObject(this);

        sendStream = null;
        readStream = null;

        errorsConnection = 0;
        limitErrorsConnection = 3;
    }

    public void setSocket(Socket socket) {
        try {
            sendStream = new DataOutputStream(socket.getOutputStream());
            readStream = new DataInputStream(socket.getInputStream());
        } catch (IOException exception) {
            log.error("Error in creating threads for sending and receiving messages.");
        }
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
                    networkCtrl.takeMessage(message, this);
                }
            }
        } catch (IOException exception) {
            errorsConnection++;
            log.error("Error in network.");
        }
    }

    public void send(byte[] message) {
        try {
            sendStream.writeInt(message.length);
            sendStream.write(message);
            sendStream.flush();
        } catch (IOException exception) {
            errorsConnection++;
            log.error("Error in network.");
        }
    }

    public boolean isErrorsExceedsLimit() {
        return (errorsConnection >= limitErrorsConnection);
    }
}
