package org.foomaa.jvchat.uilinks;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class ErrorStartUILinkFactory {
    private final ObjectProvider<ErrorStartUILink> errorStartUILinkObjectProvider;

    @Builder
    ErrorStartUILinkFactory(ObjectProvider<ErrorStartUILink> errorStartUILinkObjectProvider) {
        this.errorStartUILinkObjectProvider = errorStartUILinkObjectProvider;
    }

    public ErrorStartUILink create(String message) {
        ErrorStartUILink errorStartUILink = errorStartUILinkObjectProvider.getObject();
        errorStartUILink.show(message);
        return errorStartUILink;
    }
}
