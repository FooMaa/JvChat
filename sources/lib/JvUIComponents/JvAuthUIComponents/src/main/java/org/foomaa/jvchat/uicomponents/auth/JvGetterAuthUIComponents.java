package org.foomaa.jvchat.uicomponents.auth;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JvGetterAuthUIComponents {
    private static JvGetterAuthUIComponents instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterAuthUIComponents() {
        context = new AnnotationConfigApplicationContext(
                JvAuthUIComponentsSpringConfig.class);
    }

    public static JvGetterAuthUIComponents getInstance() {
        if (instance == null) {
            instance = new JvGetterAuthUIComponents();
        }
        return instance;
    }

    public JvActiveLabelAuthUI getBeanActiveLabelAuthUI(String text) {
        return (JvActiveLabelAuthUI) context.getBean(
                JvAuthUIComponentsSpringConfig.NameBeans.BeanActiveLabelAuthUI.getValue(),
                text);
    }

    public JvButtonAuthUI getBeanButtonAuthUI(String text) {
        return (JvButtonAuthUI) context.getBean(
                JvAuthUIComponentsSpringConfig.NameBeans.BeanButtonAuthUI.getValue(),
                text);
    }

    public JvErrorLabelAuthUI getBeanErrorLabelAuthUI(String text) {
        return (JvErrorLabelAuthUI) context.getBean(
                JvAuthUIComponentsSpringConfig.NameBeans.BeanErrorLabelAuthUI.getValue(),
                text);
    }

    public JvOptionPaneAuthUI getBeanOptionPaneAuthUI(String msg, JvOptionPaneAuthUI.TypeDlg type) {
        return (JvOptionPaneAuthUI) context.getBean(
                JvAuthUIComponentsSpringConfig.NameBeans.BeanOptionPaneAuthUI.getValue(),
                msg, type);
    }

    public JvPasswordFieldAuthUI getBeanPasswordFieldAuthUI(String text) {
        return (JvPasswordFieldAuthUI) context.getBean(
                JvAuthUIComponentsSpringConfig.NameBeans.BeanPasswordFieldAuthUI.getValue(),
                text);
    }

    public JvTextFieldAuthUI getBeanTextFieldAuthUI(String text) {
        return (JvTextFieldAuthUI) context.getBean(
                JvAuthUIComponentsSpringConfig.NameBeans.BeanTextFieldAuthUI.getValue(),
                text);
    }

    public JvTitlePanelAuthUI getBeanTitlePanelAuthUI(String text) {
        return (JvTitlePanelAuthUI) context.getBean(
                JvAuthUIComponentsSpringConfig.NameBeans.BeanTitlePanelAuthUI.getValue(),
                text);
    }

    public JvToolTipAuthUI getBeanToolTipAuthUI() {
        return context.getBean(JvAuthUIComponentsSpringConfig.NameBeans.BeanToolTipAuthUI.getValue(),
                JvToolTipAuthUI.class);
    }

    public JvEntryFrameAuthUI getBeanEntryFrameAuthUI() {
        return context.getBean(JvAuthUIComponentsSpringConfig.NameBeans.BeanEntryFrameAuthUI.getValue(),
                JvEntryFrameAuthUI.class);

    }

    public JvNewPasswordFrameAuthUI getBeanNewPasswordFrameAuthUI(String post) {
        return (JvNewPasswordFrameAuthUI) context.getBean(
                JvAuthUIComponentsSpringConfig.NameBeans.BeanNewPasswordFrameAuthUI.getValue(),
                post);
    }

    public JvRegistrationFrameAuthUI getBeanRegistrationFrameAuthUI() {
        return context.getBean(JvAuthUIComponentsSpringConfig.NameBeans.BeanRegistrationFrameAuthUI.getValue(),
                JvRegistrationFrameAuthUI.class);
    }

    public JvResetPasswordFrameAuthUI getBeanResetPasswordFrameAuthUI() {
        return context.getBean(JvAuthUIComponentsSpringConfig.NameBeans.BeanResetPasswordFrameAuthUI.getValue(),
                JvResetPasswordFrameAuthUI.class);
    }

    public JvVerifyCodeFrameAuthUI getBeanVerifyCodeFrameAuthUI(JvVerifyCodeFrameAuthUI.RegimeWork rw) {
        return (JvVerifyCodeFrameAuthUI) context.getBean(
                JvAuthUIComponentsSpringConfig.NameBeans.BeanVerifyCodeFrameAuthUI.getValue(),
                rw);
    }
}