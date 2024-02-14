package org.foomaa.jvchat.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class JvServersListenThread extends Thread
{
    private Socket socketTread;

    public JvServersListenThread(Socket fromSocketUser) {
        this.socketTread = fromSocketUser;
    }

    @Override
    public void run() {
        try (Socket tempSocket = socketTread;
             BufferedReader readFromUser = new BufferedReader(new InputStreamReader(tempSocket.getInputStream()))) {

            String inputMessage;
            while ((inputMessage = readFromUser.readLine()) != null) {
                System.out.println("The message: " + inputMessage);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
