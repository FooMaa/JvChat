package org.foomaa.jvchat.settings;

import java.util.Base64;
import java.util.Objects;


public class JvMainSettings {
    private static JvMainSettings instance;

    private JvMainSettings() {}

    static JvMainSettings getInstance() {
        if (instance == null) {
            instance = new JvMainSettings();
        }
        return instance;
    }

    // PROFILE
    public enum TypeProfiles {
        TESTS("tests"),
        USERS("users"),
        SERVERS("servers");

        private final String name;

        TypeProfiles(String newName) {
            name = newName;
        }

        @Override
        public final String toString() {
            return name;
        }
    }

    private TypeProfiles PROFILE;

    public void setProfile(TypeProfiles profile) {
        if (PROFILE != profile) {
            PROFILE = profile;
        }
    }

    public TypeProfiles getProfile() {
        return PROFILE;
    }

    // NETWORK
    private int port = 4004;
    private String ip = "";
    private int quantityConnections = 1000;

    public void setIp(String newIp) {
        if (!Objects.equals(ip, newIp)) {
            ip = newIp;
        }
    }

    public void setPort(int newPort) {
        if (port != newPort) {
            port = newPort;
        }
    }

    public void setQuantityConnections(int newQuantityConnections) {
        if (quantityConnections != newQuantityConnections) {
            quantityConnections = newQuantityConnections;
        }
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getQuantityConnections() {
        return quantityConnections;
    }

    // DATABASE
    private final String dbUrl = "jdbc:postgresql://127.0.0.1:5432/chat";
    private final String dbUser = "jvchat";
    private final String magicStringDb = new String(Base64.getDecoder().decode("MTExMQ==".getBytes()));

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser () {
        return dbUser;
    }

    public String getMagicStringDb() {
        return magicStringDb;
    }

    // EMAIL
    private final String emailAddress = "jvchat.foomaa@mail.ru";
    private final String magicStringEmail =  new String(Base64.getDecoder().decode("bmlzeGtrMEZjTThXYVQxY1U3Qk4=".getBytes()));

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getMagicStringEmail() {
        return magicStringEmail;
    }
}