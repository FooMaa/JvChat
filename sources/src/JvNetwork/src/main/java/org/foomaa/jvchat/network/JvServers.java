package org.foomaa.jvchat.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.net.DatagramSocket;
import java.net.InetAddress;


public class JvServers {
    private static JvServers instance;
    private static final int port = 80;

    private JvServers() {
        System.out.println("Server is started");
        try {
            ServerSocket servSocket = new ServerSocket(port, 1000,InetAddress.getByName("192.168.83.83"));
            while (true) {
                // Получив соединение начинаем работать с сокетом
                Socket fromClientSocket = servSocket.accept();
                // Стартуем новый поток для обработки запроса клиента
                new SocketThread(fromClientSocket).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static JvServers getInstance() throws IOException {
        if(instance == null){
            instance = new JvServers();
        }
        return instance;
    }
}

class SocketThread extends Thread
{
    private Socket fromClientSocket;

    public SocketThread(Socket fromClientSocket) {
        this.fromClientSocket = fromClientSocket;
    }

    @Override
    public void run() {
        // Автоматически будут закрыты все ресурсы
        try (Socket localSocket = fromClientSocket;
             PrintWriter pw = new PrintWriter(localSocket.getOutputStream(), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(localSocket.getInputStream()))) {

            // Читаем сообщения от клиента до тех пор пока он не скажет "bye"
            String str;
            while ((str = br.readLine()) != null) {
                // Печатаем сообщение
                System.out.println("The message: " + str);
                // Сравниваем с "bye" и если это так - выходим из цикла и закрываем соединение
                if (str.equals("bye")) {
                    // Тоже говорим клиенту "bye" и выходим из цикла
                    pw.println("bye");
                    break;
                } else {
                    // Посылаем клиенту ответ
                    str = "Server returns " + str;
                    pw.println(str);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

