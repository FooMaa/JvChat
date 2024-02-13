package org.foomaa.jvchat.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class JvUsers {
    private static JvUsers instance;

    private JvUsers() throws IOException {
            // Определяем номер порта, на котором нас ожидает сервер для ответа
            int portNumber = 1777;
            // Подготавливаем строку для запроса - просто строка
            String str = "Тестовая строка для передачи";

            // Пишем, что стартовали клиент
            System.out.println("Client is started");

            // Открыть сокет (Socket) для обращения к локальному компьютеру
            // Сервер мы будем запускать на этом же компьютере
            // Это специальный класс для сетевого взаимодействия c клиентской стороны
            Socket socket = new Socket("127.0.0.1", portNumber);

            // Создать поток для чтения символов из сокета
            // Для этого надо открыть поток сокета - socket.getInputStream()
            // Потом преобразовать его в поток символов - new InputStreamReader
            // И уже потом сделать его читателем строк - BufferedReader
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
            socket.close();
        }

    public static JvUsers getInstance() throws IOException {
        if(instance == null){
            instance = new JvUsers();
        }
        return instance;
    }
}
