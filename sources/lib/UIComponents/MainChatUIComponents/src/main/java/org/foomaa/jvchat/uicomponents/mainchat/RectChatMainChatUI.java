package org.foomaa.jvchat.uicomponents.mainchat;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.UUID;

import javax.swing.*;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.foomaa.jvchat.ctrl.ChatsCtrl;
import org.foomaa.jvchat.ctrl.MessagesDialogCtrl;
import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;
import org.foomaa.jvchat.settings.DisplaySettings;
import org.foomaa.jvchat.settings.UsersInfoSettings;
import org.foomaa.jvchat.structobjects.ChatStructObject;
import org.foomaa.jvchat.structobjects.MessageStructObject;

@Slf4j
public class RectChatMainChatUI extends JPanel {
    private String nickName;
    private String shortLastMessage;
    private UUID lastMessageSender;
    private String timeLastMessage;
    private MainChatsGlobalDefines.TypeStatusMessage statusMessage;
    private MainChatsGlobalDefines.TypeStatusOnline statusOnline;

    @Getter
    private UUID uuidChat;

    @Getter
    private UUID uuidUser;

    private String lastOnlineDateTime;
    private final String nameForLabelOnline;
    private final String nameForLabelLastMessage;
    private final String nameForLabelTimeLastMessage;
    private boolean flagSelect;

    // DI ↓
    private final UsersInfoSettings usersInfoSettings;
    private final DisplaySettings displaySettings;
    private final MessagesDialogCtrl messagesDialogCtrl;
    private final ChatsCtrl chatsCtrl;

    @Builder
    RectChatMainChatUI(
            ChatStructObject chatObject,
            UsersInfoSettings usersInfoSettings,
            DisplaySettings displaySettings,
            MessagesDialogCtrl messagesDialogCtrl,
            ChatsCtrl chatsCtrl) {
        this.usersInfoSettings = Objects.requireNonNull(usersInfoSettings, "usersInfoSettings is mandatory");
        this.displaySettings = Objects.requireNonNull(displaySettings, "displaySettings is mandatory");
        this.messagesDialogCtrl = Objects.requireNonNull(messagesDialogCtrl, "messagesDialogCtrl is mandatory");
        this.chatsCtrl = Objects.requireNonNull(chatsCtrl, "chatsCtrl is mandatory");

        nickName = "";
        shortLastMessage = "";
        lastMessageSender = null;

        statusMessage = MainChatsGlobalDefines.TypeStatusMessage.Error;
        uuidChat = null;
        uuidUser = null;
        statusOnline = MainChatsGlobalDefines.TypeStatusOnline.Offline;
        lastOnlineDateTime = "";
        nameForLabelOnline = "onlineLabel";
        nameForLabelLastMessage = "lastMessageLabel";
        nameForLabelTimeLastMessage = "timeLastMessageLabel";

        flagSelect = false;

        installTimeLastMessage(chatObject);
        makeChatBox();
        addListenerToElements();
    }

    public void setChatObject(ChatStructObject chatObject) {
        nickName = chatObject.getUserChat().getLogin();
        shortLastMessage = chatObject.getLastMessage().getText();
        lastMessageSender = chatObject.getLastMessage().getUuidUserSender();

        statusMessage = chatObject.getLastMessage().getStatusMessage();
        uuidChat = chatObject.getUuid();
        uuidUser = chatObject.getUserChat().getUuid();
    }

    private void installTimeLastMessage(ChatStructObject chatObject) {
        timeLastMessage = chatsCtrl.getTimeFormattedLastMessage(
                chatObject.getLastMessage().getTimestamp());
    }

    private void makeChatBox() {
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());

        int gridyNum = 0;

        JLabel loginLabel = new JLabel(nickName);
        loginLabel.setFont(new Font("Times", Font.BOLD, displaySettings.getResizePixel(0.017)));

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridy = gridyNum;
        gbc.insets = new Insets(1, 5, 1, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        add(loginLabel, gbc);

        JLabel statusOnlineLabel = new JLabel(getStatusOnlineText());
        statusOnlineLabel.setName(nameForLabelOnline);
        statusOnlineLabel.setFont(new Font("Times", Font.PLAIN, displaySettings.getResizePixel(0.014)));
        statusOnlineLabel.setForeground(getStatusOnlineColor());

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = gridyNum;
        gbc.insets = new Insets(1, 5, 1, 5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(statusOnlineLabel, gbc);
        gridyNum++;

        boolean isBoldMessage = isBoldMessageByStatus();
        JLabel lastMessageLabel = new JLabel(createLastMessageString());
        lastMessageLabel.setName(nameForLabelLastMessage);
        lastMessageLabel.setFont(
                new Font("Times", (isBoldMessage ? Font.BOLD : Font.PLAIN), displaySettings.getResizePixel(0.014)));

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridy = gridyNum;
        gbc.insets = new Insets(1, 5, 1, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;
        add(lastMessageLabel, gbc);

        JLabel timeLastMessageLabel = new JLabel(timeLastMessage);
        timeLastMessageLabel.setName(nameForLabelTimeLastMessage);
        timeLastMessageLabel.setFont(
                new Font("Times", (isBoldMessage ? Font.BOLD : Font.PLAIN), displaySettings.getResizePixel(0.014)));

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = gridyNum;
        gbc.insets = new Insets(1, 5, 1, 5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(timeLastMessageLabel, gbc);

        setBackgroundColor();

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void setBackgroundColor() {
        if (flagSelect) {
            setBackground(new Color(246, 230, 125));
        } else {
            setBackground(new Color(181, 252, 250));
        }
    }

    private boolean isBoldMessageByStatus() {
        UUID currentUuid = usersInfoSettings.getUuid();

        if (lastMessageSender.equals(currentUuid)) {
            return false;
        }

        return statusMessage != MainChatsGlobalDefines.TypeStatusMessage.Read;
    }

    private String createLastMessageString() {
        UUID currentUuid = usersInfoSettings.getUuid();

        if (lastMessageSender.equals(currentUuid)) {
            return "Вы: " + shortLastMessage;
        }

        return shortLastMessage;
    }

    public void setStatusOnline(MainChatsGlobalDefines.TypeStatusOnline newStatusOnline) {
        if (statusOnline != newStatusOnline) {
            statusOnline = newStatusOnline;
        }
        updateOnlineStatus();
    }

    private void updateOnlineStatus() {
        JLabel statusOnlineLabel = (JLabel) findComponentStatusOnline();

        if (statusOnlineLabel == null) {
            log.error("Здесь nickNameLabel оказался null");
            return;
        }

        statusOnlineLabel.setText(getStatusOnlineText());
        statusOnlineLabel.setForeground(getStatusOnlineColor());
    }

    private java.awt.Component findComponentStatusOnline() {
        return findComponentByName(nameForLabelOnline);
    }

    private java.awt.Component findComponentLastMessage() {
        return findComponentByName(nameForLabelLastMessage);
    }

    private java.awt.Component findComponentTimeLastMessage() {
        return findComponentByName(nameForLabelTimeLastMessage);
    }

    private java.awt.Component findComponentByName(String nameComponent) {
        for (java.awt.Component component : getComponents()) {
            if (Objects.equals(component.getName(), nameComponent)) {
                return component;
            }
        }
        return null;
    }

    public void setLastOnlineDateTime(String newLastOnlineDateTime) {
        if (!Objects.equals(lastOnlineDateTime, newLastOnlineDateTime)) {
            lastOnlineDateTime = newLastOnlineDateTime;
        }
    }

    private Color getStatusOnlineColor() {
        switch (statusOnline) {
            case Error -> {
                return new Color(254, 50, 50);
            }
            case Offline -> {
                return new Color(0, 0, 0);
            }
            case Online -> {
                return new Color(14, 114, 14);
            }
        }
        return null;
    }

    private String getStatusOnlineText() {
        String result = "";

        switch (statusOnline) {
            case Error -> result = "Ошибка получения онлайна";
            case Offline -> {
                if (lastOnlineDateTime != null && !Objects.equals(lastOnlineDateTime, "")) {
                    result = "Был(а) " + lastOnlineDateTime;
                } else {
                    result = "Не в сети";
                }
            }
            case Online -> result = "В сети";
        }

        return result;
    }

    private void addListenerToElements() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                messagesDialogCtrl.setCurrentActiveChatUuid(uuidChat);
            }
        });
    }

    public void setFlagSelect(boolean newFlagSelect) {
        if (flagSelect != newFlagSelect) {
            flagSelect = newFlagSelect;
            setBackgroundColor();
        }
    }

    private void setBoldToLabelConditionally(JLabel label, boolean isBold) {
        label.setFont(new Font("Times", (isBold ? Font.BOLD : Font.PLAIN), displaySettings.getResizePixel(0.014)));
    }

    public void updateLastMessage(MessageStructObject message) {
        shortLastMessage = message.getText();

        timeLastMessage = chatsCtrl.getTimeFormattedLastMessage(message.getTimestamp());
        lastMessageSender = message.getUuidUserSender();
        statusMessage = message.getStatusMessage();

        JLabel labelMsg = (JLabel) findComponentLastMessage();
        JLabel labelTime = (JLabel) findComponentTimeLastMessage();

        labelMsg.setText(createLastMessageString());
        labelTime.setText(timeLastMessage);

        setBoldToLabelConditionally(labelMsg, isBoldMessageByStatus());
        setBoldToLabelConditionally(labelTime, isBoldMessageByStatus());
    }
}
