package org.foomaa.jvchat.network;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.foomaa.jvchat.settings.ServersInfoSettings;

@ExtendWith(MockitoExtension.class)
class ServersSocketTest {
    @Mock
    private ServersInfoSettings settingsMock;

    private ServersSocket serversSocket;

    @BeforeEach
    void setUp() {
        lenient().when(settingsMock.getPort()).thenReturn(0);
    }

    @AfterEach
    void tearDown() throws IOException {
        if (serversSocket != null
                && serversSocket.getSocketServers() != null
                && !serversSocket.getSocketServers().isClosed()) {
            serversSocket.getSocketServers().close();
        }
    }

    @Test
    void builderShouldThrowNullPointerExceptionWhenSettingsAreNull() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> ServersSocket.builder().serversInfoSettings(null).build());

        assertEquals("serversInfoSettings is mandatory", exception.getMessage());
    }

    @Test
    void startShouldCreateServerSocketWhenIpIsEmpty() throws IOException {
        when(settingsMock.getIp()).thenReturn("");

        serversSocket =
                ServersSocket.builder().serversInfoSettings(settingsMock).build();

        serversSocket.start();
        ServerSocket actualSocket = serversSocket.getSocketServers();

        assertNotNull(actualSocket, "Socket should be initialized");
        assertTrue(actualSocket.isBound(), "Socket should be bound to a port");
        assertFalse(actualSocket.isClosed(), "Socket should be open");

        assertTrue(actualSocket.getInetAddress().isAnyLocalAddress());
    }

    @Test
    void startShouldCreateServerSocketWhenIpIsProvided() throws IOException {
        String specificIp = "127.0.0.1";
        int backlog = 50;

        when(settingsMock.getIp()).thenReturn(specificIp);
        when(settingsMock.getQuantityConnections()).thenReturn(backlog);

        serversSocket =
                ServersSocket.builder().serversInfoSettings(settingsMock).build();

        serversSocket.start();
        ServerSocket actualSocket = serversSocket.getSocketServers();

        assertNotNull(actualSocket, "Socket should be initialized");
        assertTrue(actualSocket.isBound(), "Socket should be bound to a port");
        assertEquals(specificIp, actualSocket.getInetAddress().getHostAddress(), "IP should match the provided one");
    }

    @Test
    void getSocketServersShouldReturnSameInstance() throws IOException {
        when(settingsMock.getIp()).thenReturn("");
        serversSocket =
                ServersSocket.builder().serversInfoSettings(settingsMock).build();

        serversSocket.start();

        ServerSocket socket1 = serversSocket.getSocketServers();
        ServerSocket socket2 = serversSocket.getSocketServers();

        assertSame(socket1, socket2, "Getter should return the exact same static socket instance");
    }
}
