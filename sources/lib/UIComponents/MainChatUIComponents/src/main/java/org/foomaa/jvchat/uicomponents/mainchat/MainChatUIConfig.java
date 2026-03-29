package org.foomaa.jvchat.uicomponents.mainchat;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import org.foomaa.jvchat.ctrl.ChatsCtrl;
import org.foomaa.jvchat.ctrl.MessagesDialogCtrl;
import org.foomaa.jvchat.settings.DisplaySettings;

@Configuration
public class MainChatUIConfig {
    @Bean
    @Scope("prototype")
    @Profile("users")
    public FindTextFieldMainChatUI beanFindTextFieldMainChatUI(DisplaySettings displaySettings) {
        return FindTextFieldMainChatUI.builder().displaySettings(displaySettings).build();
    }

    @Bean
    @Profile("users")
    public FindTextFieldMainChatUIFactory beanFindTextFieldMainChatUIFactory(
            ObjectProvider<FindTextFieldMainChatUI> findTextFieldMainChatUIObjectProvider) {
        return FindTextFieldMainChatUIFactory.builder()
                .findTextFieldMainChatUIObjectProvider(findTextFieldMainChatUIObjectProvider)
                .build();
    }

    @Bean
    @Profile("users")
    public MainFrameMainChatUI beanMainFrameMainChatUI(DisplaySettings displaySettings,
            TitlePanelMainChatUI titlePanel, MainPanelMainChatUI mainPanel) {
        return MainFrameMainChatUI.builder().displaySettings(displaySettings).titlePanel(titlePanel)
                .mainPanel(mainPanel).build();
    }

    @Bean
    @Profile("users")
    public MainPanelMainChatUI beanMainPanelMainChatUI(ScrollPanelChatsMainChatUI scrollPanelChats,
            ScrollPanelMessagesMainChatUI scrollPanelMessages,
            PanelSendingMessageMainChatUIFactory panelSendingMessageFactory,
            FindTextFieldMainChatUIFactory findTextFieldMainChatUIFactory) {
        return MainPanelMainChatUI.builder().scrollPanelChats(scrollPanelChats)
                .scrollPanelMessages(scrollPanelMessages)
                .panelSendingMessageFactory(panelSendingMessageFactory)
                .findTextFieldMainChatUIFactory(findTextFieldMainChatUIFactory).build();
    }

    @Bean
    @Scope("prototype")
    @Profile("users")
    public PanelSendingMessageMainChatUI beanPanelSendingMessageMainChatUI(
            ScrollPanelChatsMainChatUI scrollPanelChats,
            ScrollPanelMessagesMainChatUI scrollPanelMessages,
            MessagesDialogCtrl messagesDialogCtrl,
            SendButtonMainChatUIFactory sendButtonMainChatUIFactory,
            SendingTextAreaScrollMainChatUIFactory sendingTextAreaScrollMainChatUIFactory,
            ChatsCtrl chatsCtrl) {
        return PanelSendingMessageMainChatUI.builder().scrollPanelChats(scrollPanelChats)
                .scrollPanelMessages(scrollPanelMessages).messagesDialogCtrl(messagesDialogCtrl)
                .sendButtonMainChatUIFactory(sendButtonMainChatUIFactory)
                .sendingTextAreaScrollMainChatUIFactory(sendingTextAreaScrollMainChatUIFactory)
                .chatsCtrl(chatsCtrl).build();
    }

    @Bean
    @Profile("users")
    public PanelSendingMessageMainChatUIFactory beanPanelSendingMessageMainChatUIFactory(ObjectProvider<PanelSendingMessageMainChatUI> panelSendingMessageObjectProvider) {
        return PanelSendingMessageMainChatUIFactory.builder().panelSendingMessageObjectProvider(panelSendingMessageObjectProvider).build();
    }






    @Bean(name = "beanScrollPanelChatsMainChatUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public ScrollPanelChatsMainChatUI beanScrollPanelChatsMainChatUI() {
        return new ScrollPanelChatsMainChatUI();
    }

    @Bean(name = "beanScrollPanelMessagesMainChatUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public ScrollPanelMessagesMainChatUI beanScrollPanelMessagesMainChatUI() {
        return new ScrollPanelMessagesMainChatUI();
    }

    @Bean(name = "beanTitlePanelMainChatUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public TitlePanelMainChatUI beanTitlePanelMainChatUI() {
        return new TitlePanelMainChatUI();
    }

    @Bean(name = "beanRectMessageMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public RectMessageMainChatUI beanRectMessageMainChatUI(MessageStructObject messageObject) {
        return new RectMessageMainChatUI(messageObject);
    }

    @Bean(name = "beanRectChatMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public RectChatMainChatUI beanRectChatMainChatUI(ChatStructObject chatObject) {
        return new RectChatMainChatUI(chatObject);
    }

    @Bean(name = "beanSendButtonMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public SendButtonMainChatUI beanSendButtonMainChatUI(String text) {
        return new SendButtonMainChatUI(text);
    }

    @Bean(name = "beanSendingTextAreaScrollMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public SendingTextAreaScrollMainChatUI beanSendingTextAreaScrollMainChatUI() {
        return new SendingTextAreaScrollMainChatUI();
    }

    @Bean(name = "beanPanelSendingMessageMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public PanelSendingMessageMainChatUI beanPanelSendingMessageMainChatUI() {
        return new PanelSendingMessageMainChatUI();
    }

    @Bean(name = "beanToolTipMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public ToolTipMainChatUI beanToolTipMainChatUI() {
        return new ToolTipMainChatUI();
    }
}
