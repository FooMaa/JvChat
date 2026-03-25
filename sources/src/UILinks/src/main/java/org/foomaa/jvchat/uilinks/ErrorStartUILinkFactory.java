package org.foomaa.jvchat.uilinks;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("users")
public class ErrorStartUILinkFactory {
    private final ObjectProvider<ErrorStartUILink> errorStartUILinkObjectProvider;

    ErrorStartUILinkFactory(ObjectProvider<ErrorStartUILink> errorStartUILinkObjectProvider) {
        this.errorStartUILinkObjectProvider = errorStartUILinkObjectProvider;
    }

    public ErrorStartUILink create(String message) {
        ErrorStartUILink errorStartUILink = errorStartUILinkObjectProvider.getObject();
        errorStartUILink.show(message);
        return errorStartUILink;
    }
}
