package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.ctrl.JvChatsCtrl;
import org.springframework.context.annotation.*;


@Configuration
public class JvMainChatUIComponentsSpringConfig {
    public enum NameBeans {
        BeanMainFrameMainChatUI("beanMainFrameMainChatUI"),
        BeanScrollPanelChatsMainChatUI("beanScrollPanelChatsMainChatUI"),
        BeanScrollPanelMessagesMainChatUI("beanScrollPanelMessagesMainChatUI"),
        BeanRectMessageMainChatUI("beanRectMessageMainChatUI"),
        BeanRectChatMainChatUI("beanRectChatMainChatUI"),
        BeanSendButtonMainChatUI("beanSendButtonMainChatUI"),
        BeanSendingTextAreaScrollMainChatUI("beanSendingTextAreaScrollMainChatUI"),
        BeanPanelSendingMessageMainChatUI("beanPanelSendingMessageMainChatUI"),
        BeanFindTextFieldMainChatUI("beanFindTextFieldMainChatUI");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanMainFrameMainChatUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMainFrameMainChatUI beanMainFrameMainChatUI() {
        return JvMainFrameMainChatUI.getInstance();
    }

    @Bean(name = "beanScrollPanelChatsMainChatUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvScrollPanelChatsMainChatUI beanScrollPanelChatsMainChatUI() {
        return JvScrollPanelChatsMainChatUI.getInstance();
    }

    @Bean(name = "beanScrollPanelMessagesMainChatUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvScrollPanelMessagesMainChatUI beanScrollPanelMessagesMainChatUI() {
        return JvScrollPanelMessagesMainChatUI.getInstance();
    }

    @Bean(name = "beanRectMessageMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvRectMessageMainChatUI beanRectMessageMainChatUI() {
        return new JvRectMessageMainChatUI();
    }

    @Bean(name = "beanRectChatMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvRectChatMainChatUI beanRectChatMainChatUI(String nickName,
                                                       String shortLastMessage,
                                                       String lastMessageSender,
                                                       String timeLastMessage,
                                                       String timeLastOnline,
                                                       JvChatsCtrl.TypeStatusMessage statusMessage,
                                                       JvChatsCtrl.TypeStatusOnline statusOnline) {
        return new JvRectChatMainChatUI(nickName,
                shortLastMessage,
                lastMessageSender,
                timeLastMessage,
                timeLastOnline,
                statusMessage,
                statusOnline);
    }

    @Bean(name = "beanSendButtonMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvSendButtonMainChatUI beanSendButtonMainChatUI(String text) {
        return JvSendButtonMainChatUI.getInstance(text);
    }

    @Bean(name = "beanSendingTextAreaScrollMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvSendingTextAreaScrollMainChatUI beanSendingTextAreaScrollMainChatUI() {
        return JvSendingTextAreaScrollMainChatUI.getInstance();
    }

    @Bean(name = "beanPanelSendingMessageMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvPanelSendingMessageMainChatUI beanPanelSendingMessageMainChatUI() {
        return JvPanelSendingMessageMainChatUI.getInstance();
    }

    @Bean(name = "beanFindTextFieldMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvFindTextFieldMainChatUI beanFindTextFieldMainChatUI(String text) {
        return JvFindTextFieldMainChatUI.getInstance(text);
    }
}