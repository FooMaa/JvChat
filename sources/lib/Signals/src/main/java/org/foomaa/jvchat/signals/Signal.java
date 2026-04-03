package org.foomaa.jvchat.signals;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class Signal<T> {
    Signal() {}

    @FunctionalInterface
    public interface Connection {
        void disconnect();
    }

    private final List<Consumer<T>> slots = new CopyOnWriteArrayList<>();

    public Connection connect(Consumer<T> slot) {
        slots.add(slot);
        return () -> slots.remove(slot);
    }

    public Connection connect(Runnable slot) {
        Consumer<T> wrapper = (data) -> slot.run();
        slots.add(wrapper);
        return () -> slots.remove(wrapper);
    }

    public void emit(T data) {
        for (Consumer<T> slot : slots) {
            slot.accept(data);
        }
    }

    public void emit() {
        emit(null);
    }
}
