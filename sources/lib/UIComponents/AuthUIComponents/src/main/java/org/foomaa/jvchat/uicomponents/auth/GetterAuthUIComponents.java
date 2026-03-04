package org.foomaa.jvchat.uicomponents.auth;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class GetterAuthUIComponents {
    private static GetterAuthUIComponents instance;
    private final AnnotationConfigApplicationContext context;

    private GetterAuthUIComponents() {
        context = new AnnotationConfigApplicationContext(AuthUIComponentsSpringConfig.class);
    }

    public static GetterAuthUIComponents getInstance() {
        if (instance == null) {
            instance = new GetterAuthUIComponents();
        }
        return instance;
    }

    public AnnotationConfigApplicationContext getContext() {
        return context;
    }

    public ActiveLabelAuthUI getBeanActiveLabelAuthUI(String text) {
        return null;
    }

    public ButtonAuthUI getBeanButtonAuthUI(String text) {
        return null;
    }

    public ErrorLabelAuthUI getBeanErrorLabelAuthUI(String text) {
        return null;
    }

    public OptionPaneAuthUI getBeanOptionPaneAuthUI(String msg, OptionPaneAuthUI.TypeDlg type) {
        return (OptionPaneAuthUI) context.getBean(
                AuthUIComponentsSpringConfig.NameBeans.BeanOptionPaneAuthUI.getValue(),
                msg, type);
    }

    public PasswordFieldAuthUI getBeanPasswordFieldAuthUI(String text) {
        return null;
    }

    public TextFieldAuthUI getBeanTextFieldAuthUI(String text) {
        return null;
    }

    public ToolTipAuthUI getBeanToolTipAuthUI() {
        return null;
    }

    public EntryPanelAuthUI getBeanEntryPanelAuthUI() {
        return null;

    }

    public MainFrameAuthUI getBeanMainFrameAuthUI() {
        return null;

    }

    public NewPasswordPanelAuthUI getBeanNewPasswordPanelAuthUI() {
        return null;
    }

    public RegistrationPanelAuthUI getBeanRegistrationPanelAuthUI() {
        return null;
    }

    public ResetPasswordPanelAuthUI getBeanResetPasswordPanelAuthUI() {
        return null;
    }

    public VerifyCodePanelAuthUI getBeanVerifyCodePanelAuthUI() {
        return null;
    }

    public TitlePanelAuthUI getBeanTitlePanelAuthUI() {
        return null;
    }

    public DefinesAuthUI getBeanDefinesAuthUI() {
        return context.getBean(AuthUIComponentsSpringConfig.NameBeans.BeanDefinesAuthUI.getValue(),
                DefinesAuthUI.class);
    }
}