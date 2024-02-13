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
    public static int port = 4004;
    public static String ip = "192.168.83.83";

}
