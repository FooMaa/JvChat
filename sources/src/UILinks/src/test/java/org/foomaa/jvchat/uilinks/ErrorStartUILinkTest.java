package org.foomaa.jvchat.uilinks;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.foomaa.jvchat.uicomponents.auth.OptionPaneAuthUI;
import org.foomaa.jvchat.uicomponents.auth.OptionPaneAuthUIFactory;

@ExtendWith(MockitoExtension.class)
class ErrorStartUILinkTest {
    @Mock
    private OptionPaneAuthUIFactory factory;

    @Mock
    private OptionPaneAuthUI ui;

    @InjectMocks
    private ErrorStartUILink errorStartUILink;

    @Test
    void shouldShowErrorMessage() {
        String message = "Something went wrong";

        when(factory.create()).thenReturn(ui);

        errorStartUILink.show(message);

        verify(factory).create();
        verify(ui).show(message, OptionPaneAuthUI.TypeDlg.ERROR);
    }

    @Test
    void shouldThrowWhenFactoryIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> ErrorStartUILink.builder().optionPaneAuthUIFactory(null).build());
    }
}
