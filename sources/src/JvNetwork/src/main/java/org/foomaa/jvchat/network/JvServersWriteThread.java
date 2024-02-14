package org.foomaa.jvchat.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class JvServersWriteThread extends Thread
{
    private Socket socketTread;

    public JvServersWriteThread(Socket fromSocketUser) {
        this.socketTread = fromSocketUser;
    }

    @Override
    public void run() {
        try (Socket tempSocket = socketTread;
             PrintWriter writeToUser = new PrintWriter(tempSocket.getOutputStream(), true)) {
            writeToUser.println("Hello");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
