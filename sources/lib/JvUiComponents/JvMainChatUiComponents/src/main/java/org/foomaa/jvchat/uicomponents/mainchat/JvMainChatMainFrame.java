package org.foomaa.jvchat.uicomponents.mainchat;

import org.foomaa.jvchat.settings.JvDisplaySettings;
import org.foomaa.jvchat.settings.JvGetterSettings;

import javax.swing.*;
import java.awt.*;


public class JvMainChatMainFrame extends JFrame {
    private static JvMainChatMainFrame instance;
    private JvMainChatScrollPanelChats scrollPanelChats;
    private JvMainChatScrollPanelMessages scrollPanelMessages;

    private JvMainChatMainFrame() {
        super("MainChatWindow");

        makeFrameSetting();
        addListenerToElements();
        addGeneralSettingsToWidget();
    }

    public static JvMainChatMainFrame getInstance() {
        if (instance == null) {
            instance = new JvMainChatMainFrame();
        }
        return instance;
    }

    private void addGeneralSettingsToWidget() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("JvChat");
        setSize(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.585,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH),
                JvGetterSettings.getInstance().getBeanDisplaySettings().getResizeFromDisplay(0.5625,
                        JvDisplaySettings.TypeOfDisplayBorder.HEIGHT));
        setResizable(true);
        setLocationRelativeTo(null);
        toFront();
        setVisible(true);
        requestFocus();
    }

    private void makeFrameSetting() {
        JPanel base = new JPanel();
        base.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        int insX = JvGetterSettings.getInstance().getBeanDisplaySettings().
                getResizeFromDisplay(0.025,
                        JvDisplaySettings.TypeOfDisplayBorder.WIDTH);
        int gridxNum = 0;

        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        //gbc.insets = new Insets(JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0125), 0,
              //  JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.0084), 0);
        gbc.gridx = gridxNum;
        base.add(JvMainChatScrollPanelChats.getInstance(), gbc);
        gridxNum++;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        // gbc.insets = new Insets(0, insX,
        //        JvGetterSettings.getInstance().getBeanDisplaySettings().getResizePixel(0.004), insX);
        gbc.gridx = gridxNum;
        //base.add(null, gbc);

        getContentPane().add(base);
    }

    public void openWindow() {
        setVisible(true);
    }

    private void addListenerToElements() {
    }

    private void closeWindow() {
        //dispose();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(false);
    }

//    private void waitRepeatServer() {
//        setEnabled(false);
//        while (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getEntryRequestFlag() ==
//                JvMessagesDefinesCtrl.TypeFlags.DEFAULT) {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException exception) {
//                JvLog.write(JvLog.TypeLog.Error, "Не удалось ждать");
//            }
//        }
//        if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getEntryRequestFlag() ==
//                JvMessagesDefinesCtrl.TypeFlags.TRUE) {
//            closeWindow();
//            setEnabled(true);
//            JvLog.write(JvLog.TypeLog.Info, "Вход Авыполнен");
//        } else if (JvGetterControls.getInstance().getBeanMessagesDefinesCtrl().getEntryRequestFlag() ==
//                JvMessagesDefinesCtrl.TypeFlags.FALSE) {
//            setEnabled(true);
//            JvGetterAuthUiComponents.getInstance()
//                    .getBeanAuthOptionPane("Вход не выполнен, данные не верные.", JvAuthOptionPane.TypeDlg.ERROR);
//        }
//    }
}
