package org.foomaa.jvchat.settings;


public class JvUISettings {
    private static JvUISettings instance;
    private final int quantityMessagesLoad;

    private JvUISettings() {
        quantityMessagesLoad = 30;
    }

    static JvUISettings getInstance() {
        if (instance == null) {
            instance = new JvUISettings();
        }
        return instance;
    }

    public int getQuantityMessagesLoad() {
        return quantityMessagesLoad;
    }
}
