package org.foomaa.jvchat.globaldefines;

public class JvMainGlobalDefines {
    private static JvMainGlobalDefines instance;

    public final String NAME_PROJECT = "JvChat";

    private JvMainGlobalDefines() {}

    public static JvMainGlobalDefines getInstance() {
        if (instance == null) {
            instance = new JvMainGlobalDefines();
        }
        return instance;
    }
}
