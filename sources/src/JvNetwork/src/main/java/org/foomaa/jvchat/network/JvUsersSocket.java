package org.foomaa.jvchat.network;

import org.foomaa.jvchat.settings.JvMainSettings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class JvUsersSocket {
    private static JvUsersSocket instance;
    private static Socket socketUsers;
    private BufferedReader repeatFromServer;
    private PrintWriter sendToServer;

    private JvUsersSocket() throws IOException {
        socketUsers = new Socket();
        socketUsers.connect(new InetSocketAddress(JvMainSettings.getIp(), JvMainSettings.getPort()), 4000);
        closeSocketWhenKill();

        repeatFromServer = new BufferedReader(new InputStreamReader(socketUsers.getInputStream()));
        sendToServer = new PrintWriter(socketUsers.getOutputStream(), true);
        sendToServer.println("Тестовая строка для передачи");

        String str;
        while ((str = repeatFromServer.readLine()) != null) {
            System.out.println(str);
        }


        // Входим в цикл чтения, что нам ответил сервер
//            while ((str = br.readLine()) != null) {
//                // Если пришел ответ “bye”, то заканчиваем цикл
//                if (str.equals("bye")) {
//                    break;
//                }
//                // Печатаем ответ от сервера на консоль для проверки
//                System.out.println(str);
//                // Посылаем ему "bye" для окончания "разговора"
//                pw.println("bye");
//            }

//            br.close();
//            pw.close();
//            socket.close();
    }

    public static JvUsersSocket getInstance() throws IOException {
        if(instance == null){
            instance = new JvUsersSocket();
        }
        return instance;
    }

    private void closeSocketWhenKill() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("Закрываем сокет ...");
                repeatFromServer.close();
                sendToServer.close();
                socketUsers.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }
}
