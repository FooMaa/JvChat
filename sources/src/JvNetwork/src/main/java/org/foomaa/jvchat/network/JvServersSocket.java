package org.foomaa.jvchat.network;

import org.foomaa.jvchat.settings.JvMainSettings;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;


public class JvServersSocket {
    private static JvServersSocket instance;
    private static ServerSocket servSocket;
    private JvServersSocket() {

        System.out.println("Server is started");
        try {
            if (JvMainSettings.getIp().isEmpty()) {
                servSocket = new ServerSocket(JvMainSettings.getPort());
            } else {
                servSocket = new ServerSocket(JvMainSettings.getPort(),
                        1000, InetAddress.getByName(JvMainSettings.getIp()));
            }
            System.out.println(servSocket.getInetAddress().toString());
            System.out.println(servSocket.getLocalPort());

            closeSocketWhenKill();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    servSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));

            while (true) {
                // Получив соединение начинаем работать с сокетом
                Socket fromClientSocket = servSocket.accept();
                // Стартуем новый поток для обработки запроса клиента
                new JvServersSocketThread(fromClientSocket).start();
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

    private static void closeSocketWhenKill() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("Закрываем серверный сокет ...");
                servSocket.close();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }));
    }
}

class JvServersSocketThread extends Thread
{
    private Socket fromClient;

    public JvServersSocketThread(Socket fromClientSocket) {
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

