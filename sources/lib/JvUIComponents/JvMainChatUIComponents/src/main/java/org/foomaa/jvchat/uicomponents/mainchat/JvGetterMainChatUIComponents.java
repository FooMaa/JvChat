package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.ctrl.JvChatsCtrl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JvGetterMainChatUIComponents {
    private static JvGetterMainChatUIComponents instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterMainChatUIComponents() {
        context = new AnnotationConfigApplicationContext(
                JvMainChatUIComponentsSpringConfig.class);
    }

    public static JvGetterMainChatUIComponents getInstance() {
        if (instance == null) {
            instance = new JvGetterMainChatUIComponents();
        }
        return instance;
    }

    public JvMainFrameMainChatUI getBeanMainFrameMainChatUI() {
        return context.getBean(JvMainChatUIComponentsSpringConfig.NameBeans.BeanMainFrameMainChatUI.getValue(),
                JvMainFrameMainChatUI.class);
    }

    public JvScrollPanelChatsMainChatUI getBeanScrollPanelChatsMainChatUI() {
        return context.getBean(JvMainChatUIComponentsSpringConfig.NameBeans.BeanScrollPanelChatsMainChatUI.getValue(),
                JvScrollPanelChatsMainChatUI.class);
    }

    public JvScrollPanelMessagesMainChatUI getBeanScrollPanelMessagesMainChatUI() {
        return context.getBean(JvMainChatUIComponentsSpringConfig.NameBeans.BeanScrollPanelMessagesMainChatUI.getValue(),
                JvScrollPanelMessagesMainChatUI.class);
    }

    public JvRectMessageMainChatUI getBeanRectMessageMainChatUI() {
        return context.getBean(JvMainChatUIComponentsSpringConfig.NameBeans.BeanRectMessageMainChatUI.getValue(),
                JvRectMessageMainChatUI.class);
    }

    public JvRectChatMainChatUI getBeanRectChatMainChatUI(String nickName,
                                                          String shortLastMessage,
                                                          String lastMessageSender,
                                                          String timeMessage,
                                                          String lastTimeOnline,
                                                          JvChatsCtrl.TypeStatusMessage statusMessage,
                                                          JvChatsCtrl.TypeStatusOnline statusOnline) {
        return (JvRectChatMainChatUI) context.getBean(JvMainChatUIComponentsSpringConfig.NameBeans.BeanRectChatMainChatUI.getValue(),
                nickName,
                shortLastMessage,
                lastMessageSender,
                timeMessage,
                lastTimeOnline,
                statusMessage,
                statusOnline);
    }

    public JvSendButtonMainChatUI getBeanSendButtonMainChatUI(String text) {
        return (JvSendButtonMainChatUI) context.getBean(
                JvMainChatUIComponentsSpringConfig.NameBeans.BeanSendButtonMainChatUI.getValue(),
                text);
    }

    public JvSendingTextAreaScrollMainChatUI getBeanSendingTextAreaScrollMainChatUI() {
        return context.getBean(JvMainChatUIComponentsSpringConfig.NameBeans.BeanSendingTextAreaScrollMainChatUI.getValue(),
                JvSendingTextAreaScrollMainChatUI.class);
    }

    public JvPanelSendingMessageMainChatUI getBeanPanelSendingMessageMainChatUI() {
        return context.getBean(JvMainChatUIComponentsSpringConfig.NameBeans.BeanPanelSendingMessageMainChatUI.getValue(),
                JvPanelSendingMessageMainChatUI.class);
    }

    public JvFindTextFieldMainChatUI getBeanFindTextFieldMainChatUI(String text) {
        return (JvFindTextFieldMainChatUI) context.getBean(
                JvMainChatUIComponentsSpringConfig.NameBeans.BeanFindTextFieldMainChatUI.getValue(),
                text);
    }
}