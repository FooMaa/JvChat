package org.foomaa.jvchat.settings;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("users")
public class UISettings {
    UISettings() {
        quantityMessagesLoad = 30;
    }

    private final int quantityMessagesLoad;

    public int getQuantityMessagesLoad() {
        return quantityMessagesLoad;
    }
}
