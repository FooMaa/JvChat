package org.foomaa.jvchat.network;

import java.io.*;
import java.net.Socket;

class JvServersThread extends Thread
{
    private Socket socketTread;
    private BufferedReader readFromUser;
    private BufferedWriter writeToUser;

    public JvServersThread(Socket fromSocketUser) throws IOException {
        this.socketTread = fromSocketUser;
        readFromUser = new BufferedReader(new InputStreamReader(socketTread.getInputStream()));
        writeToUser = new BufferedWriter(new OutputStreamWriter(socketTread.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("The message: " + readFromUser.readLine());
                if (readFromUser.readLine() == null) {
                    break;
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void send(String messages) {
        try {
            writeToUser.write(messages + "\n");
            writeToUser.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
