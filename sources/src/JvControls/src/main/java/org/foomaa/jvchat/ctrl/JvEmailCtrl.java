package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.network.JvEmailProcessor;
import org.foomaa.jvchat.settings.JvMainSettings;

public class JvEmailCtrl {
    private static JvEmailCtrl instance;
    private static JvEmailProcessor email;

    private JvEmailCtrl() {
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            email = JvEmailProcessor.getInstance();
        }
    }

    public static JvEmailCtrl getInstance() {
        if (instance == null) {
            instance = new JvEmailCtrl();
        }
        return instance;
    }

}
