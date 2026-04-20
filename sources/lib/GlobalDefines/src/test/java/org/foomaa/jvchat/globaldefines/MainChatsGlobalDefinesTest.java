package org.foomaa.jvchat.globaldefines;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MainChatsGlobalDefinesTest {
    @Test
    void shouldReturnCorrectStatusMessageForValidValues() {
        assertThat(MainChatsGlobalDefines.TypeStatusMessage.getTypeStatusMessage(0))
                .isEqualTo(MainChatsGlobalDefines.TypeStatusMessage.Error);

        assertThat(MainChatsGlobalDefines.TypeStatusMessage.getTypeStatusMessage(1))
                .isEqualTo(MainChatsGlobalDefines.TypeStatusMessage.Sent);

        assertThat(MainChatsGlobalDefines.TypeStatusMessage.getTypeStatusMessage(2))
                .isEqualTo(MainChatsGlobalDefines.TypeStatusMessage.Delivered);

        assertThat(MainChatsGlobalDefines.TypeStatusMessage.getTypeStatusMessage(3))
                .isEqualTo(MainChatsGlobalDefines.TypeStatusMessage.Read);
    }

    @Test
    void shouldReturnErrorForUnknownStatusMessageValue() {
        assertThat(MainChatsGlobalDefines.TypeStatusMessage.getTypeStatusMessage(-1))
                .isEqualTo(MainChatsGlobalDefines.TypeStatusMessage.Error);

        assertThat(MainChatsGlobalDefines.TypeStatusMessage.getTypeStatusMessage(999))
                .isEqualTo(MainChatsGlobalDefines.TypeStatusMessage.Error);
    }

    @Test
    void shouldReturnCorrectStringRepresentation() {
        assertThat(MainChatsGlobalDefines.TypeStatusMessage.Sent.toString()).isEqualTo("1");

        assertThat(MainChatsGlobalDefines.TypeStatusMessage.Read.toString()).isEqualTo("3");
    }

    @Test
    void shouldContainCorrectRawValuesForStatusMessage() {
        assertThat(MainChatsGlobalDefines.TypeStatusMessage.Error.getValue()).isEqualTo(0);
        assertThat(MainChatsGlobalDefines.TypeStatusMessage.Sent.getValue()).isEqualTo(1);
        assertThat(MainChatsGlobalDefines.TypeStatusMessage.Delivered.getValue())
                .isEqualTo(2);
        assertThat(MainChatsGlobalDefines.TypeStatusMessage.Read.getValue()).isEqualTo(3);
    }

    @Test
    void shouldReturnCorrectStatusOnlineForValidValues() {
        assertThat(MainChatsGlobalDefines.TypeStatusOnline.getTypeStatusOnline(0))
                .isEqualTo(MainChatsGlobalDefines.TypeStatusOnline.Error);

        assertThat(MainChatsGlobalDefines.TypeStatusOnline.getTypeStatusOnline(1))
                .isEqualTo(MainChatsGlobalDefines.TypeStatusOnline.Offline);

        assertThat(MainChatsGlobalDefines.TypeStatusOnline.getTypeStatusOnline(2))
                .isEqualTo(MainChatsGlobalDefines.TypeStatusOnline.Online);
    }

    @Test
    void shouldReturnErrorForUnknownStatusOnlineValue() {
        assertThat(MainChatsGlobalDefines.TypeStatusOnline.getTypeStatusOnline(-10))
                .isEqualTo(MainChatsGlobalDefines.TypeStatusOnline.Error);

        assertThat(MainChatsGlobalDefines.TypeStatusOnline.getTypeStatusOnline(999))
                .isEqualTo(MainChatsGlobalDefines.TypeStatusOnline.Error);
    }

    @Test
    void shouldContainCorrectRawValuesForStatusOnline() {
        assertThat(MainChatsGlobalDefines.TypeStatusOnline.Error.getValue()).isEqualTo(0);
        assertThat(MainChatsGlobalDefines.TypeStatusOnline.Offline.getValue()).isEqualTo(1);
        assertThat(MainChatsGlobalDefines.TypeStatusOnline.Online.getValue()).isEqualTo(2);
    }
}
