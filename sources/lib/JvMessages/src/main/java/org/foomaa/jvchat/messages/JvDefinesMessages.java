package org.foomaa.jvchat.messages;


public class JvDefinesMessages {
    JvDefinesMessages() {}

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
        ChatsLoadRequest(12),
        ChatsLoadReply(13),
        CheckOnlineUserRequest(14),
        CheckOnlineUserReply(15),
        LoadUsersOnlineStatusRequest(16),
        LoadUsersOnlineStatusReply(17),
        TextMessageSendUserToServer(18),
        TextMessageSendUserToServerVerification(19),
        TextMessagesChangingStatusFromServer(20),
        TextMessagesChangingStatusFromServerVerification(21),
        TextMessagesChangingStatusFromUser(22),
        TextMessagesChangingStatusFromUserVerification(23),
        TextMessageRedirectServerToUser(24),
        TextMessageRedirectServerToUserVerification(25),
        MessagesLoadRequest(26),
        MessagesLoadReply(27);

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
        IP,
        UsersOnlineInfoList,
        UuidsUsersList,
        TimeStampLastOnlineString,
        LoginSender,
        LoginReceiver,
        Text,
        TimeStampMessageSend,
        MapStatusMessages,
        LoginRequesting,
        LoginDialog,
        QuantityMessages,
        MessagesInfoList,
        LastMessageText,
        UuidChat,
        UuidMessage,
        UuidUser,
        IsLoginSentLastMessage,
        DateTimeLastMessage,
        StatusMessage,
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