package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.structobjects.MessageStructObject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.foomaa.jvchat.structobjects.ChatStructObject;


public class GetterMainChatUIComponents {
    private static GetterMainChatUIComponents instance;
    private final AnnotationConfigApplicationContext context;

    private GetterMainChatUIComponents() {
        context = new AnnotationConfigApplicationContext(
                MainChatUIComponentsSpringConfig.class);
    }

    public static GetterMainChatUIComponents getInstance() {
        if (instance == null) {
            instance = new GetterMainChatUIComponents();
        }
        return instance;
    }

    public MainFrameMainChatUI getBeanMainFrameMainChatUI() {
        return context.getBean(MainChatUIComponentsSpringConfig.NameBeans.BeanMainFrameMainChatUI.getValue(),
                MainFrameMainChatUI.class);
    }

    public ScrollPanelChatsMainChatUI getBeanScrollPanelChatsMainChatUI() {
        return null;
    }

    public TitlePanelMainChatUI getBeanTitlePanelMainChatUI() {
        return context.getBean(MainChatUIComponentsSpringConfig.NameBeans.BeanTitlePanelMainChatUI.getValue(),
                TitlePanelMainChatUI.class);
    }

    public MainPanelMainChatUI getBeanMainPanelMainChatUI() {
        return context.getBean(MainChatUIComponentsSpringConfig.NameBeans.BeanMainPanelMainChatUI.getValue(),
                MainPanelMainChatUI.class);
    }

    public ScrollPanelMessagesMainChatUI getBeanScrollPanelMessagesMainChatUI() {
        return context.getBean(MainChatUIComponentsSpringConfig.NameBeans.BeanScrollPanelMessagesMainChatUI.getValue(),
                ScrollPanelMessagesMainChatUI.class);
    }

    public RectMessageMainChatUI getBeanRectMessageMainChatUI(MessageStructObject messageObject) {
        return (RectMessageMainChatUI) context.getBean(MainChatUIComponentsSpringConfig.NameBeans.BeanRectMessageMainChatUI.getValue(),
                messageObject);
    }

    public RectChatMainChatUI getBeanRectChatMainChatUI(ChatStructObject chatObject) {
        return null;
    }

    public SendButtonMainChatUI getBeanSendButtonMainChatUI(String text) {
        return (SendButtonMainChatUI) context.getBean(
                MainChatUIComponentsSpringConfig.NameBeans.BeanSendButtonMainChatUI.getValue(),
                text);
    }

    public SendingTextAreaScrollMainChatUI getBeanSendingTextAreaScrollMainChatUI() {
        return context.getBean(MainChatUIComponentsSpringConfig.NameBeans.BeanSendingTextAreaScrollMainChatUI.getValue(),
                SendingTextAreaScrollMainChatUI.class);
    }

    public PanelSendingMessageMainChatUI getBeanPanelSendingMessageMainChatUI() {
        return context.getBean(MainChatUIComponentsSpringConfig.NameBeans.BeanPanelSendingMessageMainChatUI.getValue(),
                PanelSendingMessageMainChatUI.class);
    }

    public FindTextFieldMainChatUI getBeanFindTextFieldMainChatUI(String text) {
        return (FindTextFieldMainChatUI) context.getBean(
                MainChatUIComponentsSpringConfig.NameBeans.BeanFindTextFieldMainChatUI.getValue(),
                text);
    }

    public ToolTipMainChatUI getBeanToolTipMainChatUI() {
        return context.getBean(MainChatUIComponentsSpringConfig.NameBeans.BeanToolTipMainChatUI.getValue(),
                ToolTipMainChatUI.class);
    }
}