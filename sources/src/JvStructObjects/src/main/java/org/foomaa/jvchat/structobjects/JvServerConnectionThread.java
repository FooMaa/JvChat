package org.foomaa.jvchat.structobjects;

import java.util.UUID;

public class JvServerConnectionThread extends JvBaseStructObject {
    private final UUID uuid;
    private Thread thread;

    JvServerConnectionThread() {
        uuid = UUID.randomUUID();
        thread = null;
        commitProperties();
    }

    public void setThread(Thread newThread) {
        if (thread != newThread) {
            thread = newThread;
            commitProperties();
        }
    }

    public Thread getThread() {
        return thread;
    }

    public UUID getUuid() {
        return uuid;
    }
}
