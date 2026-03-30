package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.ctrl.MessagesDefinesCtrl;
import org.foomaa.jvchat.ctrl.SendMessagesCtrl;
import org.foomaa.jvchat.settings.UISettings;
import org.foomaa.jvchat.settings.UsersInfoSettings;
import org.foomaa.jvchat.structobjects.ChatStructObject;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import org.foomaa.jvchat.ctrl.ChatsCtrl;
import org.foomaa.jvchat.ctrl.MessagesDialogCtrl;
import org.foomaa.jvchat.settings.DisplaySettings;
import org.springframework.stereotype.Component;

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

    @Bean
    @Scope("prototype")
    @Profile("users")
    public RectChatMainChatUI beanRectChatMainChatUI(ChatStructObject chatObject, UsersInfoSettings usersInfoSettings,
                                                     DisplaySettings displaySettings, MessagesDialogCtrl messagesDialogCtrl,
                                                     ChatsCtrl chatsCtrl) {
        return RectChatMainChatUI.builder()
                .chatObject(chatObject)
                .usersInfoSettings(usersInfoSettings)
                .displaySettings(displaySettings)
                .messagesDialogCtrl(messagesDialogCtrl)
                .chatsCtrl(chatsCtrl)
                .build();
    }

    @Bean
    @Profile("users")
    public RectChatMainChatUIFactory beanRectChatMainChatUIFactory(ObjectProvider<RectChatMainChatUI> rectChatObjectProvider) {
        return RectChatMainChatUIFactory.builder().rectChatObjectProvider(rectChatObjectProvider).build();
    }

    @Bean
    @Scope("prototype")
    @Profile("users")
    public RectMessageMainChatUI beanRectMessageMainChatUI(DisplaySettings displaySettings, MessagesDialogCtrl messagesDialogCtrl,
                                                           ScrollPanelMessagesMainChatUI scrollPanelMessages) {
        return RectMessageMainChatUI.builder()
                .displaySettings(displaySettings)
                .messagesDialogCtrl(messagesDialogCtrl)
                .scrollPanelMessages(scrollPanelMessages)
                .build();
    }

    @Bean
    @Profile("users")
    public RectMessageMainChatUIFactory beanRectMessageMainChatUIFactory(ObjectProvider<RectMessageMainChatUI> rectMessageObjectProvider) {
        return RectMessageMainChatUIFactory.builder().rectMessageObjectProvider(rectMessageObjectProvider).build();
    }

    @Bean
    @Profile("users")
    public ScrollPanelChatsMainChatUI beanScrollPanelChatsMainChatUI(UsersInfoSettings usersInfoSettings, UISettings uiSettings,
                                                                     SendMessagesCtrl sendMessagesCtrl, MessagesDefinesCtrl messagesDefinesCtrl,
                                                                     RectChatMainChatUIFactory rectChatFactory,
                                                                     ChatsCtrl chatsCtrl) {
        return ScrollPanelChatsMainChatUI.builder()
                .usersInfoSettings(usersInfoSettings)
                .uiSettings(uiSettings)
                .sendMessagesCtrl(sendMessagesCtrl)
                .messagesDefinesCtrl(messagesDefinesCtrl)
                .rectChatFactory(rectChatFactory)
                .chatsCtrl(chatsCtrl)
                .build();
    }

    @Bean
    @Profile("users")
    public ScrollPanelMessagesMainChatUI beanScrollPanelMessagesMainChatUI(MessagesDefinesCtrl messagesDefinesCtrl,
                                                                           MessagesDialogCtrl messagesDialogCtrl,
                                                                           RectMessageMainChatUIFactory rectMessageMainChatUIFactory) {
        return ScrollPanelMessagesMainChatUI.builder().messagesDefinesCtrl(messagesDefinesCtrl).messagesDialogCtrl(messagesDialogCtrl).rectMessageMainChatUIFactory(rectMessageMainChatUIFactory).build();
    }

    @Bean
    @Scope("prototype")
    @Profile("users")
    public SendButtonMainChatUI beanSendButtonMainChatUI() {
        return SendButtonMainChatUI.builder().build();
    }










    @Bean(name = "beanTitlePanelMainChatUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public TitlePanelMainChatUI beanTitlePanelMainChatUI() {
        return new TitlePanelMainChatUI();
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
