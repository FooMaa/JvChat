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

    public JvMainChatScrollPanelChats getBeanMainChatScrollPanelChats() {
        return context.getBean(JvMainChatUiComponentsSpringConfig.NameBeans.BeanMainChatScrollPanelChats.getValue(),
                JvMainChatScrollPanelChats.class);
    }

    public JvMainChatScrollPanelMessages getBeanMainChatScrollPanelMessages() {
        return context.getBean(JvMainChatUiComponentsSpringConfig.NameBeans.BeanMainChatScrollPanelMessages.getValue(),
                JvMainChatScrollPanelMessages.class);
    }

    public JvMainChatRectMessage getBeanMainChatRectMessage() {
        return context.getBean(JvMainChatUiComponentsSpringConfig.NameBeans.BeanMainChatRectMessage.getValue(),
                JvMainChatRectMessage.class);
    }

    public JvMainChatRectChat getBeanMainChatRectChat() {
        return context.getBean(JvMainChatUiComponentsSpringConfig.NameBeans.BeanMainChatRectChat.getValue(),
                JvMainChatRectChat.class);
    }

    public JvMainChatSendButton getBeanMainChatSendButton(String text) {
        return (JvMainChatSendButton) context.getBean(
                JvMainChatUiComponentsSpringConfig.NameBeans.BeanMainChatSendButton.getValue(),
                text);
    }

    public JvMainChatSendingTextAreaScroll getBeanMainChatSendingTextAreaScroll() {
        return context.getBean(JvMainChatUiComponentsSpringConfig.NameBeans.BeanMainChatSendingTextAreaScroll.getValue(),
                JvMainChatSendingTextAreaScroll.class);
    }
}