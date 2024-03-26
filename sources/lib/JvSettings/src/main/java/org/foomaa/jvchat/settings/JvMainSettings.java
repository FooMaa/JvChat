package org.foomaa.jvchat.settings;

import java.util.Base64;
import java.util.Objects;

public class JvMainSettings {
    // PROFILE
    public enum TypeProfiles {
        TESTS("tests"),
        USERS("users"),
        SERVERS("servers");
        private final String name;

        TypeProfiles(final String text) {
            this.name = text;
        }
        @Override
        public String toString() {
            return name;
        }
    }
    private static TypeProfiles PROFILE;

    public static void setProfile(TypeProfiles profile) {
        if (PROFILE != profile) {
            PROFILE = profile;
        }
    }

    public static TypeProfiles getProfile() {
        return PROFILE;
    }

    // NETWORK
    private static int port = 4004;
    private static String ip = "";
    private static int quantityConnections = 1000;

    public static void setIp(String newIp) {
        if (!Objects.equals(ip, newIp)) {
            ip = newIp;
        }
    }

    public static void setPort(int newPort) {
        if (port != newPort) {
            port = newPort;
        }
    }

    public static void setQuantityConnections(int newQuantityConnections) {
        if (quantityConnections != newQuantityConnections) {
            quantityConnections = newQuantityConnections;
        }
    }

    public static String getIp() {
        return ip;
    }

    public static int getPort() {
        return port;
    }

    public static int getQuantityConnections() {
        return quantityConnections;
    }

    // DATABASE
    private static final String dbUrl = "jdbc:postgresql://127.0.0.1:5432/chat";
    private static final String dbUser = "jvchat";
    private static final String magicStringDb = new String(Base64.getDecoder().decode("MTExMQ==".getBytes()));

    public static String getDbUrl() {
        return dbUrl;
    }

    public static String getDbUser () {
        return dbUser;
    }

    public static String getMagicStringDb() {
        return magicStringDb;
    }

    // EMAIL
    private static final String emailAddress = "jvchat.foomaa@mail.ru";
    private static final String magicStringEmail =  new String(Base64.getDecoder().decode("bmlzeGtrMEZjTThXYVQxY1U3Qk4=".getBytes()));

    public static String getEmailAddress() {
        return emailAddress;
    }

    public static String getMagicStringEmail() {
        return magicStringEmail;
    }
}