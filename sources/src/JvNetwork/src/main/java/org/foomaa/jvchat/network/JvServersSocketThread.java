package org.foomaa.jvchat.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class JvServersSocketThread extends Thread
{
    private Socket socketTread;

    public JvServersSocketThread(Socket fromSocketUser) {
        this.socketTread = fromSocketUser;
    }

    @Override
    public void run() {
        try (Socket tempSocket = socketTread;
             PrintWriter pw = new PrintWriter(tempSocket.getOutputStream(), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(tempSocket.getInputStream()))) {

            String str;
            while ((str = br.readLine()) != null) {
                System.out.println("The message: " + str);
            }
            pw.write("KILL");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}