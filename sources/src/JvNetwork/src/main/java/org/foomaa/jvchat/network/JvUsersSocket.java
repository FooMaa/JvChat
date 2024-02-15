package org.foomaa.jvchat.network;

import org.foomaa.jvchat.settings.JvMainSettings;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class JvUsersSocket {
    private static JvUsersSocket instance;
    private static Socket socketUsers;

    private JvUsersSocket() throws IOException {
        socketUsers = new Socket();
        socketUsers.connect(new InetSocketAddress(JvMainSettings.getIp(), JvMainSettings.getPort()), 4000);
        closeSocketWhenKill();
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
                socketUsers.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    public Socket getCurrentSocket() {
        return socketUsers;
    }
}
