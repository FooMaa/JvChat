package org.foomaa.jvchat.settings;


public class JvUISettings {
    JvUISettings() {
        quantityMessagesLoad = 30;
    }

    private final int quantityMessagesLoad;

    public int getQuantityMessagesLoad() {
        return quantityMessagesLoad;
    }
}
