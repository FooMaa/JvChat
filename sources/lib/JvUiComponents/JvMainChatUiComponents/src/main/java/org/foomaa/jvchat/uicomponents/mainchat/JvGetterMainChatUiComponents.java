package org.foomaa.jvchat.uicomponents.mainchat;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JvGetterMainChatUiComponents {
    private static JvGetterMainChatUiComponents instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterMainChatUiComponents() {
        context = new AnnotationConfigApplicationContext(
                JvMainChatUiComponentsSpringConfig.class);
    }

    public static JvGetterMainChatUiComponents getInstance() {
        if (instance == null) {
            instance = new JvGetterMainChatUiComponents();
        }
        return instance;
    }

    public JvMainChatMainFrame getBeanMainChatMainFrame() {
        return context.getBean(JvMainChatUiComponentsSpringConfig.NameBeans.BeanMainChatMainFrame.getValue(),
                JvMainChatMainFrame.class);
    }

    public JvMainChatScrollPaneChats getBeanMainChatScrollPaneChats() {
        return context.getBean(JvMainChatUiComponentsSpringConfig.NameBeans.BeanMainChatScrollPaneChats.getValue(),
                JvMainChatScrollPaneChats.class);
    }

    public JvMainChatScrollPaneMessages getBeanMainChatScrollPaneMessages() {
        return context.getBean(JvMainChatUiComponentsSpringConfig.NameBeans.BeanMainChatScrollPaneMessages.getValue(),
                JvMainChatScrollPaneMessages.class);
    }

    public JvMainChatRectMessage getBeanMainChatRectMessage() {
        return context.getBean(JvMainChatUiComponentsSpringConfig.NameBeans.BeanMainChatRectMessage.getValue(),
                JvMainChatRectMessage.class);
    }

    public JvMainChatRectChat getBeanMainChatRectChat() {
        return context.getBean(JvMainChatUiComponentsSpringConfig.NameBeans.BeanMainChatRectChat.getValue(),
                JvMainChatRectChat.class);
    }
}