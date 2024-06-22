package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.messages.JvMessagesDefines;

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
    private JvMessagesDefines.TypeErrorRegistration errorRegistrationFlag =
    JvMessagesDefines.TypeErrorRegistration.NoError;
    private TypeFlags ChangePasswordRequest = TypeFlags.DEFAULT;
    private TypeFlags VerifyRegistrationEmailRequestFlag = TypeFlags.DEFAULT;
    private JvMessagesDefines.TypeErrorRegistration errorVerifyRegEmailFlag =
    JvMessagesDefines.TypeErrorRegistration.NoError;
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

    public void setErrorRegistrationFlag(JvMessagesDefines.TypeErrorRegistration newFlag) {
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

    public void setErrorVerifyRegEmailFlag(JvMessagesDefines.TypeErrorRegistration newFlag) {
        if (errorVerifyRegEmailFlag != newFlag) {
            errorVerifyRegEmailFlag = newFlag;
        }
    };

    public TypeFlags getEntryRequestFlag() {
        return EntryRequestFlag;
    }

    public TypeFlags getRegistrationRequestFlag() {
        return RegistrationRequestFlag;
    }

    public JvMessagesDefines.TypeErrorRegistration getErrorRegistrationFlag() {
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

    public JvMessagesDefines.TypeErrorRegistration getErrorVerifyRegEmailFlag() {
        return errorVerifyRegEmailFlag;
    }
}
