package org.foomaa.jvchat.settings;

public class JvMainSettings {

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
}
