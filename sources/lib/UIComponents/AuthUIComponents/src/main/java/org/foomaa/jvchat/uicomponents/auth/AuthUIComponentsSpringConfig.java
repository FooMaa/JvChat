package org.foomaa.jvchat.uicomponents.auth;

import org.springframework.context.annotation.*;


@Configuration
@ComponentScans({
        @ComponentScan("org.foomaa.jvchat.events"),
})
class AuthUIComponentsSpringConfig {
    public enum NameBeans {
        BeanActiveLabelAuthUI("beanActiveLabelAuthUI"),
        BeanButtonAuthUI("beanButtonAuthUI"),
        BeanErrorLabelAuthUI("beanErrorLabelAuthUI"),
        BeanOptionPaneAuthUI("beanOptionPaneAuthUI"),
        BeanPasswordFieldAuthUI("beanPasswordFieldAuthUI"),
        BeanTextFieldAuthUI("beanTextFieldAuthUI"),
        BeanEntryPanelAuthUI("beanEntryPanelAuthUI"),
        BeanNewPasswordPanelAuthUI("beanNewPasswordPanelAuthUI"),
        BeanRegistrationPanelAuthUI("beanRegistrationPanelAuthUI"),
        BeanResetPasswordPanelAuthUI("beanResetPasswordPanelAuthUI"),
        BeanVerifyCodePanelAuthUI("beanVerifyCodePanelAuthUI"),
        BeanTitlePanelAuthUI("beanTitlePanelAuthUI"),
        BeanToolTipAuthUI("beanToolTipAuthUI"),
        BeanMainFrameAuthUI("beanMainFrameAuthUI"),
        BeanDefinesAuthUI("beanDefinesAuthUI");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanActiveLabelAuthUI")
    @Scope("prototype")
    @SuppressWarnings("unused")
    public ActiveLabelAuthUI beanActiveLabelAuthUI(String text) {
        return new ActiveLabelAuthUI(text);
    }

    @Bean(name = "beanButtonAuthUI")
    @Scope("prototype")
    @SuppressWarnings("unused")
    public ButtonAuthUI beanButtonAuthUI(String text) {
        return new ButtonAuthUI(text);
    }

    @Bean(name = "beanErrorLabelAuthUI")
    @Scope("prototype")
    @SuppressWarnings("unused")
    public ErrorLabelAuthUI beanErrorLabelAuthUI(String text) {
        return new ErrorLabelAuthUI(text);
    }

    @Bean(name = "beanPasswordFieldAuthUI")
    @Scope("prototype")
    @SuppressWarnings("unused")
    public PasswordFieldAuthUI beanPasswordFieldAuthUI(String text) {
        return new PasswordFieldAuthUI(text);
    }

    @Bean(name = "beanTextFieldAuthUI")
    @Scope("prototype")
    @SuppressWarnings("unused")
    public TextFieldAuthUI beanTextFieldAuthUI(String text) {
        return new TextFieldAuthUI(text);
    }

    @Bean(name = "beanToolTipAuthUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public ToolTipAuthUI beanToolTipAuthUI() {
        return new ToolTipAuthUI();
    }

    @Bean(name = "beanMainFrameAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public MainFrameAuthUI beanMainFrameAuthUI() {
        return new MainFrameAuthUI();
    }

//    @Bean(name = "beanEntryPanelAuthUI")
//    @Lazy
//    @Scope("singleton")
//    @SuppressWarnings("unused")
//    public EntryPanelAuthUI beanEntryPanelAuthUI() {
//        return new EntryPanelAuthUI();
//    }

    @Bean(name = "beanNewPasswordPanelAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public NewPasswordPanelAuthUI beanNewPasswordPanelAuthUI() {
        return new NewPasswordPanelAuthUI();
    }

    @Bean(name = "beanRegistrationPanelAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public RegistrationPanelAuthUI beanRegistrationPanelAuthUI() {
        return new RegistrationPanelAuthUI();
    }

    @Bean(name = "beanResetPasswordPanelAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public ResetPasswordPanelAuthUI beanResetPasswordPanelAuthUI() {
        return new ResetPasswordPanelAuthUI();
    }

    @Bean(name = "beanVerifyCodePanelAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public VerifyCodePanelAuthUI beanVerifyCodePanelAuthUI() {
        return new VerifyCodePanelAuthUI();
    }

    @Bean(name = "beanTitlePanelAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public TitlePanelAuthUI beanTitlePanelAuthUI() {
        return new TitlePanelAuthUI();
    }

    @Bean(name = "beanDefinesAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public DefinesAuthUI beanDefinesAuthUI() {
        return new DefinesAuthUI();
    }
}