package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.structobjects.MessageStructObject;
import org.springframework.context.annotation.*;

import org.foomaa.jvchat.structobjects.ChatStructObject;


@Configuration
class MainChatUIComponentsSpringConfig {
    public enum NameBeans {
        BeanMainFrameMainChatUI("beanMainFrameMainChatUI"),
        BeanScrollPanelChatsMainChatUI("beanScrollPanelChatsMainChatUI"),
        BeanScrollPanelMessagesMainChatUI("beanScrollPanelMessagesMainChatUI"),
        BeanTitlePanelMainChatUI("beanTitlePanelMainChatUI"),
        BeanMainPanelMainChatUI("beanMainPanelMainChatUI"),
        BeanRectMessageMainChatUI("beanRectMessageMainChatUI"),
        BeanRectChatMainChatUI("beanRectChatMainChatUI"),
        BeanSendButtonMainChatUI("beanSendButtonMainChatUI"),
        BeanSendingTextAreaScrollMainChatUI("beanSendingTextAreaScrollMainChatUI"),
        BeanPanelSendingMessageMainChatUI("beanPanelSendingMessageMainChatUI"),
        BeanFindTextFieldMainChatUI("beanFindTextFieldMainChatUI"),
        BeanToolTipMainChatUI("beanToolTipMainChatUI");

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
    public MainFrameMainChatUI beanMainFrameMainChatUI() {
        return new MainFrameMainChatUI();
    }

//    @Bean(name = "beanScrollPanelChatsMainChatUI")
//    @Lazy
//    @Scope("singleton")
//    @SuppressWarnings("unused")
//    public ScrollPanelChatsMainChatUI beanScrollPanelChatsMainChatUI() {
//        return new ScrollPanelChatsMainChatUI();
//    }

    @Bean(name = "beanScrollPanelMessagesMainChatUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public ScrollPanelMessagesMainChatUI beanScrollPanelMessagesMainChatUI() {
        return new ScrollPanelMessagesMainChatUI();
    }

    @Bean(name = "beanTitlePanelMainChatUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public TitlePanelMainChatUI beanTitlePanelMainChatUI() {
        return new TitlePanelMainChatUI();
    }

    @Bean(name = "beanMainPanelMainChatUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public MainPanelMainChatUI beanMainPanelMainChatUI() {
        return new MainPanelMainChatUI();
    }

    @Bean(name = "beanRectMessageMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public RectMessageMainChatUI beanRectMessageMainChatUI(MessageStructObject messageObject) {
        return new RectMessageMainChatUI(messageObject);
    }

//    @Bean(name = "beanRectChatMainChatUI")
//    @Lazy
//    @Scope("prototype")
//    @SuppressWarnings("unused")
//    public RectChatMainChatUI beanRectChatMainChatUI(ChatStructObject chatObject) {
//        return new RectChatMainChatUI(chatObject);
//    }

    @Bean(name = "beanSendButtonMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public SendButtonMainChatUI beanSendButtonMainChatUI(String text) {
        return new SendButtonMainChatUI(text);
    }

    @Bean(name = "beanSendingTextAreaScrollMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public SendingTextAreaScrollMainChatUI beanSendingTextAreaScrollMainChatUI() {
        return new SendingTextAreaScrollMainChatUI();
    }

    @Bean(name = "beanPanelSendingMessageMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public PanelSendingMessageMainChatUI beanPanelSendingMessageMainChatUI() {
        return new PanelSendingMessageMainChatUI();
    }

    @Bean(name = "beanFindTextFieldMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public FindTextFieldMainChatUI beanFindTextFieldMainChatUI(String text) {
        return new FindTextFieldMainChatUI(text);
    }

    @Bean(name = "beanToolTipMainChatUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public ToolTipMainChatUI beanToolTipMainChatUI() {
        return new ToolTipMainChatUI();
    }
}