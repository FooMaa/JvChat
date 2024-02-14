package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.network.JvUsersThread;
import org.foomaa.jvchat.settings.JvMainSettings;

import org.foomaa.jvchat.network.JvServersSocket;
import org.foomaa.jvchat.network.JvUsersSocket;

import java.io.IOException;

public class JvNetworkCtrl {
    private static JvNetworkCtrl instance;
    private static JvUsersThread thread;

    private JvNetworkCtrl() throws IOException {
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            JvServersSocket.getInstance();
        } else if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
             thread =  JvUsersSocket.getInstance().getCurrentThread();
        }
    }

    public static JvNetworkCtrl getInstance() throws IOException {
        if(instance == null){
            instance = new JvNetworkCtrl();
        }
        return instance;
    }

    public static void takeMessage(byte[] message) {

    }

    public static void putMessage(byte[] message) {
        thread.send(message);
    }

}
