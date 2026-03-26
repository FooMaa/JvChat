package org.foomaa.jvchat.uilinks;

import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import org.foomaa.jvchat.uicomponents.auth.MainFrameAuthUI;

@Component
@Lazy
@Profile("users")
public class StartAuthenticationUILink {
    StartAuthenticationUILink(MainFrameAuthUI mainFrameAuthUI) {
        mainFrameAuthUI.openWindow();
    }
}
