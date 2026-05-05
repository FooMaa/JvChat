package org.foomaa.jvchat.uilinks;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.foomaa.jvchat.uicomponents.auth.MainFrameAuthUI;

@ExtendWith(MockitoExtension.class)
class StartAuthenticationUILinkTest {
    @Mock
    private MainFrameAuthUI mainFrameAuthUI;

    @InjectMocks
    private StartAuthenticationUILink startAuthenticationUILink;

    @Test
    void shouldOpenAuthenticationWindow() {
        startAuthenticationUILink.openFrame();

        verify(mainFrameAuthUI).openWindow();
    }

    @Test
    void shouldThrowExceptionWhenUiIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> StartAuthenticationUILink.builder().mainFrameAuthUI(null).build());
    }
}
