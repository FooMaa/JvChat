package org.foomaa.jvchat.uicomponents.auth;

import lombok.Builder;

public class DefinesAuthUI {
    @Builder
    DefinesAuthUI() {}

    public enum RegimeWorkMainFrame {
        Auth,
        Registration,
        ResetPassword,
        VerifyCodeRegistration,
        VerifyCodeResetPassword,
        NewPassword
    }
}
