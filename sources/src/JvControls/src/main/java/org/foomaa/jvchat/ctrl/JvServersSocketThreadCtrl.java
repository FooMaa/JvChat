package org.foomaa.jvchat.ctrl;

import java.io.*;
import java.net.Socket;

public class JvServersSocketThreadCtrl extends Thread
{
    private Socket socketTread;
    private DataInputStream readFromUser;
    private DataOutputStream sendToUser;

    public JvServersSocketThreadCtrl(Socket fromSocketUser) throws IOException {
        this.socketTread = fromSocketUser;
        sendToUser = new DataOutputStream(socketTread.getOutputStream());
        readFromUser =  new DataInputStream(socketTread.getInputStream());
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
                    System.out.println(message);
                    JvNetworkCtrl.takeMessage(message, this);
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
