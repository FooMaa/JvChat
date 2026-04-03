package org.foomaa.jvchat.ctrl;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import org.foomaa.jvchat.messages.DefinesMessages;

@Getter
@Setter
public class MessagesDefinesCtrl {
    public enum TypeFlags {
        TRUE,
        FALSE,
        DEFAULT
    }

    private TypeFlags EntryRequestFlag = TypeFlags.DEFAULT;
    private TypeFlags RegistrationRequestFlag = TypeFlags.DEFAULT;
    private TypeFlags ResetPasswordRequestFlag = TypeFlags.DEFAULT;
    private TypeFlags VerifyFamousEmailRequestFlag = TypeFlags.DEFAULT;
    private DefinesMessages.TypeErrorRegistration errorRegistrationFlag = DefinesMessages.TypeErrorRegistration.NoError;
    private TypeFlags ChangePasswordRequest = TypeFlags.DEFAULT;
    private TypeFlags VerifyRegistrationEmailRequestFlag = TypeFlags.DEFAULT;
    private DefinesMessages.TypeErrorRegistration ErrorVerifyRegEmailFlag =
            DefinesMessages.TypeErrorRegistration.NoError;
    private TypeFlags ChatsLoadReplyFlag = TypeFlags.DEFAULT;
    private TypeFlags LoadUsersOnlineReplyFlag = TypeFlags.DEFAULT;
    private TypeFlags TextMessagesLoadReplyFlag = TypeFlags.DEFAULT;
    private TypeFlags TextMessageRedirectServerToUserFlag = TypeFlags.DEFAULT;
    private TypeFlags TextMessageSendUserToServerFlag = TypeFlags.DEFAULT;
    private TypeFlags TextMessagesChangingStatusFromServerFlag = TypeFlags.DEFAULT;
    private TypeFlags TextMessagesChangingStatusFromUserFlag = TypeFlags.DEFAULT;
    private TypeFlags TextMessageRedirectServerToUserVerificationFlag = TypeFlags.DEFAULT;

    @Builder
    MessagesDefinesCtrl() {}
}
