package org.foomaa.jvchat.uicomponents.mainchat;

import java.awt.*;
import java.util.Objects;
import java.util.UUID;

import javax.swing.*;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.ctrl.ChatsCtrl;
import org.foomaa.jvchat.ctrl.MessagesDialogCtrl;
import org.foomaa.jvchat.structobjects.MessageStructObject;

@Slf4j
public class PanelSendingMessageMainChatUI extends JPanel {
    private final SendingTextAreaScrollMainChatUI sendingTextAreaScroll;
    private final JButton sendButton;
    private final ScrollPanelChatsMainChatUI scrollPanelChats;
    private final ScrollPanelMessagesMainChatUI scrollPanelMessages;
    private final MessagesDialogCtrl messagesDialogCtrl;
    private final ChatsCtrl chatsCtrl;

    @Builder
    PanelSendingMessageMainChatUI(ScrollPanelChatsMainChatUI scrollPanelChats,
            ScrollPanelMessagesMainChatUI scrollPanelMessages, MessagesDialogCtrl messagesDialogCtrl,
            SendButtonMainChatUIFactory sendButtonMainChatUIFactory,
            SendingTextAreaScrollMainChatUIFactory sendingTextAreaScrollMainChatUIFactory, ChatsCtrl chatsCtrl) {
        Objects.requireNonNull(sendingTextAreaScrollMainChatUIFactory,
                "sendingTextAreaScrollMainChatUIFactory is mandatory");
        Objects.requireNonNull(sendButtonMainChatUIFactory, "sendButtonMainChatUIFactory is mandatory");

        this.scrollPanelChats = Objects.requireNonNull(scrollPanelChats, "scrollPanelChats is mandatory");
        this.scrollPanelMessages = Objects.requireNonNull(scrollPanelMessages, "scrollPanelMessages is mandatory");
        this.messagesDialogCtrl = Objects.requireNonNull(messagesDialogCtrl, "messagesDialogCtrl is mandatory");
        this.chatsCtrl = Objects.requireNonNull(chatsCtrl, "chatsCtrl is mandatory");

        sendingTextAreaScroll = sendingTextAreaScrollMainChatUIFactory.create();
        sendButton = sendButtonMainChatUIFactory.create("Send");

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
            MessageStructObject messageObj = messagesDialogCtrl.createAndSendMessage(text);
            if (messageObj == null) {
                log.error("Не создано сообщение для отправки, не отправлено...");
                return;
            }
            scrollPanelMessages.addMessage(messageObj);
        }
    }

    private void updateComponentsAfterSending() {
        UUID selectedUuid = messagesDialogCtrl.getCurrentActiveChatUuid();
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
