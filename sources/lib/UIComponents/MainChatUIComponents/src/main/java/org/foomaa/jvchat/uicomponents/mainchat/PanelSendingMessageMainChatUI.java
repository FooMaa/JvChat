package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.UUID;

import org.foomaa.jvchat.ctrl.GetterControls;
import org.foomaa.jvchat.logger.Log;
import org.foomaa.jvchat.structobjects.MessageStructObject;


public class PanelSendingMessageMainChatUI extends JPanel {
    private final SendingTextAreaScrollMainChatUI sendingTextAreaScroll;
    private final JButton sendButton;

    PanelSendingMessageMainChatUI() {
        sendingTextAreaScroll = GetterMainChatUIComponents.getInstance().getBeanSendingTextAreaScrollMainChatUI();
        sendButton = GetterMainChatUIComponents.getInstance().getBeanSendButtonMainChatUI("Отправить");

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
            Log.write(Log.TypeLog.Error, "sendingTextAreaScroll.getText() вернул null");
            return;
        }

        if (!Objects.equals(text, "")) {
            MessageStructObject messageObj = GetterControls.getInstance().getBeanMessagesDialogCtrl().createAndSendMessage(text);
            if (messageObj == null) {
                Log.write(Log.TypeLog.Error, "Не создано сообщение для отправки, не отправлено...");
                return;
            }
            GetterMainChatUIComponents.getInstance().getBeanScrollPanelMessagesMainChatUI().addMessage(messageObj);
        }
    }

    private void updateComponentsAfterSending() {
        UUID selectedUuid = GetterControls.getInstance().getBeanMessagesDialogCtrl().getCurrentActiveChatUuid();
        MessageStructObject message = GetterControls.getInstance().getBeanChatsCtrl().getMessageObjectByUuidChat(selectedUuid);

        Box boxComponents = GetterMainChatUIComponents.getInstance().getBeanScrollPanelChatsMainChatUI().getBoxComponents();

        for (Component component : boxComponents.getComponents()) {
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
