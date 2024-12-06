package org.foomaa.jvchat.settings;

import java.util.Objects;


public class JvUsersInfoSettings {
    JvUsersInfoSettings() {}

    private String login = "";

    public void setLogin(String newLogin) {
        if (!Objects.equals(login, newLogin)) {
            login = newLogin;
        }
    }

    public String getLogin() {
        return login;
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