package org.foomaa.jvchat.signals;

import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class SignalFactory {
    private final ObjectProvider<Signal> eventObjectProvider;

    @Builder
    SignalFactory(ObjectProvider<Signal> eventObjectProvider) {
        this.eventObjectProvider = Objects.requireNonNull(eventObjectProvider, "eventObjectProvider is mandatory");
    }

    public Signal create() {
        return eventObjectProvider.getObject();
    }
}
