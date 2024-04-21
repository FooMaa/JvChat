package org.foomaa.jvchat.ctrl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;

@Component
@Scope("prototype")
public class JvServersSocketThreadCtrl extends Thread
{
    private DataInputStream readFromUser;
    private DataOutputStream sendToUser;

    public JvServersSocketThreadCtrl(Socket fromSocketUser) {
        try {
            sendToUser = new DataOutputStream(fromSocketUser.getOutputStream());
            readFromUser =  new DataInputStream(fromSocketUser.getInputStream());
        } catch (IOException exception) {
            System.out.println("Ошибка в создании потоков отправки и принятия сообщений");
        }
        start();
    }

    @Override
    public void run() {
        JvGetterControls.getInstance();
        try {
            while (true) {
                int length = readFromUser.readInt();
                if (length > 0) {
                    byte[] message = new byte[length];
                    readFromUser.readFully(message, 0, message.length);
                    JvNetworkCtrl.takeMessage(message, currentThread());
                }
            }
        } catch (IOException exception) {
            System.out.println("Error in network");
        }
    }

    public void send(byte[] message) {
        try {
            sendToUser.writeInt(message.length);
            sendToUser.write(message);
            sendToUser.flush();
        } catch (IOException exception) {
            System.out.println("Error in network");
        }
    }
}