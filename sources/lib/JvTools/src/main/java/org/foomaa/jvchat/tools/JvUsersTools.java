package org.foomaa.jvchat.tools;

import java.util.regex.Pattern;


public class JvUsersTools {
    private static JvUsersTools instance;

    private JvUsersTools() {}

    static JvUsersTools getInstance() {
        if (instance == null) {
            instance = new JvUsersTools();
        }
        return instance;
    }

    public boolean validateInputEmail(String param) {
        Pattern regex = Pattern.compile(
                "^(?=.{1,64}@)[A-Za-z0-9+_-]+(\\.[A-Za-z0-9+_-]+)*@"
                        + "[^-][A-Za-z0-9+-]+(\\.[A-Za-z0-9+-]+)*(\\.[A-Za-z]{2,})$");
        if (param.isEmpty()) {
            return true;
        }
        return regex.matcher(param).matches();
    }
}
