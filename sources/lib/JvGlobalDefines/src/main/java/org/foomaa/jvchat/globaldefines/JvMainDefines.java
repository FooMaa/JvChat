package org.foomaa.jvchat.globaldefines;

public class JvMainDefines {
    private static JvMainDefines instance;

    public final String NAME_PROJECT = "JvChat";

    private JvMainDefines() {}

    public static JvMainDefines getInstance() {
        if (instance == null) {
            instance = new JvMainDefines();
        }
        return instance;
    }
}
