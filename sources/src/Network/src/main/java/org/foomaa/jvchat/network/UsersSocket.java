package org.foomaa.jvchat.network;

import org.foomaa.jvchat.settings.UsersInfoSettings;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;


@Component
@Lazy
@Profile("users")
@Slf4j
public class UsersSocket {
    private static Socket socketUsers;

    private UsersSocket(UsersInfoSettings usersInfoSettings) {
        try {
            socketUsers = new Socket();
            socketUsers.connect(new InetSocketAddress(usersInfoSettings.getIpRemoteServer(),
                    usersInfoSettings.getPortRemoteServer()), 4000);
            closeSocketWhenKill();
        } catch (IOException exception) {
            log.error("No connection.");
        }
    }

    private void closeSocketWhenKill() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
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