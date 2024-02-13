package org.foomaa.jvchat.network;

import org.foomaa.jvchat.settings.JvMainSettings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class JvUsers {
    private static JvUsers instance;

    private JvUsers() throws IOException {
            int portNumber = 9999;
            // Подготавливаем строку для запроса - просто строка
            String str = "Тестовая строка для передачи";

            Socket socket = new Socket(JvMainSettings.ip, JvMainSettings.port);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Создать поток для записи символов в сокет
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            // Отправляем тестовую строку в сокет
            pw.println(str);

            // Входим в цикл чтения, что нам ответил сервер
            while ((str = br.readLine()) != null) {
                // Если пришел ответ “bye”, то заканчиваем цикл
                if (str.equals("bye")) {
                    break;
                }
                // Печатаем ответ от сервера на консоль для проверки
                System.out.println(str);
                // Посылаем ему "bye" для окончания "разговора"
                pw.println("bye");
            }

            br.close();
            pw.close();
//            socket.close();
        }

    public static JvUsers getInstance() throws IOException {
        if(instance == null){
            instance = new JvUsers();
        }
        return instance;
    }
}
