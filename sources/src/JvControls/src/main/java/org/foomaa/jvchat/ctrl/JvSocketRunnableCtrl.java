package org.foomaa.jvchat.ctrl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.models.JvGetterModels;


/* NOTE(VAD): here it is done so that the tasks of the server and the user
 * solved by one class. There is a model of all connections JvSocketRunnableCtrlModel.
 * The main element of the model is JvSocketRunnableCtrlStructObject,
 * which contains a Runnable field. This field is the object
 * of this JvSocketRunnableCtrl class.
 */
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
            JvLog.write(JvLog.TypeLog.Error, "Error in creating threads for sending and receiving messages.");
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
            JvLog.write(JvLog.TypeLog.Error, "Error in network.");
        }
    }

    public void send(byte[] message) {
        try {
            sendStream.writeInt(message.length);
            sendStream.write(message);
            sendStream.flush();
        } catch (IOException exception) {
            errorsConnection++;
            JvLog.write(JvLog.TypeLog.Error, "Error in network.");
        }
    }


    public boolean isErrorsExceedsLimit() {
        return (errorsConnection >= limitErrorsConnection);
    }
}
