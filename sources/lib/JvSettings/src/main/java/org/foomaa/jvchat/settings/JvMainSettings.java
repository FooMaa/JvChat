package org.foomaa.jvchat.settings;


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
}