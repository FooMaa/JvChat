package org.foomaa.jvchat.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Objects;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.settings.UsersInfoSettings;

@Slf4j
public class UsersSocket {
    private static Socket socketUsers;
    private final UsersInfoSettings usersInfoSettings;

    @Builder
    private UsersSocket(UsersInfoSettings usersInfoSettings) {
        this.usersInfoSettings = Objects.requireNonNull(usersInfoSettings,
                "usersInfoSettings is mandatory");
    }

    public void start() throws IOException {
        socketUsers = new Socket();
        socketUsers.connect(new InetSocketAddress(usersInfoSettings.getIpRemoteServer(),
                usersInfoSettings.getPortRemoteServer()), 4000);
        closeSocketWhenKill();
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
