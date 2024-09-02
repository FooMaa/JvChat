package org.foomaa.jvchat.messages;


public class JvDefinesMessages {
    private static JvDefinesMessages instance;

    private JvDefinesMessages() {}

    public static JvDefinesMessages getInstance() {
        if (instance == null) {
            instance = new JvDefinesMessages();
        }
        return instance;
    }

    public enum TypeMessage {
        EntryRequest(0),
        EntryReply(1),
        RegistrationRequest(2),
        RegistrationReply(3),
        VerifyRegistrationEmailRequest(4),
        VerifyRegistrationEmailReply(5),
        ResetPasswordRequest(6),
        ResetPasswordReply(7),
        VerifyFamousEmailRequest(8),
        VerifyFamousEmailReply(9),
        ChangePasswordRequest(10),
        ChangePasswordReply(11),
        NecessityServerRequest(12),
        NecessityServerReply(13),
        ChatsLoadRequest(14),
        ChatsLoadReply(15);

        private final int value;

        TypeMessage(int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }

        public boolean compare(int i) {
            return value == i;
        }

        public static TypeMessage getTypeMsg(int value)
        {
            TypeMessage[] errors = TypeMessage.values();
            for (TypeMessage error : errors) {
                if (error.compare(value))
                    return error;
            }
            return null;
        }
    }

    public enum TypeData {
        Login,
        Email,
        Password,
        ErrorReg,
        BoolReply,
        VerifyCode,
        ChatsLoad,
        ChatsInfoList,
        TypeNecessity
    }

    public enum TypeNecessityServer {
        LoginUser(0);

        private final int value;

        TypeNecessityServer(int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }

        public boolean compare(int i) {
            return value == i;
        }

        public static TypeNecessityServer getTypeNecessityServer(int value)
        {
            TypeNecessityServer[] errors = TypeNecessityServer.values();
            for (TypeNecessityServer error : errors) {
                if (error.compare(value))
                    return error;
            }
            return null;
        }
    }

    public enum TypeErrorRegistration {
        Login(JvClientServerSerializeProtocolMessage_pb.RegistrationReply.Error.Login_VALUE),
        Email(JvClientServerSerializeProtocolMessage_pb.RegistrationReply.Error.Email_VALUE),
        LoginAndEmail(JvClientServerSerializeProtocolMessage_pb.RegistrationReply.Error.LoginAndEmail_VALUE),
        EmailSending(JvClientServerSerializeProtocolMessage_pb.RegistrationReply.Error.EmailSending_VALUE),
        Code(JvClientServerSerializeProtocolMessage_pb.VerifyRegistrationEmailReply.Error.Code_VALUE),
        NoError(JvClientServerSerializeProtocolMessage_pb.RegistrationReply.Error.NoError_VALUE);

        private final int value;

        TypeErrorRegistration(int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }

        public boolean compare(int i) {
            return value == i;
        }

        public static TypeErrorRegistration getTypeError(int value)
        {
            TypeErrorRegistration[] errors = TypeErrorRegistration.values();
            for (TypeErrorRegistration error : errors) {
                if (error.compare(value))
                    return error;
            }
            return TypeErrorRegistration.NoError;
        }
    }
}