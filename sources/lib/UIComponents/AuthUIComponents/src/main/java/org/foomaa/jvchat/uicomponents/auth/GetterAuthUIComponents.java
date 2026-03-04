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
        return (ActiveLabelAuthUI) context.getBean(
                AuthUIComponentsSpringConfig.NameBeans.BeanActiveLabelAuthUI.getValue(),
                text);
    }

    public ButtonAuthUI getBeanButtonAuthUI(String text) {
        return (ButtonAuthUI) context.getBean(
                AuthUIComponentsSpringConfig.NameBeans.BeanButtonAuthUI.getValue(),
                text);
    }

    public ErrorLabelAuthUI getBeanErrorLabelAuthUI(String text) {
        return (ErrorLabelAuthUI) context.getBean(
                AuthUIComponentsSpringConfig.NameBeans.BeanErrorLabelAuthUI.getValue(),
                text);
    }

    public OptionPaneAuthUI getBeanOptionPaneAuthUI(String msg, OptionPaneAuthUI.TypeDlg type) {
        return (OptionPaneAuthUI) context.getBean(
                AuthUIComponentsSpringConfig.NameBeans.BeanOptionPaneAuthUI.getValue(),
                msg, type);
    }

    public PasswordFieldAuthUI getBeanPasswordFieldAuthUI(String text) {
        return (PasswordFieldAuthUI) context.getBean(
                AuthUIComponentsSpringConfig.NameBeans.BeanPasswordFieldAuthUI.getValue(),
                text);
    }

    public TextFieldAuthUI getBeanTextFieldAuthUI(String text) {
        return (TextFieldAuthUI) context.getBean(
                AuthUIComponentsSpringConfig.NameBeans.BeanTextFieldAuthUI.getValue(),
                text);
    }

    public ToolTipAuthUI getBeanToolTipAuthUI() {
        return context.getBean(AuthUIComponentsSpringConfig.NameBeans.BeanToolTipAuthUI.getValue(),
                ToolTipAuthUI.class);
    }

    public EntryPanelAuthUI getBeanEntryPanelAuthUI() {
        return null;

    }

    public MainFrameAuthUI getBeanMainFrameAuthUI() {
        return context.getBean(AuthUIComponentsSpringConfig.NameBeans.BeanMainFrameAuthUI.getValue(),
                MainFrameAuthUI.class);

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
        return context.getBean(AuthUIComponentsSpringConfig.NameBeans.BeanTitlePanelAuthUI.getValue(),
                TitlePanelAuthUI.class);
    }

    public DefinesAuthUI getBeanDefinesAuthUI() {
        return context.getBean(AuthUIComponentsSpringConfig.NameBeans.BeanDefinesAuthUI.getValue(),
                DefinesAuthUI.class);
    }
}