package org.foomaa.jvchat.network;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.foomaa.jvchat.settings.UsersInfoSettings;

@ExtendWith(MockitoExtension.class)
class UsersSocketTest {
    @Mock
    private UsersInfoSettings usersInfoSettingsMock;

    private ServerSocket mockServer;
    private UsersSocket usersSocket;

    @BeforeEach
    void setUp() throws IOException {
        mockServer = new ServerSocket(0);

        lenient().when(usersInfoSettingsMock.getIpRemoteServer()).thenReturn("127.0.0.1");
        lenient().when(usersInfoSettingsMock.getPortRemoteServer()).thenReturn(mockServer.getLocalPort());

        usersSocket =
                UsersSocket.builder().usersInfoSettings(usersInfoSettingsMock).build();
    }

    @AfterEach
    void tearDown() throws IOException {
        if (usersSocket != null && usersSocket.getCurrentSocket() != null) {
            usersSocket.getCurrentSocket().close();
        }

        if (mockServer != null) {
            mockServer.close();
        }
    }

    @Test
    void builderShouldThrowExceptionWhenSettingsNull() {
        assertThrows(
                NullPointerException.class,
                () -> UsersSocket.builder().usersInfoSettings(null).build());
    }

    @Test
    void startShouldConnectSuccessfullyWhenServerIsUp() throws IOException {
        usersSocket.start();
        Socket socket = usersSocket.getCurrentSocket();

        assertNotNull(socket);
        assertTrue(socket.isConnected(), "The socket must be connected");
        assertFalse(socket.isClosed(), "The socket must not be closed");
        assertEquals(mockServer.getLocalPort(), socket.getPort(), "The connection port must match");
    }

    @Test
    void startShouldThrowExceptionWhenServerIsDown() throws IOException {
        mockServer.close();

        assertThrows(
                IOException.class,
                () -> usersSocket.start(),
                "A conclusion should be thrown if connection is not possible");
    }

    @Test
    void startShouldRespectTimeoutWhenIpIsUnreachable() {
        when(usersInfoSettingsMock.getIpRemoteServer()).thenReturn("10.255.255.1");

        assertThrows(IOException.class, () -> usersSocket.start());
    }

    @Test
    void getCurrentSocketShouldReturnSameInstance() throws IOException {
        usersSocket.start();
        Socket s1 = usersSocket.getCurrentSocket();
        Socket s2 = usersSocket.getCurrentSocket();

        assertSame(s1, s2, "The method must return the same static instance");
    }
}
