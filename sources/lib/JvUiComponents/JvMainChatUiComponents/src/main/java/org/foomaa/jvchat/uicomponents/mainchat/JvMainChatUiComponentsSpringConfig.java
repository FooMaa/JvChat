package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.ctrl.JvChatsCtrl;
import org.springframework.context.annotation.*;


@Configuration
public class JvMainChatUiComponentsSpringConfig {
    public enum NameBeans {
        BeanMainChatMainFrame("beanMainChatMainFrame"),
        BeanMainChatScrollPanelChats("beanMainChatScrollPanelChats"),
        BeanMainChatScrollPanelMessages("beanMainChatScrollPanelMessages"),
        BeanMainChatRectMessage("beanMainChatRectMessage"),
        BeanMainChatRectChat("beanMainChatRectChat"),
        BeanMainChatSendButton("beanMainChatSendButton"),
        BeanMainChatSendingTextAreaScroll("beanMainChatSendingTextAreaScroll");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanMainChatMainFrame")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMainChatMainFrame beanMainChatMainFrame() {
        return JvMainChatMainFrame.getInstance();
    }

    @Bean(name = "beanMainChatScrollPanelChats")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMainChatScrollPanelChats beanMainChatScrollPanelChats() {
        return JvMainChatScrollPanelChats.getInstance();
    }

    @Bean(name = "beanMainChatScrollPanelMessages")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMainChatScrollPanelMessages beanMainChatScrollPanelMessages() {
        return JvMainChatScrollPanelMessages.getInstance();
    }

    @Bean(name = "beanMainChatRectMessage")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvMainChatRectMessage beanMainChatRectMessage() {
        return new JvMainChatRectMessage();
    }

    @Bean(name = "beanMainChatRectChat")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvMainChatRectChat beanMainChatRectChat(String nickName,
                                                   String shortLastMessage,
                                                   String lastMessageSender,
                                                   String time,
                                                   JvChatsCtrl.TypeStatusMessage status) {
        return new JvMainChatRectChat(nickName, shortLastMessage, lastMessageSender, time, status);
    }

    @Bean(name = "beanMainChatSendButton")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvMainChatSendButton beanMainChatSendButton(String text) {
        return new JvMainChatSendButton(text);
    }

    @Bean(name = "beanMainChatSendingTextAreaScroll")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvMainChatSendingTextAreaScroll beanMainChatSendingTextAreaScroll() {
        return new JvMainChatSendingTextAreaScroll();
    }
}