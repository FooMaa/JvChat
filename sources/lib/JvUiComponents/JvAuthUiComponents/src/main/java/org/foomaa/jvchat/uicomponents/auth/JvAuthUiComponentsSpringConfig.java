package org.foomaa.jvchat.uicomponents.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;


@Configuration
public class JvAuthUiComponentsSpringConfig {
    public enum NameBeans {
        BeanAuthActiveLabel("beanAuthActiveLabel"),
        BeanAuthButton("beanAuthButton"),
        BeanAuthLabel("beanAuthLabel"),
        BeanAuthOptionPane("beanAuthOptionPane"),
        BeanAuthPasswordField("beanAuthPasswordField"),
        BeanAuthTextField("beanAuthTextField"),
        BeanAuthEntryFrame("beanAuthEntryFrame"),
        BeanAuthNewPasswordFrame("beanAuthNewPasswordFrame"),
        BeanAuthRegistrationFrame("beanAuthRegistrationFrame"),
        BeanAuthResetPasswordFrame("beanAuthResetPasswordFrame"),
        BeanAuthVerifyCodeFrame("beanAuthVerifyCodeFrame");

        private final String value;

        NameBeans(String newValue) {
            value = newValue;
        }

        public String getValue() {
            return value;
        }
    }

    @Bean(name = "beanAuthActiveLabel")
    @Scope("prototype")
    public JvAuthActiveLabel beanAuthActiveLabel(String text) {
        return new JvAuthActiveLabel(text);
    }

    @Bean(name = "beanAuthButton")
    @Scope("prototype")
    public JvAuthButton beanAuthButton(String text) {
        return new JvAuthButton(text);
    }

    @Bean(name = "beanAuthLabel")
    @Scope("prototype")
    public JvAuthLabel beanAuthLabel(String text) {
        return new JvAuthLabel(text);
    }

    @Bean(name = "beanAuthOptionPane")
    @Scope("prototype")
    public JvAuthOptionPane beanAuthOptionPane(String msg, JvAuthOptionPane.TypeDlg type) {
        return new JvAuthOptionPane(msg, type);
    }

    @Bean(name = "beanAuthPasswordField")
    @Scope("prototype")
    public JvAuthPasswordField beanAuthPasswordField(String text) {
        return new JvAuthPasswordField(text);
    }

    @Bean(name = "beanAuthTextField")
    @Scope("prototype")
    public JvAuthTextField beanAuthTextField(String text) {
        return new JvAuthTextField(text);
    }

    @Bean(name = "beanAuthEntryFrame")
    @Lazy
    @Scope("singleton")
    public JvAuthEntryFrame beanAuthEntryFrame() {
        return JvAuthEntryFrame.getInstance();
    }

    @Bean(name = "beanAuthNewPasswordFrame")
    @Lazy
    @Scope("singleton")
    public JvAuthNewPasswordFrame beanAuthNewPasswordFrame(String post) {
        return JvAuthNewPasswordFrame.getInstance(post);
    }

    @Bean(name = "beanAuthRegistrationFrame")
    @Lazy
    @Scope("singleton")
    public JvAuthRegistrationFrame beanAuthRegistrationFrame() {
        return JvAuthRegistrationFrame.getInstance();
    }

    @Bean(name = "beanAuthResetPasswordFrame")
    @Lazy
    @Scope("singleton")
    public JvAuthResetPasswordFrame beanAuthResetPasswordFrame() {
        return JvAuthResetPasswordFrame.getInstance();
    }

    @Bean(name = "beanAuthVerifyCodeFrame")
    @Lazy
    @Scope("singleton")
    public JvAuthVerifyCodeFrame beanAuthVerifyCodeFrame(JvAuthVerifyCodeFrame.RegimeWork rw) {
        return JvAuthVerifyCodeFrame.getInstance(rw);
    }
}