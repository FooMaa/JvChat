package org.foomaa.jvchat.uicomponents.mainchat;

import org.springframework.context.annotation.*;


@Configuration
public class JvMainChatUiComponentsSpringConfig {
    public enum NameBeans {
        BeanMainChatMainFrame("beanMainChatMainFrame"),
        BeanMainChatScrollPanelChats("beanMainChatScrollPanelChats"),
        BeanMainChatScrollPanelMessages("beanMainChatScrollPanelMessages"),
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

    @Bean(name = "beanMainChatScrollPanelChats")
    @Lazy
    @Scope("singleton")
    public JvMainChatScrollPanelChats beanMainChatScrollPanelChats() {
        return JvMainChatScrollPanelChats.getInstance();
    }

    @Bean(name = "beanMainChatScrollPanelMessages")
    @Lazy
    @Scope("singleton")
    public JvMainChatScrollPanelMessages beanMainChatScrollPanelMessages() {
        return JvMainChatScrollPanelMessages.getInstance();
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