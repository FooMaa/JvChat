package org.foomaa.jvchat.uicomponents.auth;

import org.springframework.context.annotation.*;


@Configuration
@ComponentScans({
        @ComponentScan("org.foomaa.jvchat.events"),
})
class JvAuthUIComponentsSpringConfig {
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
    public JvActiveLabelAuthUI beanActiveLabelAuthUI(String text) {
        return new JvActiveLabelAuthUI(text);
    }

    @Bean(name = "beanButtonAuthUI")
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvButtonAuthUI beanButtonAuthUI(String text) {
        return new JvButtonAuthUI(text);
    }

    @Bean(name = "beanErrorLabelAuthUI")
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvErrorLabelAuthUI beanErrorLabelAuthUI(String text) {
        return new JvErrorLabelAuthUI(text);
    }

    @Bean(name = "beanOptionPaneAuthUI")
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvOptionPaneAuthUI beanOptionPaneAuthUI(String msg, JvOptionPaneAuthUI.TypeDlg type) {
        return new JvOptionPaneAuthUI(msg, type);
    }

    @Bean(name = "beanPasswordFieldAuthUI")
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvPasswordFieldAuthUI beanPasswordFieldAuthUI(String text) {
        return new JvPasswordFieldAuthUI(text);
    }

    @Bean(name = "beanTextFieldAuthUI")
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvTextFieldAuthUI beanTextFieldAuthUI(String text) {
        return new JvTextFieldAuthUI(text);
    }

    @Bean(name = "beanToolTipAuthUI")
    @Lazy
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvToolTipAuthUI beanToolTipAuthUI() {
        return new JvToolTipAuthUI();
    }

    @Bean(name = "beanMainFrameAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvMainFrameAuthUI beanMainFrameAuthUI() {
        return new JvMainFrameAuthUI();
    }

    @Bean(name = "beanEntryPanelAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvEntryPanelAuthUI beanEntryPanelAuthUI() {
        return new JvEntryPanelAuthUI();
    }

    @Bean(name = "beanNewPasswordPanelAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvNewPasswordPanelAuthUI beanNewPasswordPanelAuthUI() {
        return new JvNewPasswordPanelAuthUI();
    }

    @Bean(name = "beanRegistrationPanelAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvRegistrationPanelAuthUI beanRegistrationPanelAuthUI() {
        return new JvRegistrationPanelAuthUI();
    }

    @Bean(name = "beanResetPasswordPanelAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvResetPasswordPanelAuthUI beanResetPasswordPanelAuthUI() {
        return new JvResetPasswordPanelAuthUI();
    }

    @Bean(name = "beanVerifyCodePanelAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvVerifyCodePanelAuthUI beanVerifyCodePanelAuthUI() {
        return new JvVerifyCodePanelAuthUI();
    }

    @Bean(name = "beanTitlePanelAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvTitlePanelAuthUI beanTitlePanelAuthUI() {
        return new JvTitlePanelAuthUI();
    }

    @Bean(name = "beanDefinesAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvDefinesAuthUI beanDefinesAuthUI() {
        return new JvDefinesAuthUI();
    }
}