package org.foomaa.jvchat.signals;

import lombok.Builder;

public class SignalFactory {
    @Builder
    SignalFactory() {}

    public <T> Signal<T> create() {
        return new Signal<>();
    }
}
