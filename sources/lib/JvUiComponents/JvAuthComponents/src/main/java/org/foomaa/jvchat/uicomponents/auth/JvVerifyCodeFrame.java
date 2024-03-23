package org.foomaa.jvchat.uicomponents.auth;

import org.foomaa.jvchat.ctrl.JvMessageCtrl;
import org.foomaa.jvchat.messages.JvSerializatorData;
import org.foomaa.jvchat.settings.JvDisplaySettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class JvVerifyCodeFrame extends JFrame {
    private final JPanel panel;
    private final JvAuthLabel tInfo;
    private final JvAuthTextField tCode;
    private final JvAuthLabel tErrorHelpInfo;
    private final JvAuthButton bSet;
    private final String email;

    public JvVerifyCodeFrame(String post) {
        super("ResetPasswordWindow");

        email = post;
        panel = new JPanel();
        tInfo = new JvAuthLabel("Введите код, отправленный на почту:");
        tCode = new JvAuthTextField("Код");
        tErrorHelpInfo = new JvAuthLabel("");
        tErrorHelpInfo.settingToError();
        bSet = new JvAuthButton("ОТПРАВИТЬ");

        makeFrameSetting();
        addListenerToElements();
        addGeneralSettingsToWidget();
    }

    private void makeFrameSetting() {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        int insX = JvDisplaySettings.
                getResizeFromDisplay(0.025,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        int gridyNum = 0;

        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.PAGE_START;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(JvDisplaySettings.getResizePixel(0.0125), 0, JvDisplaySettings.getResizePixel(0.0084), 0);
        gbc.gridy = gridyNum;
        panel.add(tInfo, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, JvDisplaySettings.getResizePixel(0.004), insX);
        gbc.gridy = gridyNum;
        panel.add(tCode, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, insX, JvDisplaySettings.getResizePixel(0.0084), insX);
        gbc.gridy = gridyNum;
        panel.add(tErrorHelpInfo, gbc);
        gridyNum++;

        gbc.fill = GridBagConstraints.PAGE_END;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, JvDisplaySettings.getResizePixel(0.017), 0);
        gbc.ipadx = JvDisplaySettings.getResizeFromDisplay(0.03,
                JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        gbc.ipady = JvDisplaySettings.getResizeFromDisplay(0.01,
                JvDisplaySettings.TypeOfDisplayBorder.HEIGHT);
        gbc.gridy = gridyNum;
        panel.add(bSet, gbc);

        getContentPane().add(panel);
    }

    private void addListenerToElements() {
        bSet.addActionListener(event -> {
            if (checkFields()) {
                JvMessageCtrl.getInstance().sendMessage(JvSerializatorData.TypeMessage.VerifyEmailRequest,
                        email, tCode.getInputText());
                waitRepeatServer();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new JvEntryFrame();
            }
        });
    }

    private boolean checkFields() {
        tCode.setNormalBorder();
        tErrorHelpInfo.setText("");

        Vector<String> fields = new Vector<>();

        if (Objects.equals(tCode.getInputText(), "") ||
                (tCode.getInputText().length() != 6 )) {
            tCode.setErrorBorder();
            fields.add("\"Код\"");
        }

        StringBuilder concatFields = new StringBuilder();
        if (!fields.isEmpty()) {
            for (int i = 0; i < fields.size(); i++) {
                concatFields.append(fields.elementAt(i)).append(", ");
            }
            concatFields = new StringBuilder(concatFields.substring(0, concatFields.length() - 2));
            if (fields.size() == 1) {
                tErrorHelpInfo.setText(String.format("Поле %s должно быть заполнено и содержать отправленный код", concatFields));
            } else {
                tErrorHelpInfo.setText(String.format("Поля %s должны быть заполнены и содержать отправленный код", concatFields));
            }
            return false;
        }
        return true;
    }

    private void addGeneralSettingsToWidget() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("ВОССТАНОВЛЕНИЕ ПАРОЛЯ");
        setSize(JvDisplaySettings.getResizeFromDisplay(0.3,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvDisplaySettings.getResizeFromDisplay(0.25,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        toFront();
        setVisible(true);
        requestFocus();
    }

    private void closeWindow() {
        setVisible(false);
        dispose();
        new JvEntryFrame();
    }

    private void waitRepeatServer() {
        setEnabled(false);
        while (JvMessageCtrl.getInstance().getVerifyEmailRequestFlag()
                == JvMessageCtrl.TypeFlags.DEFAULT) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException exception) {
                System.out.println("Не удалось ждать");
            }
        }
        if (JvMessageCtrl.getInstance().getVerifyEmailRequestFlag()
                == JvMessageCtrl.TypeFlags.TRUE) {
            closeWindow();
            System.out.println("Код верен");
        } else if (JvMessageCtrl.getInstance().getVerifyEmailRequestFlag()
                == JvMessageCtrl.TypeFlags.FALSE) {
            setEnabled(true);
            new JvAuthOptionPane("Код не верен. Введите код полученный по почте еще раз.", JvAuthOptionPane.TypeDlg.ERROR);
        }
    }
}
