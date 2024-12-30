package org.foomaa.jvchat.settings;

import java.util.Objects;
import java.util.UUID;


public class JvUsersInfoSettings {
    JvUsersInfoSettings() {}

    private String login = "";
    private UUID uuid = null;

    public void setLogin(String newLogin) {
        if (!Objects.equals(login, newLogin)) {
            login = newLogin;
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getLogin() {
        return login;
    }

    public void setUuid(UUID newUuid) {
        if (!uuid.equals(newUuid)) {
            uuid = newUuid;
        }
    }


    private String ipRemoteServer;

    private int portRemoteServer = 4004;

    public String getIpRemoteServer() {
        return ipRemoteServer;
    }

    public void setIpRemoteServer(String newIPRemoteServer) {
        if (!Objects.equals(ipRemoteServer, newIPRemoteServer)) {
            ipRemoteServer = newIPRemoteServer;
        }
    }

    public int getPortRemoteServer() {
        return portRemoteServer;
    }

    public void setPortRemoteServer(int newPortRemoteServer) {
        if (!Objects.equals(portRemoteServer, newPortRemoteServer)) {
            portRemoteServer = newPortRemoteServer;
        }
    }
}