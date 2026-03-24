package org.foomaa.jvchat.uicomponents.mainchat;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Profile("users")
public class FindTextFieldMainChatUIFactory {
    private final ObjectProvider<FindTextFieldMainChatUI> findTextFieldMainChatUIObjectProvider;

    FindTextFieldMainChatUIFactory(ObjectProvider<FindTextFieldMainChatUI> findTextFieldMainChatUIObjectProvider) {
        this.findTextFieldMainChatUIObjectProvider = findTextFieldMainChatUIObjectProvider;
    }

    public FindTextFieldMainChatUI create(String defaultText) {
        FindTextFieldMainChatUI findTextFieldMainChatUI = findTextFieldMainChatUIObjectProvider.getObject();
        findTextFieldMainChatUI.setDefaultText(defaultText);
        return findTextFieldMainChatUI;
    }
}
