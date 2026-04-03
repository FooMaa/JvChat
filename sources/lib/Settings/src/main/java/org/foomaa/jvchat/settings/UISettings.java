package org.foomaa.jvchat.settings;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UISettings {
    private final int quantityMessagesLoad;

    @Builder
    UISettings() {
        quantityMessagesLoad = 30;
    }
}
