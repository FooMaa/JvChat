package org.foomaa.jvchat.globaldefines;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DbGlobalDefinesTest {
    @Test
    void shouldReturnCorrectEnumForValidValue() {
        DbGlobalDefines.LineKeys result = DbGlobalDefines.LineKeys.getTypeLineKey("login");

        assertThat(result).isEqualTo(DbGlobalDefines.LineKeys.Login);
    }

    @Test
    void shouldMapAllEnumValues() {
        for (DbGlobalDefines.LineKeys key : DbGlobalDefines.LineKeys.values()) {
            DbGlobalDefines.LineKeys result = DbGlobalDefines.LineKeys.getTypeLineKey(key.getValue());

            assertThat(result).isEqualTo(key);
        }
    }

    @Test
    void shouldReturnNullForUnknownValue() {
        DbGlobalDefines.LineKeys result = DbGlobalDefines.LineKeys.getTypeLineKey("unknown_value");

        assertThat(result).isNull();
    }

    @Test
    void shouldReturnNullForNullInput() {
        DbGlobalDefines.LineKeys result = DbGlobalDefines.LineKeys.getTypeLineKey(null);

        assertThat(result).isNull();
    }

    @Test
    void shouldContainCorrectRawValues() {
        assertThat(DbGlobalDefines.LineKeys.Login.getValue()).isEqualTo("login");
        assertThat(DbGlobalDefines.LineKeys.UuidUser.getValue()).isEqualTo("uuid_user");
        assertThat(DbGlobalDefines.LineKeys.TextMessage.getValue()).isEqualTo("text_message");
    }
}
