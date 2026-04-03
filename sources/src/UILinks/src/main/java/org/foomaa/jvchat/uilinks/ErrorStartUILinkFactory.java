package org.foomaa.jvchat.uilinks;

import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class ErrorStartUILinkFactory {
    private final ObjectProvider<ErrorStartUILink> errorStartUILinkObjectProvider;

    @Builder
    ErrorStartUILinkFactory(ObjectProvider<ErrorStartUILink> errorStartUILinkObjectProvider) {
        this.errorStartUILinkObjectProvider =
                Objects.requireNonNull(errorStartUILinkObjectProvider, "errorStartUILinkObjectProvider is mandatory");
    }

    public void create(String message) {
        ErrorStartUILink errorStartUILink = errorStartUILinkObjectProvider.getObject();
        errorStartUILink.show(message);
    }
}
