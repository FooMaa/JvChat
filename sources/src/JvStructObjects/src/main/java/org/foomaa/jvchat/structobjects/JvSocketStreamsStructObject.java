package org.foomaa.jvchat.structobjects;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class JvSocketStreamsStructObject extends JvBaseStructObject {
    private final UUID uuid;
    private DataOutputStream sendStream;
    private DataInputStream readStream;
    private final int limitErrorsConnection;
    private int errorsConnection;

    JvSocketStreamsStructObject() {
        uuid = UUID.randomUUID();
        sendStream = null;
        readStream = null;
        errorsConnection = 0;
        limitErrorsConnection = 3;

        commitProperties();
    }

    public void setSendStream(DataOutputStream newSendStream) {
        if (sendStream != newSendStream) {
            sendStream = newSendStream;
            commitProperties();
        }
    }

    public void setReadStream(DataInputStream newReadStream) {
        if (readStream != newReadStream) {
            readStream = newReadStream;
            commitProperties();
        }
    }

    public void setErrorsConnection(int newErrorsConnection) {
        if (errorsConnection != newErrorsConnection) {
            errorsConnection = newErrorsConnection;
            commitProperties();
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public DataOutputStream getSendStream() {
        return sendStream;
    }

    public DataInputStream getReadStream() {
        return readStream;
    }

    public int getErrorsConnection() {
        return errorsConnection;
    }

    public byte[] readStreamProcess() throws IOException {
        int length = readStream.readInt();

        if (length > 0) {
            byte[] message = new byte[length];
            readStream.readFully(message, 0, message.length);
            return message;
        }

        return null;
    }

    public void sendStreamProcess(byte[] message) throws IOException {
        sendStream.writeInt(message.length);
        sendStream.write(message);
        sendStream.flush();
    }

    public boolean isErrorsExceedsLimit() {
        return (errorsConnection >= limitErrorsConnection);
    }
}
