package org.foomaa.jvchat.settings;

import lombok.Builder;

public class UISettings {
    @Builder
    UISettings() {
        quantityMessagesLoad = 30;
    }

    private final int quantityMessagesLoad;

    public int getQuantityMessagesLoad() {
        return quantityMessagesLoad;
    }
}
