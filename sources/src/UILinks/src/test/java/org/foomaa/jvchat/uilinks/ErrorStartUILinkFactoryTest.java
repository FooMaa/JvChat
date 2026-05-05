package org.foomaa.jvchat.uilinks;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.ObjectProvider;

@ExtendWith(MockitoExtension.class)
class ErrorStartUILinkFactoryTest {
    @Mock
    private ObjectProvider<ErrorStartUILink> provider;

    @Mock
    private ErrorStartUILink link;

    @InjectMocks
    private ErrorStartUILinkFactory factory;

    @Test
    void shouldCreateLinkAndShowMessage() {
        String message = "error occurred";

        when(provider.getObject()).thenReturn(link);

        factory.create(message);

        verify(provider).getObject();
        verify(link).show(message);
    }

    @Test
    void shouldThrowExceptionWhenProviderIsNull() {
        assertThrows(NullPointerException.class, () -> ErrorStartUILinkFactory.builder()
                .errorStartUILinkObjectProvider(null)
                .build());
    }
}
