package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.settings.JvGetterSettings;
import org.foomaa.jvchat.globaldefines.JvMainChatsGlobalDefines;
import org.foomaa.jvchat.tools.JvGetterTools;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;


public class JvRectChatMainChatUI extends JPanel {
    private final String nickName;
    private final String shortLastMessage;
    private final String lastMessageSender;
    private final String timeLastMessage;
    private final JvMainChatsGlobalDefines.TypeStatusMessage statusMessage;
    private JvMainChatsGlobalDefines.TypeStatusOnline statusOnline;
    private String lastOnlineDateTime;
    private final String nameForLabelOnline;

    JvRectChatMainChatUI(String newNickName,
                         String newShortLastMessage,
                         String newLastMessageSender,
                         String newTimeLastMessage,
                         JvMainChatsGlobalDefines.TypeStatusMessage newStatusMessage) {
        nickName = newNickName;
        shortLastMessage = newShortLastMessage;
        lastMessageSender = newLastMessageSender;
        timeLastMessage = newTimeLastMessage;
        statusMessage = newStatusMessage;

        statusOnline = JvMainChatsGlobalDefines.TypeStatusOnline.Offline;
        lastOnlineDateTime = "";
        nameForLabelOnline = "onlineLabel";

        makeChatBox();
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

        JLabel lastMessageLabel = new JLabel(createLastMessageString());
        lastMessageLabel.setFont(new Font("Times", Font.PLAIN,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.014)));

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridy = gridyNum;
        gbc.insets = new Insets(1, 5, 1, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.WEST;
        add(lastMessageLabel, gbc);

        JLabel timeLabel = new JLabel(timeLastMessage);
        timeLabel.setFont(new Font("Times", Font.PLAIN,
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.014)));

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.gridx = GridBagConstraints.RELATIVE;
        gbc.gridy = gridyNum;
        gbc.insets = new Insets(1, 5, 1, 5);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(timeLabel, gbc);

        setStatusFront();
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    private void setStatusFront() {
        switch (statusMessage) {
            case Error -> setBackground(new Color(254,196,200));
            case Sent -> setBackground(new Color(254,252,190));
            case Delivered -> setBackground(new Color(181,252,250));
            case Read -> setBackground(new Color(191,254,188));
        }
    }

    private String createLastMessageString() {
        String currentLogin = JvGetterSettings.getInstance().getBeanUsersInfoSettings().getLogin();

        if (Objects.equals(lastMessageSender, currentLogin)) {
            return "Вы:" + shortLastMessage;
        }

        getComponents();
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
        for (Component component : getComponents()) {
            if (Objects.equals(component.getName(), nameForLabelOnline)) {
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
                return new Color(50,254,50);
            }
        }
        return null;
    }

    private String getStatusOnlineText() {
        String result = "";

        switch (statusOnline) {
            case Error -> result = "Error status online";
            case Offline -> {
                String lastOnlineProcessed = createLastOnlineText();
                if (lastOnlineProcessed != null && !Objects.equals(lastOnlineProcessed, "")) {
                    result = "Offline (" + lastOnlineProcessed + ")";
                } else {
                    result = "Offline";
                }
            }
            case Online -> result = "Online";
        }

        return result;
    }

    private String createLastOnlineText() {
        if (lastOnlineDateTime == null || Objects.equals(lastOnlineDateTime, "")) {
            JvLog.write(JvLog.TypeLog.Error, "Ошибка при попытке парсинга времени последнего онлайна");
            return "";
        }

        int normalizeCount = 3;
        String timestampString = JvGetterTools.getInstance()
                .getBeanMainTools().normalizeMillisecond(lastOnlineDateTime, normalizeCount);

        if (timestampString == null) {
            JvLog.write(JvLog.TypeLog.Error, "Не получилось нормализовать дату и время к нужному формату");
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(timestampString, formatter);
        } catch (DateTimeParseException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Ошибка при попытке парсинга времени последнего онлайна");
            return "";
        }

        String result = "";
        Duration duration = Duration.between(localDateTime, LocalDateTime.now());

        if (duration.toDays() < 1) {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
            result = localDateTime.format(formatter);
        } else if (duration.toDays() == 1) {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
            result = "Вчера " + localDateTime.format(formatter);
        } else {
            formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
            result = localDateTime.format(formatter);
        }

        return result;
    }
}
