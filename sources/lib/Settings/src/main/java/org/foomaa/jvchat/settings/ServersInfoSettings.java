package org.foomaa.jvchat.settings;

import java.util.Base64;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServersInfoSettings {
    @Builder
    ServersInfoSettings() {}

    // NETWORK
    private int port = 4004;
    private String ip = "";
    private int quantityConnections = 1000;

    // DATABASE
    private final String dbUrl = "jdbc:postgresql://127.0.0.1:5432/jvchat";
    private final String dbUser = "jvchat";
    private final String magicStringDb = new String(Base64.getDecoder().decode("MTExMQ==".getBytes()));

    // EMAIL
    private final String emailAddress = "jvchat.foomaa@mail.ru";
    // To find this password follow the link:
    // https://account.mail.ru/user/2-step-auth/passwords?back_url=https%3A%2F%2Fid.mail.ru%2Fsecurity
    private final String magicStringEmail =
            new String(Base64.getDecoder().decode("THhtZ2lUV0gwYW1ISlRyblI0SjM=".getBytes()));
}
