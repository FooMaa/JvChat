package org.foomaa.jvchat.uilinks;

import java.util.Objects;

import org.springframework.context.annotation.*;

import lombok.Builder;

import org.foomaa.jvchat.uicomponents.auth.MainFrameAuthUI;

public class StartAuthenticationUILink {
    // DI ↓
    private final MainFrameAuthUI mainFrameAuthUI;

    @Builder
    StartAuthenticationUILink(MainFrameAuthUI mainFrameAuthUI) {
        this.mainFrameAuthUI = Objects.requireNonNull(mainFrameAuthUI, "mainTool is mandatory");
    }

    public void openFrame() {
        mainFrameAuthUI.openWindow();
    }
}
