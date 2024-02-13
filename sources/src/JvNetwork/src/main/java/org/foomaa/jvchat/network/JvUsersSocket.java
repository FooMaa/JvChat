package org.foomaa.jvchat.network;

import org.foomaa.jvchat.settings.JvMainSettings;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class JvUsersSocket {
    private static JvUsersSocket instance;
    private static Socket socket;
    private JvUsersSocket() throws IOException {
        String str = "Тестовая строка для передачи";
        socket = new Socket();
        socket.connect(new InetSocketAddress(JvMainSettings.getIp(), JvMainSettings.getPort()), 4000);
        closeSocketWhenKill();

        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // Создать поток для записи символов в сокет
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        // Отправляем тестовую строку в сокет
        pw.println("Тестовая строка для передачи");



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

    private static void closeSocketWhenKill() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("Закрываем сокет ...");
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }
}
