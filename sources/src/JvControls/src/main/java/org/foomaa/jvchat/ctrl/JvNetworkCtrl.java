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
            thread = JvUsersSocket.getInstance().getCurrentThread();
        }
    }

    public static JvNetworkCtrl getInstance() throws IOException {
        if(instance == null){
            instance = new JvNetworkCtrl();
        }
        return instance;
    }


    public static void takeMessage(String message, Thread thr) {
            thread = thr;
            byte[] dataMsg = message.getBytes();
            JvMessageCtrl.takeMessage(dataMsg);
    }

    public static void setMessage(byte[] message) {
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            ((JvServersThread) thread).send(message);
        } else if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.USERS) {
            ((JvUsersThread) thread).send(message);;
        }
    }
}
