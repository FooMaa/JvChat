package org.foomaa.jvchat.network;

import org.foomaa.jvchat.settings.JvMainSettings;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;


public class JvServersSocket {
    private static JvServersSocket instance;

    private JvServersSocket() {
        System.out.println("Server is started");
        try {
            ServerSocket servSocket = new ServerSocket(JvMainSettings.port,
                    1000, InetAddress.getByName(JvMainSettings.ip));
            while (true) {
                // Получив соединение начинаем работать с сокетом
                Socket fromClientSocket = servSocket.accept();
                // Стартуем новый поток для обработки запроса клиента
                new JvSocketThread(fromClientSocket).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static JvServersSocket getInstance() throws IOException {
        if(instance == null){
            instance = new JvServersSocket();
        }
        return instance;
    }
}

class JvSocketThread extends Thread
{
    private Socket fromClient;

    public JvSocketThread(Socket fromClientSocket) {
        this.fromClient = fromClientSocket;
    }

    @Override
    public void run() {
        // Автоматически будут закрыты все ресурсы
        try (Socket tempSocket = fromClient;
             PrintWriter pw = new PrintWriter(tempSocket.getOutputStream(), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(tempSocket.getInputStream()))) {
            
            String str;
            while ((str = br.readLine()) != null) {
                System.out.println("The message: " + str);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

