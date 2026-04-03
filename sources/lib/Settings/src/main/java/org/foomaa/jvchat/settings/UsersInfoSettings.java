package org.foomaa.jvchat.settings;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersInfoSettings {
    private String login = "";
    private UUID uuid = null;
    private String ipRemoteServer;
    private int portRemoteServer = 4004;

    @Builder
    UsersInfoSettings() {}
}
