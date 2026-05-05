package org.foomaa.jvchat.network;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import org.foomaa.jvchat.settings.ServersInfoSettings;

@ExtendWith(MockitoExtension.class)
class EmailProcessorTest {
    @Mock
    private ServersInfoSettings serversInfoSettingsMock;

    @Mock
    private Session sessionMock;

    @Mock
    private Transport transportMock;

    private MockedStatic<Session> sessionStaticMock;

    @BeforeEach
    void setUp() throws MessagingException {
        lenient().when(serversInfoSettingsMock.getEmailAddress()).thenReturn("test@mail.ru");
        lenient().when(serversInfoSettingsMock.getMagicStringEmail()).thenReturn("secretPassword");

        sessionStaticMock = mockStatic(Session.class);
        sessionStaticMock
                .when(() -> Session.getDefaultInstance(any(Properties.class)))
                .thenReturn(sessionMock);

        lenient().when(sessionMock.getProperties()).thenReturn(new Properties());

        lenient().when(sessionMock.getTransport()).thenReturn(transportMock);
    }

    @AfterEach
    void tearDown() {
        if (sessionStaticMock != null) {
            sessionStaticMock.close();
        }
    }

    @Test
    void builderShouldThrowNullPointerExceptionWhenSettingsAreNull() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> EmailProcessor.builder().serversInfoSettings(null).build());

        assertEquals("serversInfoSettings is mandatory", exception.getMessage());
    }

    @Test
    void sendEmailShouldReturnTrueWhenEmailIsSentSuccessfully() throws MessagingException {
        EmailProcessor emailProcessor = EmailProcessor.builder()
                .serversInfoSettings(serversInfoSettingsMock)
                .build();

        boolean result = emailProcessor.sendEmail("recipient@example.com", "Hello from Test");

        assertTrue(result, "Expected sendEmail to return true on success");

        verify(transportMock).connect(eq("smtp.mail.ru"), eq(465), eq("test@mail.ru"), eq("secretPassword"));

        verify(transportMock).sendMessage(any(MimeMessage.class), any(Address[].class));
        verify(transportMock).close();
    }

    @Test
    void sendEmailShouldReturnFalseWhenMessagingExceptionOccurs() throws MessagingException {
        EmailProcessor emailProcessor = EmailProcessor.builder()
                .serversInfoSettings(serversInfoSettingsMock)
                .build();

        doThrow(new MessagingException("Mocked connection failure"))
                .when(transportMock)
                .connect(anyString(), anyInt(), anyString(), anyString());

        boolean result = emailProcessor.sendEmail("recipient@example.com", "Will not send");

        assertFalse(result, "Expected sendEmail to return false when exception is thrown");

        verify(transportMock, never()).sendMessage(any(Message.class), any(Address[].class));
        verify(transportMock, never()).close();
    }
}
