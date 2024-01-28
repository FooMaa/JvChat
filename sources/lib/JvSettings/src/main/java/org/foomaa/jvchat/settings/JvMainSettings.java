package org.foomaa.jvchat.settings;

public class JvMainSettings {

    public enum TypeProfiles {
        TESTS,
        USERS,
        SERVERS;
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
