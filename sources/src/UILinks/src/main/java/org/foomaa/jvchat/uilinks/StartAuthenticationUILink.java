package org.foomaa.jvchat.uilinks;

import org.foomaa.jvchat.uicomponents.auth.MainFrameAuthUI;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;


@Component
@Lazy
@Profile("users")
public class StartAuthenticationUILink {
    StartAuthenticationUILink(MainFrameAuthUI mainFrameAuthUI) {
        mainFrameAuthUI.openWindow();
    }
}