package org.foomaa.jvchat.messages;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DefinesMessagesTest {
    @Test
    void shouldReturnCorrectEnumForValidValue() {
        assertThat(DefinesMessages.TypeMessage.getTypeMsg(0)).isEqualTo(DefinesMessages.TypeMessage.EntryRequest);

        assertThat(DefinesMessages.TypeMessage.getTypeMsg(27)).isEqualTo(DefinesMessages.TypeMessage.MessagesLoadReply);
    }

    @Test
    void shouldReturnNullForUnknownTypeMessage() {
        assertThat(DefinesMessages.TypeMessage.getTypeMsg(999)).isNull();
    }

    @Test
    void shouldCompareTypeMessageCorrectly() {
        DefinesMessages.TypeMessage msg = DefinesMessages.TypeMessage.TextMessageSendUserToServer;

        assertThat(msg.compare(18)).isTrue();
        assertThat(msg.compare(99)).isFalse();
    }

    @Test
    void shouldReturnCorrectErrorEnum() {
        int value = DefinesMessages.TypeErrorRegistration.NoError.getValue();

        assertThat(DefinesMessages.TypeErrorRegistration.getTypeError(value))
                .isEqualTo(DefinesMessages.TypeErrorRegistration.NoError);
    }

    @Test
    void shouldReturnDefaultNoErrorForUnknownValue() {
        assertThat(DefinesMessages.TypeErrorRegistration.getTypeError(999))
                .isEqualTo(DefinesMessages.TypeErrorRegistration.NoError);
    }

    @Test
    void shouldCompareTypeErrorCorrectly() {
        DefinesMessages.TypeErrorRegistration err = DefinesMessages.TypeErrorRegistration.Email;

        assertThat(err.compare(err.getValue())).isTrue();
        assertThat(err.compare(-1)).isFalse();
    }
}
