package org.foomaa.jvchat.settings;

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
        if (ip != newIp) {
            ip = newIp;
        }
    }

    public static void setPort(int newPort) {
        if (port != newPort) {
            port = newPort;
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
}
