package org.foomaa.jvchat.ctrl;

import java.io.*;
import java.net.Socket;

public class JvUsersSocketThreadCtrl extends Thread {
    private Socket socketTread;
    private DataInputStream readFromServer;
    private DataOutputStream sendToServer;

    public JvUsersSocketThreadCtrl(Socket fromSocketUser) throws IOException {
        this.socketTread = fromSocketUser;
        sendToServer = new DataOutputStream(socketTread.getOutputStream());
        readFromServer =  new DataInputStream(socketTread.getInputStream());
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
                    System.out.println(message);
                    JvNetworkCtrl.takeMessage(message, currentThread());
                }
            }
        } catch (IOException exception) {}
    }

    public void send(byte[] message) throws IOException {
        try {
            sendToServer.writeInt(message.length);
            sendToServer.write(message);
            sendToServer.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
