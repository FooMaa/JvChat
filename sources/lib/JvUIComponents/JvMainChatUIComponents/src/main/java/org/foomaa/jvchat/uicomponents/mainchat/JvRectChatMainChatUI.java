package org.foomaa.jvchat.uicomponents.mainchat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.UUID;

import org.foomaa.jvchat.ctrl.JvGetterControls;
import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.structobjects.JvChatStructObject;
import org.foomaa.jvchat.structobjects.JvMessageStructObject;


public class JvRectChatMainChatUI extends JPanel {
    private final String nickName;
    private String shortLastMessage;
    private String lastMessageSender;
    private String timeLastMessage;
    private JvMainChatsGlobalDefines.TypeStatusMessage statusMessage;
    private JvMainChatsGlobalDefines.TypeStatusOnline statusOnline;
    private final UUID uuidChat;
    private String lastOnlineDateTime;
    private final String nameForLabelOnline;
    private final String nameForLabelLastMessage;
    private final String nameForLabelTimeLastMessage;

    private boolean flagSelect;

    JvRectChatMainChatUI(JvChatStructObject chatObject) {
        nickName = chatObject.getUserChat().getLogin();
        shortLastMessage = chatObject.getLastMessage().getText();
        lastMessageSender = chatObject.getLastMessage().getLoginSender();
        timeLastMessage = JvGetterControls.getInstance().getBeanChatsCtrl()
                .getTimeFormattedLastMessage(chatObject.getLastMessage().getTimestamp());
        statusMessage = chatObject.getLastMessage().getStatusMessage();
        uuidChat = chatObject.getUuid();

        statusOnline = JvMainChatsGlobalDefines.TypeStatusOnline.Offline;
        lastOnlineDateTime = "";
        nameForLabelOnline = "onlineLabel";
        nameForLabelLastMessage = "lastMessageLabel";
        nameForLabelTimeLastMessage = "timeLastMessageLabel";

        flagSelect = false;

        makeChatBox();
        addListenerToElements();
    }

    public String getNickName() {
        return nickName;
    }

    private void makeChatBox() {
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());

        int gridyNum = 0;

        JLabel loginLabel = new JLabel(nickName);
        loginLabel.setFont(new Font("Times", Font.BOLD,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.017)));

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridy = gridyNum;
        gbc.insets = new Insets(1, 5, 1, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        add(loginLabel, gbc);

        JLabel statusOnlineLabel = new JLabel(getStatusOnlineText());
        statusOnlineLabel.setName(nameForLabelOnline);
        statusOnlineLabel.setFont(new Font("Times", Font.PLAIN,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.014)));
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
        lastMessageLabel.setFont(new Font("Times", (isBoldMessage ? Font.BOLD : Font.PLAIN),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.014)));

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridy = gridyNum;
        gbc.insets = new Insets(1, 5, 1, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;
        add(lastMessageLabel, gbc);

        JLabel timeLastMessageLabel = new JLabel(timeLastMessage);
        timeLastMessageLabel.setName(nameForLabelTimeLastMessage);
        timeLastMessageLabel.setFont(new Font("Times", (isBoldMessage ? Font.BOLD : Font.PLAIN),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.014)));

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
            setBackground(new Color(181,252,250));
        }
    }

    private boolean isBoldMessageByStatus() {
        String currentLogin = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getLogin();

        if (Objects.equals(lastMessageSender, currentLogin)) {
            return false;
        }

        return statusMessage != JvMainChatsGlobalDefines.TypeStatusMessage.Read;
    }

    private String createLastMessageString() {
        String currentLogin = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getLogin();

        if (Objects.equals(lastMessageSender, currentLogin)) {
            return "Вы: " + shortLastMessage;
        }

        return shortLastMessage;
    }

    public void setStatusOnline(JvMainChatsGlobalDefines.TypeStatusOnline newStatusOnline) {
        if (statusOnline != newStatusOnline) {
            statusOnline = newStatusOnline;
        }
        updateOnlineStatus();
    }

    private void updateOnlineStatus() {
        JLabel statusOnlineLabel = (JLabel) findComponentStatusOnline();

        if (statusOnlineLabel == null) {
            JvLog.write(JvLog.TypeLog.Error, "Здесь nickNameLabel оказался null");
            return;
        }

        statusOnlineLabel.setText(getStatusOnlineText());
        statusOnlineLabel.setForeground(getStatusOnlineColor());
    }

    private Component findComponentStatusOnline() {
        return findComponentByName(nameForLabelOnline);
    }

    private Component findComponentLastMessage() {
        return findComponentByName(nameForLabelLastMessage);
    }

    private Component findComponentTimeLastMessage() {
        return findComponentByName(nameForLabelTimeLastMessage);
    }

    private Component findComponentByName(String nameComponent) {
        for (Component component : getComponents()) {
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
                return new Color(254,50,50);
            }
            case Offline -> {
                return new Color(0,0,0);
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
                JvGetterControls.getInstance().getBeanMessagesDialogCtrl()
                        .setCurrentActiveChatUuid(uuidChat);
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
        label.setFont(new Font("Times", (isBold ? Font.BOLD : Font.PLAIN),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.014)));
    }

    public void updateLastMessage(JvMessageStructObject message) {
        shortLastMessage = message.getText();
        timeLastMessage = JvGetterControls.getInstance().getBeanChatsCtrl()
                .getTimeFormattedLastMessage(message.getTimestamp());
        lastMessageSender = message.getLoginSender();
        statusMessage = message.getStatusMessage();

        JLabel labelMsg = (JLabel) findComponentLastMessage();
        JLabel labelTime = (JLabel) findComponentTimeLastMessage();

        labelMsg.setText(createLastMessageString());
        labelTime.setText(timeLastMessage);

        setBoldToLabelConditionally(labelMsg, isBoldMessageByStatus());
        setBoldToLabelConditionally(labelTime, isBoldMessageByStatus());
    }
}
