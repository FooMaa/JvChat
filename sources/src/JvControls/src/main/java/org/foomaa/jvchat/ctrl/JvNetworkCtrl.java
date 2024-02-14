package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.network.JvServersThread;
import org.foomaa.jvchat.network.JvUsersThread;
import org.foomaa.jvchat.settings.JvMainSettings;

import org.foomaa.jvchat.network.JvServersSocket;
import org.foomaa.jvchat.network.JvUsersSocket;

import java.io.IOException;

public class JvNetworkCtrl {
    private static JvNetworkCtrl instance;
    private static Thread thread;

    private JvNetworkCtrl() throws IOException {
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            JvServersSocket.getInstance();
        } else if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            JvUsersSocket.getInstance();
        }
    }

    public static JvNetworkCtrl getInstance() throws IOException {
        if(instance == null){
            instance = new JvNetworkCtrl();
        }
        return instance;
    }


    public static void takeMessage(byte[] message, Thread thr) {
            thread = thread;
    }

    public static void putMessage(byte[] message) {
    }

}
