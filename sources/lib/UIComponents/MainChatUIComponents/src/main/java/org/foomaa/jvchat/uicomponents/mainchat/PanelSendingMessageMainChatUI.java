package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.ctrl.ChatsCtrl;
import org.foomaa.jvchat.ctrl.GetterControls;
import org.foomaa.jvchat.structobjects.MessageStructObject;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
@Profile("users")
@Slf4j
public class PanelSendingMessageMainChatUI extends JPanel {
    private final SendingTextAreaScrollMainChatUI sendingTextAreaScroll;
    private final JButton sendButton;
    private final ScrollPanelChatsMainChatUI scrollPanelChats;
    private final ScrollPanelMessagesMainChatUI scrollPanelMessages;
    private final ObjectProvider<ChatsCtrl> chatsCtrlObjectProvider;

    PanelSendingMessageMainChatUI(ScrollPanelChatsMainChatUI scrollPanelChats,
                                  ScrollPanelMessagesMainChatUI scrollPanelMessages,
                                  ObjectProvider<SendButtonMainChatUI> sendButtonObjectProvider,
                                  ObjectProvider<SendingTextAreaScrollMainChatUI> sendingTextAreaScrollObjectProvider,
                                  ObjectProvider<ChatsCtrl> chatsCtrlObjectProvider) {
        this.scrollPanelChats = scrollPanelChats;
        this.scrollPanelMessages = scrollPanelMessages;
        this.chatsCtrlObjectProvider = chatsCtrlObjectProvider;

        sendingTextAreaScroll = sendingTextAreaScrollObjectProvider.getObject();
        sendButton = sendButtonObjectProvider.getObject("Отправить");

        settingPanel();
        addListenerToElements();
    }

    private void settingPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int gridxNum = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridxNum;
        add(sendingTextAreaScroll, gbc);
        gridxNum++;

        gbc.weightx = 0.25;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridxNum;
        add(sendButton, gbc);
    }

    private void addListenerToElements() {
        sendButton.addActionListener(event -> processSendingMessage());
    }

    private void processSendingMessage() {
        sendMessageToServer();
        updateComponentsAfterSending();
    }

    private void sendMessageToServer() {
        String text = sendingTextAreaScroll.getText();
        if (text == null) {
            log.error("sendingTextAreaScroll.getText() вернул null");
            return;
        }

        if (!Objects.equals(text, "")) {
            MessageStructObject messageObj = GetterControls.getInstance().getBeanMessagesDialogCtrl().createAndSendMessage(text);
            if (messageObj == null) {
                log.error("Не создано сообщение для отправки, не отправлено...");
                return;
            }
            scrollPanelMessages.addMessage(messageObj);
        }
    }

    private void updateComponentsAfterSending() {
        UUID selectedUuid = GetterControls.getInstance().getBeanMessagesDialogCtrl().getCurrentActiveChatUuid();

        ChatsCtrl chatsCtrl = chatsCtrlObjectProvider.getIfAvailable();
        if (chatsCtrl == null) {
            log.error("charsCtrl is null");
            return;
        }

        MessageStructObject message = chatsCtrl.getMessageObjectByUuidChat(selectedUuid);

        Box boxComponents = scrollPanelChats.getBoxComponents();

        for (java.awt.Component component : boxComponents.getComponents()) {
            RectChatMainChatUI rectChatMainChatUI = (RectChatMainChatUI) component;
            UUID uuid = rectChatMainChatUI.getUuidChat();
            if (uuid.equals(selectedUuid)) {
                boxComponents.remove(rectChatMainChatUI);
                rectChatMainChatUI.updateLastMessage(message);
                boxComponents.add(rectChatMainChatUI, 0);
            }
        }

        sendingTextAreaScroll.clearText();
    }
}
