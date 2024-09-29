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
    private JvDefinesMessages.TypeErrorRegistration errorVerifyRegEmailFlag =
    JvDefinesMessages.TypeErrorRegistration.NoError;
    private TypeFlags chatsLoadReplyFlag = TypeFlags.DEFAULT;
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
        if (errorVerifyRegEmailFlag != newFlag) {
            errorVerifyRegEmailFlag = newFlag;
        }
    }

    public void setChatsLoadReplyFlag(TypeFlags newFlag) {
        if (chatsLoadReplyFlag != newFlag) {
            chatsLoadReplyFlag = newFlag;
        }
    }

    @SuppressWarnings("unused")
    public TypeFlags getEntryRequestFlag() {
        return EntryRequestFlag;
    }

    @SuppressWarnings("unused")
    public TypeFlags getRegistrationRequestFlag() {
        return RegistrationRequestFlag;
    }

    @SuppressWarnings("unused")
    public JvDefinesMessages.TypeErrorRegistration getErrorRegistrationFlag() {
        return errorRegistrationFlag;
    }

    @SuppressWarnings("unused")
    public TypeFlags getResetPasswordRequestFlag() {
        return ResetPasswordRequestFlag;
    }

    @SuppressWarnings("unused")
    public TypeFlags getVerifyFamousEmailRequestFlag() {
        return VerifyFamousEmailRequestFlag;
    }

    @SuppressWarnings("unused")
    public TypeFlags getChangePasswordRequest() {
        return ChangePasswordRequest;
    }

    @SuppressWarnings("unused")
    public TypeFlags getVerifyRegistrationEmailRequestFlag() {
        return VerifyRegistrationEmailRequestFlag;
    }

    @SuppressWarnings("unused")
    public JvDefinesMessages.TypeErrorRegistration getErrorVerifyRegEmailFlag() {
        return errorVerifyRegEmailFlag;
    }

    public TypeFlags getChatsLoadReplyFlag() {
        return chatsLoadReplyFlag;
    }
}