package org.foomaa.jvchat.uicomponents.auth;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JvGetterAuthUiComponents {
    private static JvGetterAuthUiComponents instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterAuthUiComponents() {
        context = new AnnotationConfigApplicationContext(
                JvAuthUiComponentsSpringConfig.class);
    }

    public static JvGetterAuthUiComponents getInstance() {
        if (instance == null) {
            instance = new JvGetterAuthUiComponents();
        }
        return instance;
    }

    public JvAuthActiveLabel getBeanAuthActiveLabel(String text) {
        return (JvAuthActiveLabel) context.getBean(
                JvAuthUiComponentsSpringConfig.NameBeans.BeanAuthActiveLabel.getValue(),
                text);
    }

    public JvAuthButton getBeanAuthButton(String text) {
        return (JvAuthButton) context.getBean(
                JvAuthUiComponentsSpringConfig.NameBeans.BeanAuthButton.getValue(),
                text);
    }

    public JvAuthLabel getBeanAuthLabel(String text) {
        return (JvAuthLabel) context.getBean(
                JvAuthUiComponentsSpringConfig.NameBeans.BeanAuthLabel.getValue(),
                text);
    }

    public JvAuthOptionPane getBeanAuthOptionPane(String msg, JvAuthOptionPane.TypeDlg type) {
        return (JvAuthOptionPane) context.getBean(
                JvAuthUiComponentsSpringConfig.NameBeans.BeanAuthOptionPane.getValue(),
                msg, type);
    }

    public JvAuthPasswordField getBeanAuthPasswordField(String text) {
        return (JvAuthPasswordField) context.getBean(
                JvAuthUiComponentsSpringConfig.NameBeans.BeanAuthPasswordField.getValue(),
                text);
    }

    public JvAuthTextField getBeanAuthTextField(String text) {
        return (JvAuthTextField) context.getBean(
                JvAuthUiComponentsSpringConfig.NameBeans.BeanAuthTextField.getValue(),
                text);
    }

    public JvAuthEntryFrame getBeanAuthEntryFrame() {
        return context.getBean(JvAuthUiComponentsSpringConfig.NameBeans.BeanAuthEntryFrame.getValue(),
                JvAuthEntryFrame.class);

    }

    public JvAuthNewPasswordFrame getBeanAuthNewPasswordFrame(String post) {
        return (JvAuthNewPasswordFrame) context.getBean(
                JvAuthUiComponentsSpringConfig.NameBeans.BeanAuthNewPasswordFrame.getValue(),
                post);
    }

    public JvAuthRegistrationFrame getBeanAuthRegistrationFrame() {
        return context.getBean(JvAuthUiComponentsSpringConfig.NameBeans.BeanAuthRegistrationFrame.getValue(),
                JvAuthRegistrationFrame.class);
    }

    public JvAuthResetPasswordFrame getBeanAuthResetPasswordFrame() {
        return context.getBean(JvAuthUiComponentsSpringConfig.NameBeans.BeanAuthResetPasswordFrame.getValue(),
                JvAuthResetPasswordFrame.class);
    }

    public JvAuthVerifyCodeFrame getBeanAuthVerifyCodeFrame(JvAuthVerifyCodeFrame.RegimeWork rw) {
        return (JvAuthVerifyCodeFrame) context.getBean(
                JvAuthUiComponentsSpringConfig.NameBeans.BeanAuthVerifyCodeFrame.getValue(),
                rw);
    }
}