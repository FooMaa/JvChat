package org.foomaa.jvchat.uicomponents.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;


@Configuration
class JvAuthUIComponentsSpringConfig {
    public enum NameBeans {
        BeanActiveLabelAuthUI("beanActiveLabelAuthUI"),
        BeanButtonAuthUI("beanButtonAuthUI"),
        BeanLabelAuthUI("beanLabelAuthUI"),
        BeanOptionPaneAuthUI("beanOptionPaneAuthUI"),
        BeanPasswordFieldAuthUI("beanPasswordFieldAuthUI"),
        BeanTextFieldAuthUI("beanTextFieldAuthUI"),
        BeanEntryFrameAuthUI("beanEntryFrameAuthUI"),
        BeanNewPasswordFrameAuthUI("beanNewPasswordFrameAuthUI"),
        BeanRegistrationFrameAuthUI("beanRegistrationFrameAuthUI"),
        BeanResetPasswordFrameAuthUI("beanResetPasswordFrameAuthUI"),
        BeanVerifyCodeFrameAuthUI("beanVerifyCodeFrameAuthUI");

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

    @Bean(name = "beanLabelAuthUI")
    @Scope("prototype")
    @SuppressWarnings("unused")
    public JvLabelAuthUI beanLabelAuthUI(String text) {
        return new JvLabelAuthUI(text);
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

    @Bean(name = "beanEntryFrameAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvEntryFrameAuthUI beanEntryFrameAuthUI() {
        return new JvEntryFrameAuthUI();
    }

    @Bean(name = "beanNewPasswordFrameAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvNewPasswordFrameAuthUI beanNewPasswordFrameAuthUI(String post) {
        return new JvNewPasswordFrameAuthUI(post);
    }

    @Bean(name = "beanRegistrationFrameAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvRegistrationFrameAuthUI beanRegistrationFrameAuthUI() {
        return new JvRegistrationFrameAuthUI();
    }

    @Bean(name = "beanResetPasswordFrameAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvResetPasswordFrameAuthUI beanResetPasswordFrameAuthUI() {
        return new JvResetPasswordFrameAuthUI();
    }

    @Bean(name = "beanVerifyCodeFrameAuthUI")
    @Lazy
    @Scope("singleton")
    @SuppressWarnings("unused")
    public JvVerifyCodeFrameAuthUI beanVerifyCodeFrameAuthUI(JvVerifyCodeFrameAuthUI.RegimeWork rw) {
        return new JvVerifyCodeFrameAuthUI(rw);
    }
}