package org.foomaa.jvchat.uicomponents.auth;

public class RecordsAuthUI {
    public record Regime(DefinesAuthUI.RegimeWorkMainFrame regime) {}

    public record RegimeLoginEmailPassword(
            DefinesAuthUI.RegimeWorkMainFrame regime, String login, String email, String password) {}

    public record RegimeEmail(DefinesAuthUI.RegimeWorkMainFrame regime, String email) {}
}
