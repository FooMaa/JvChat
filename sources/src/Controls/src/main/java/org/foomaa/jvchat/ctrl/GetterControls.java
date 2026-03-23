package org.foomaa.jvchat.ctrl;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.net.Socket;
import java.util.Arrays;


public class GetterControls {
    private static GetterControls instance;
//    private final AnnotationConfigApplicationContext context;

    private GetterControls() {
//        context = new AnnotationConfigApplicationContext(
//                ControlsSpringConfig.class);
//        Log.write(Log.TypeLog.Info, Arrays.toString(context.getEnvironment().getActiveProfiles()));
    }

    public static GetterControls getInstance() {
        if (instance == null) {
            instance = new GetterControls();
        }
        return instance;
    }

    public NetworkCtrl getBeanNetworkCtrl() {
        return null;
    }

    public MessagesDefinesCtrl getBeanMessagesDefinesCtrl() {
        return null;
    }

    public SocketRunnableCtrl getBeanSocketRunnableCtrl(Socket socket) {
        return null;
    }

    public MessagesDialogCtrl getBeanMessagesDialogCtrl() {
        return null;
    }

    public OnlineServersCtrl getBeanOnlineServersCtrl() {
        return null;
    }
}