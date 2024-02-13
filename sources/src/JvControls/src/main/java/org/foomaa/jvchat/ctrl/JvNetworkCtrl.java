package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.settings.JvMainSettings;

import org.foomaa.jvchat.network.JvServersSocket;
import org.foomaa.jvchat.network.JvUsers;

import java.io.IOException;

public class JvNetworkCtrl {
    private static JvNetworkCtrl instance;

    private JvNetworkCtrl() throws IOException {
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            JvServersSocket.getInstance();
        } else if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            JvUsers.getInstance();
        }
    }

    public static JvNetworkCtrl getInstance() throws IOException {
        if(instance == null){
            instance = new JvNetworkCtrl();
        }
        return instance;
    }

}
