package org.foomaa.jvchat.uicomponents.mainchat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;


@Configuration
public class JvMainChatUiComponentsSpringConfig {
    public enum NameBeans {
        BeanMainChatMainFrame("beanMainChatMainFrame"),
        BeanMainChatScrollPaneChats("beanMainChatScrollPaneChats"),
        BeanMainChatScrollPaneMessages("beanMainChatScrollPaneMessages"),
        BeanMainChatRectMessage("beanMainChatRectMessage"),
        BeanMainChatRectChat("beanMainChatRectChat");

        private final String value;

        NameBeans(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanMainChatMainFrame")
    @Lazy
    @Scope("singleton")
    public JvMainChatMainFrame beanMainChatMainFrame() {
        return JvMainChatMainFrame.getInstance();
    }

    @Bean(name = "beanMainChatScrollPaneChats")
    @Lazy
    @Scope("singleton")
    public JvMainChatScrollPaneChats beanMainChatScrollPaneChats() {
        return JvMainChatScrollPaneChats.getInstance();
    }

    @Bean(name = "beanMainChatScrollPaneMessages")
    @Lazy
    @Scope("singleton")
    public JvMainChatScrollPaneMessages beanMainChatScrollPaneMessages() {
        return JvMainChatScrollPaneMessages.getInstance();
    }

    @Bean(name = "beanMainChatRectMessage")
    @Lazy
    @Scope("prototype")
    public JvMainChatRectMessage beanMainChatRectMessage() {
        return new JvMainChatRectMessage();
    }

    @Bean(name = "beanMainChatRectChat")
    @Lazy
    @Scope("prototype")
    public JvMainChatRectChat beanMainChatRectChat() {
        return new JvMainChatRectChat();
    }
}