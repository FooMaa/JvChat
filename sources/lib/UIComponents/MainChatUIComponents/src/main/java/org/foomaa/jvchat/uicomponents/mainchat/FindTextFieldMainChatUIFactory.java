package org.foomaa.jvchat.uicomponents.mainchat;

import java.util.Objects;

import org.springframework.beans.factory.ObjectProvider;

import lombok.Builder;

public class FindTextFieldMainChatUIFactory {
    private final ObjectProvider<FindTextFieldMainChatUI> findTextFieldMainChatUIObjectProvider;

    @Builder
    FindTextFieldMainChatUIFactory(ObjectProvider<FindTextFieldMainChatUI> findTextFieldMainChatUIObjectProvider) {
        this.findTextFieldMainChatUIObjectProvider = Objects.requireNonNull(
                findTextFieldMainChatUIObjectProvider, "findTextFieldMainChatUIObjectProvider is mandatory");
    }

    public FindTextFieldMainChatUI create(String defaultText) {
        FindTextFieldMainChatUI findTextFieldMainChatUI = findTextFieldMainChatUIObjectProvider.getObject();
        findTextFieldMainChatUI.setDefaultText(defaultText);
        return findTextFieldMainChatUI;
    }
}
