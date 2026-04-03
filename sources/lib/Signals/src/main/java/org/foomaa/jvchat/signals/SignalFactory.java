package org.foomaa.jvchat.signals;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class SignalFactory {
    @Builder
    SignalFactory(ObjectProvider<Signal<?>> signalObjectProvider) {}

    public <T> Signal<T> create() {
        return new Signal<>();
    }
}
