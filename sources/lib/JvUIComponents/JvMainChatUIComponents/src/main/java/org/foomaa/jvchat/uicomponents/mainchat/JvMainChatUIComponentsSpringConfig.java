package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.structobjects.JvMessageStructObject;
import org.springframework.context.annotation.*;

import org.foomaa.jvchat.structobjects.JvChatStructObject;


@Configuration
class JvMainChatUIComponentsSpringConfig {
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
    public JvRectMessageMainChatUI beanRectMessageMainChatUI(JvMessageStructObject messageObject) {
        return new JvRectMessageMainChatUI(messageObject);
    }

    @Bean(name = "beanRectChatMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvRectChatMainChatUI beanRectChatMainChatUI(JvChatStructObject chatObject) {
        return new JvRectChatMainChatUI(chatObject);
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