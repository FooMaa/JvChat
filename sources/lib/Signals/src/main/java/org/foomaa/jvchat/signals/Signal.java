package org.foomaa.jvchat.signals;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;

public class Signal {
    @Builder
    Signal() {}

    @FunctionalInterface
    public interface Slot {
        void accept(Object... args);
    }

    private final List<Slot> slots = new ArrayList<>();

    public Connection connect(Slot slot, Class<?>... types) {
        Slot wrapper = args -> {
            if (args.length != types.length) {
                throw new IllegalArgumentException(
                        "Неверное количество аргументов: expected " + types.length + ", got " + args.length);
            }
            for (int i = 0; i < types.length; i++) {
                if (!types[i].isInstance(args[i])) {
                    throw new IllegalArgumentException(
                            "Неверный тип аргумента #" + i + ": expected " + types[i] + ", got " + args[i].getClass());
                }
            }
            slot.accept(args);
        };
        slots.add(wrapper);
        return () -> slots.remove(wrapper);
    }

    public void emit(Object... args) {
        for (Slot slot : new ArrayList<>(slots)) {
            slot.accept(args);
        }
    }

    public interface Connection {
        void disconnect();
    }
}
