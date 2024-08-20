package org.foomaa.jvchat.settings;

import java.util.Objects;


public class JvUserInfoSettings {
    private static JvUserInfoSettings instance;

    private JvUserInfoSettings() {}

    public static JvUserInfoSettings getInstance() {
        if (instance == null) {
            instance = new JvUserInfoSettings();
        }
        return instance;
    }

    private String login;

    public void setLogin(String newLogin) {
        if (!Objects.equals(login, newLogin)) {
            login = newLogin;
        }
    }

    public String getLogin() {
        return login;
    }
}