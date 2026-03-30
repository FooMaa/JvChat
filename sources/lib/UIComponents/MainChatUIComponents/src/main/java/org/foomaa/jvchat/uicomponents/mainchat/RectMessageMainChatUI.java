package org.foomaa.jvchat.uicomponents.mainchat;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import lombok.Builder;

import org.foomaa.jvchat.ctrl.MessagesDialogCtrl;
import org.foomaa.jvchat.globaldefines.MainChatsGlobalDefines;
import org.foomaa.jvchat.settings.DisplaySettings;
import org.foomaa.jvchat.structobjects.MessageStructObject;

public class RectMessageMainChatUI extends JTextArea {
    private MainChatsGlobalDefines.TypeStatusMessage statusMessage;
    private String textMessage;
    private LocalDateTime timestamp;
    private UUID uuidMessage;

    private final DisplaySettings displaySettings;
    private final MessagesDialogCtrl messagesDialogCtrl;
    private final ScrollPanelMessagesMainChatUI scrollPanelMessages;

    @Builder
    RectMessageMainChatUI(DisplaySettings displaySettings, MessagesDialogCtrl messagesDialogCtrl,
            ScrollPanelMessagesMainChatUI scrollPanelMessages) {
        this.displaySettings = Objects.requireNonNull(displaySettings,
                "displaySettings is mandatory");
        this.messagesDialogCtrl = Objects.requireNonNull(messagesDialogCtrl,
                "messagesDialogCtrl is mandatory");
        this.scrollPanelMessages = Objects.requireNonNull(scrollPanelMessages,
                "scrollPanelMessages is mandatory");

        textMessage = "";
        statusMessage = MainChatsGlobalDefines.TypeStatusMessage.Error;
        timestamp = null;
        uuidMessage = null;

        settingLabel();
        addListenerToElements();
        setTextMessage();
    }

    public void setMessageObject(MessageStructObject messageObject) {
        textMessage = messageObject.getText();
        statusMessage = messageObject.getStatusMessage();
        timestamp = messageObject.getTimestamp();
        uuidMessage = messageObject.getUuid();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Рисуем фон облачка
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 16, 16);

        g2.setColor(new Color(16, 126, 16));

        int radius = 4;
        int diameter = radius * 2;
        int xRound = getSize().width - diameter - 5;
        int yRound = getSize().height - diameter - 5;
        int xRoundSecond = xRound - diameter - 1;

        // Рисуем кружки доставки
        if (statusMessage == MainChatsGlobalDefines.TypeStatusMessage.Delivered
                || statusMessage == MainChatsGlobalDefines.TypeStatusMessage.Read) {
            g2.fillOval(xRound, yRound, diameter, diameter);
        }
        if (statusMessage == MainChatsGlobalDefines.TypeStatusMessage.Read) {
            g2.fillOval(xRoundSecond, yRound, diameter, diameter);
        }

        String time = messagesDialogCtrl.getTimeFormattedMessage(timestamp);
        Font font = new Font("Times", Font.BOLD, displaySettings.getResizePixel(0.010));
        g2.setFont(font);
        FontMetrics fontMetrics = g2.getFontMetrics(font);

        int xTime = xRoundSecond - fontMetrics.stringWidth(time) - 5;
        int yTime = getHeight() - 5;

        g2.drawString(time, xTime, yTime);

        super.paintComponent(g);
    }

    private void settingLabel() {
        int borderSize = 10;

        setOpaque(false);
        setEditable(false);
        setBackground(new Color(173, 216, 230));
        setForeground(Color.BLACK);
        setBorder(new EmptyBorder(borderSize, borderSize, borderSize, borderSize));
        setFont(new Font("Times", Font.PLAIN, displaySettings.getResizePixel(0.014)));
        setLineWrap(true);
        setWrapStyleWord(true);

        resizeComponentLabel();
    }

    private void resizeComponentLabel() {
        int amendment = 20;
        int width = (scrollPanelMessages.getWidth() - amendment) / 2;
        setSize(new Dimension(width, getMinimumSize().height));
    }

    private void addListenerToElements() {
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                adjustHeight();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                adjustHeight();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                adjustHeight();
            }
        });
    }

    private void adjustHeight() {
        Dimension dimension = getSize();
        setSize(new Dimension(dimension.width, getMinimumSize().height));
        updateComponent();
    }

    private void setTextMessage() {
        setText(textMessage);
    }

    public void updateComponent() {
        revalidate();
        repaint();
    }

    public UUID getUuid() {
        return uuidMessage;
    }

    public void changeStatusMessage(MainChatsGlobalDefines.TypeStatusMessage newStatusMessage) {
        if (statusMessage != newStatusMessage) {
            statusMessage = newStatusMessage;
            updateComponent();
        }
    }
}
