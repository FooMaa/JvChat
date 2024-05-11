package org.foomaa.jvchat.network;

import org.foomaa.jvchat.settings.JvMainSettings;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


@Component("beanUsersSocket")
@Scope("singleton")
@Profile("users")
public class JvUsersSocket {
    private static Socket socketUsers;

    private JvUsersSocket() {
        try {
            socketUsers = new Socket();
            socketUsers.connect(new InetSocketAddress(JvMainSettings.getIp(),
                    JvMainSettings.getPort()), 4000);
            closeSocketWhenKill();
        } catch (IOException exception) {
            System.out.println("No connection");
        }
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