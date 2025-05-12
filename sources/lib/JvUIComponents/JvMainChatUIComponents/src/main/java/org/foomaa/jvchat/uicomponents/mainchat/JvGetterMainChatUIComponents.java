package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.structobjects.JvMessageStructObject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.foomaa.jvchat.structobjects.JvChatStructObject;


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

    public JvTitlePanelMainChatUI getBeanTitlePanelMainChatUI() {
        return context.getBean(JvMainChatUIComponentsSpringConfig.NameBeans.BeanTitlePanelMainChatUI.getValue(),
                JvTitlePanelMainChatUI.class);
    }

    public JvMainPanelMainChatUI getBeanMainPanelMainChatUI() {
        return context.getBean(JvMainChatUIComponentsSpringConfig.NameBeans.BeanMainPanelMainChatUI.getValue(),
                JvMainPanelMainChatUI.class);
    }

    public JvScrollPanelMessagesMainChatUI getBeanScrollPanelMessagesMainChatUI() {
        return context.getBean(JvMainChatUIComponentsSpringConfig.NameBeans.BeanScrollPanelMessagesMainChatUI.getValue(),
                JvScrollPanelMessagesMainChatUI.class);
    }

    public JvRectMessageMainChatUI getBeanRectMessageMainChatUI(JvMessageStructObject messageObject) {
        return (JvRectMessageMainChatUI) context.getBean(JvMainChatUIComponentsSpringConfig.NameBeans.BeanRectMessageMainChatUI.getValue(),
                messageObject);
    }

    public JvRectChatMainChatUI getBeanRectChatMainChatUI(JvChatStructObject chatObject) {
        return (JvRectChatMainChatUI) context.getBean(JvMainChatUIComponentsSpringConfig.NameBeans.BeanRectChatMainChatUI.getValue(),
                chatObject);
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

    public JvToolTipMainChatUI getBeanToolTipMainChatUI() {
        return context.getBean(JvMainChatUIComponentsSpringConfig.NameBeans.BeanToolTipMainChatUI.getValue(),
                JvToolTipMainChatUI.class);
    }
}