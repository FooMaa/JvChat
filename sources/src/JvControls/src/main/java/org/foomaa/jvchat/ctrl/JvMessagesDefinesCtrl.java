package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.messages.JvDefinesMessages;


public class JvMessagesDefinesCtrl {
    private static JvMessagesDefinesCtrl instance;

    private JvMessagesDefinesCtrl() {}

    static JvMessagesDefinesCtrl getInstance() {
        if (instance == null) {
            instance = new JvMessagesDefinesCtrl();
        }
        return instance;
    }

    // FLAGS
    public enum TypeFlags {
        TRUE,
        FALSE,
        DEFAULT
    }

    private TypeFlags EntryRequestFlag = TypeFlags.DEFAULT;
    private TypeFlags RegistrationRequestFlag = TypeFlags.DEFAULT;
    private TypeFlags ResetPasswordRequestFlag = TypeFlags.DEFAULT;
    private TypeFlags VerifyFamousEmailRequestFlag = TypeFlags.DEFAULT;
    private JvDefinesMessages.TypeErrorRegistration errorRegistrationFlag =
    JvDefinesMessages.TypeErrorRegistration.NoError;
    private TypeFlags ChangePasswordRequest = TypeFlags.DEFAULT;
    private TypeFlags VerifyRegistrationEmailRequestFlag = TypeFlags.DEFAULT;
    private JvDefinesMessages.TypeErrorRegistration ErrorVerifyRegEmailFlag =
    JvDefinesMessages.TypeErrorRegistration.NoError;
    private TypeFlags ChatsLoadReplyFlag = TypeFlags.DEFAULT;
    private TypeFlags LoadUsersOnlineReplyFlag = TypeFlags.DEFAULT;
    private TypeFlags TextMessagesLoadReplyFlag = TypeFlags.DEFAULT;
    private TypeFlags TextMessageRedirectServerToUserFlag = TypeFlags.DEFAULT;
    private TypeFlags TextMessageSendUserToServerVerificationFlag = TypeFlags.DEFAULT;
    private TypeFlags TextMessagesChangingStatusFromServerVerificationFlag = TypeFlags.DEFAULT;
    private TypeFlags TextMessagesChangingStatusFromUserVerificationFlag = TypeFlags.DEFAULT;
    private TypeFlags TextMessageRedirectServerToUserVerification = TypeFlags.DEFAULT;
    // FLAGS

    public void setEntryRequestFlag(TypeFlags newFlag) {
        if (EntryRequestFlag != newFlag) {
            EntryRequestFlag = newFlag;
        }
    }

    public void setRegistrationRequestFlag(TypeFlags newFlag) {
        if (RegistrationRequestFlag != newFlag) {
            RegistrationRequestFlag = newFlag;
        }
    }

    public void setResetPasswordRequestFlag(TypeFlags newFlag) {
        if (ResetPasswordRequestFlag != newFlag) {
            ResetPasswordRequestFlag = newFlag;
        }
    }

    public void setVerifyFamousEmailRequestFlag(TypeFlags newFlag) {
        if (VerifyFamousEmailRequestFlag != newFlag) {
            VerifyFamousEmailRequestFlag = newFlag;
        }
    }

    public void setErrorRegistrationFlag(JvDefinesMessages.TypeErrorRegistration newFlag) {
        if (errorRegistrationFlag != newFlag) {
            errorRegistrationFlag = newFlag;
        }
    }

    public void setChangePasswordRequest(TypeFlags newFlag) {
        if (ChangePasswordRequest != newFlag) {
            ChangePasswordRequest = newFlag;
        }
    }

    public void setVerifyRegistrationEmailRequestFlag(TypeFlags newFlag) {
        if (VerifyRegistrationEmailRequestFlag != newFlag) {
            VerifyRegistrationEmailRequestFlag = newFlag;
        }
    }

    public void setErrorVerifyRegEmailFlag(JvDefinesMessages.TypeErrorRegistration newFlag) {
        if (ErrorVerifyRegEmailFlag != newFlag) {
            ErrorVerifyRegEmailFlag = newFlag;
        }
    }

    public void setChatsLoadReplyFlag(TypeFlags newFlag) {
        if (ChatsLoadReplyFlag != newFlag) {
            ChatsLoadReplyFlag = newFlag;
        }
    }

    public void setLoadUsersOnlineReplyFlag(TypeFlags newFlag) {
        if (LoadUsersOnlineReplyFlag != newFlag) {
            LoadUsersOnlineReplyFlag = newFlag;
        }
    }

    public void setTextMessagesLoadReplyFlag(TypeFlags newFlag) {
        if (TextMessagesLoadReplyFlag != newFlag) {
            TextMessagesLoadReplyFlag = newFlag;
        }
    }

    public void setTextMessageRedirectServerToUserFlag(TypeFlags newFlag) {
        if (TextMessageRedirectServerToUserFlag != newFlag) {
            TextMessageRedirectServerToUserFlag = newFlag;
        }
    }

    public void setTextMessageSendUserToServerVerificationFlag(TypeFlags newFlag) {
        if (TextMessageSendUserToServerVerificationFlag != newFlag) {
            TextMessageSendUserToServerVerificationFlag = newFlag;
        }
    }

    public void setTextMessagesChangingStatusFromServerVerificationFlag(TypeFlags newFlag) {
        if (TextMessagesChangingStatusFromServerVerificationFlag != newFlag) {
            TextMessagesChangingStatusFromServerVerificationFlag = newFlag;
        }
    }

    public void setTextMessagesChangingStatusFromUserVerificationFlag(TypeFlags newFlag) {
        if (TextMessagesChangingStatusFromUserVerificationFlag != newFlag) {
            TextMessagesChangingStatusFromUserVerificationFlag = newFlag;
        }
    }

    public void setTextMessageRedirectServerToUserVerification(TypeFlags newFlag) {
        if (TextMessageRedirectServerToUserVerification != newFlag) {
            TextMessageRedirectServerToUserVerification = newFlag;
        }
    }

    public TypeFlags getEntryRequestFlag() {
        return EntryRequestFlag;
    }

    public TypeFlags getRegistrationRequestFlag() {
        return RegistrationRequestFlag;
    }

    public JvDefinesMessages.TypeErrorRegistration getErrorRegistrationFlag() {
        return errorRegistrationFlag;
    }

    public TypeFlags getResetPasswordRequestFlag() {
        return ResetPasswordRequestFlag;
    }

    public TypeFlags getVerifyFamousEmailRequestFlag() {
        return VerifyFamousEmailRequestFlag;
    }

    public TypeFlags getChangePasswordRequest() {
        return ChangePasswordRequest;
    }

    public TypeFlags getVerifyRegistrationEmailRequestFlag() {
        return VerifyRegistrationEmailRequestFlag;
    }

    public JvDefinesMessages.TypeErrorRegistration getErrorVerifyRegEmailFlag() {
        return ErrorVerifyRegEmailFlag;
    }

    public TypeFlags getChatsLoadReplyFlag() {
        return ChatsLoadReplyFlag;
    }

    public TypeFlags getLoadUsersOnlineReplyFlag() {
        return LoadUsersOnlineReplyFlag;
    }

    public TypeFlags getTextMessagesLoadReplyFlag() {
        return TextMessagesLoadReplyFlag;
    }

    public TypeFlags getTextMessageRedirectServerToUserFlag() {
        return TextMessageRedirectServerToUserFlag;
    }

    public TypeFlags getTextMessageSendUserToServerVerificationFlag() {
        return TextMessageSendUserToServerVerificationFlag;
    }

    public TypeFlags getTextMessagesChangingStatusFromServerVerificationFlag() {
        return TextMessagesChangingStatusFromServerVerificationFlag;
    }

    public TypeFlags getTextMessagesChangingStatusFromUserVerificationFlag() {
        return TextMessagesChangingStatusFromUserVerificationFlag;
    }

    public TypeFlags getTextMessageRedirectServerToUserVerification() {
        return TextMessageRedirectServerToUserVerification;
    }
}