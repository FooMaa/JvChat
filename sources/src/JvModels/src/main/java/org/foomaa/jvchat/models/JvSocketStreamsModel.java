package org.foomaa.jvchat.models;

import org.foomaa.jvchat.logger.JvLog;
import org.foomaa.jvchat.structobjects.JvGetterStructObjects;
import org.foomaa.jvchat.structobjects.JvSocketStreamsStructObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class JvSocketStreamsModel extends JvBaseModel {
    private static JvSocketStreamsModel instance;

    private JvSocketStreamsModel() {
        setRootObject(JvGetterStructObjects.getInstance()
                .getBeanRootStructObject(getNameModel()));
    }

    static JvSocketStreamsModel getInstance() {
        if (instance == null) {
            instance = new JvSocketStreamsModel();
        }
        return instance;
    }

    public JvSocketStreamsStructObject createSocketStreams(Socket socket) {
        JvSocketStreamsStructObject socketStreamsStructObject =
                JvGetterStructObjects.getInstance().getBeanSocketStreamsStructObject();
        try {
            socketStreamsStructObject.setSendStream(new DataOutputStream(socket.getOutputStream()));
            socketStreamsStructObject.setReadStream(new DataInputStream(socket.getInputStream()));
        } catch (IOException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Ошибка в создании потоков отправки и принятия сообщений");
        }
        return socketStreamsStructObject;
    }
}
