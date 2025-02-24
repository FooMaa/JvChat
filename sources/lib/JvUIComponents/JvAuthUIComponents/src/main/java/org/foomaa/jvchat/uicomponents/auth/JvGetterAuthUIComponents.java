package org.foomaa.jvchat.uicomponents.auth;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class JvGetterAuthUIComponents {
    private static JvGetterAuthUIComponents instance;
    private final AnnotationConfigApplicationContext context;

    private JvGetterAuthUIComponents() {
        context = new AnnotationConfigApplicationContext(JvAuthUIComponentsSpringConfig.class);
    }

    public static JvGetterAuthUIComponents getInstance() {
        if (instance == null) {
            instance = new JvGetterAuthUIComponents();
        }
        return instance;
    }

    public AnnotationConfigApplicationContext getContext() {
        return context;
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

    public JvToolTipAuthUI getBeanToolTipAuthUI() {
        return context.getBean(JvAuthUIComponentsSpringConfig.NameBeans.BeanToolTipAuthUI.getValue(),
                JvToolTipAuthUI.class);
    }

    public JvEntryPanelAuthUI getBeanEntryPanelAuthUI() {
        return context.getBean(JvAuthUIComponentsSpringConfig.NameBeans.BeanEntryPanelAuthUI.getValue(),
                JvEntryPanelAuthUI.class);

    }

    public JvMainFrameAuthUI getBeanMainFrameAuthUI() {
        return context.getBean(JvAuthUIComponentsSpringConfig.NameBeans.BeanMainFrameAuthUI.getValue(),
                JvMainFrameAuthUI.class);

    }

    public JvNewPasswordFrameAuthUI getBeanNewPasswordFrameAuthUI(String post) {
        return (JvNewPasswordFrameAuthUI) context.getBean(
                JvAuthUIComponentsSpringConfig.NameBeans.BeanNewPasswordFrameAuthUI.getValue(),
                post);
    }

    public JvRegistrationPanelAuthUI getBeanRegistrationPanelAuthUI() {
        return context.getBean(JvAuthUIComponentsSpringConfig.NameBeans.BeanRegistrationFrameAuthUI.getValue(),
                JvRegistrationPanelAuthUI.class);
    }

    public JvResetPasswordFrameAuthUI getBeanResetPasswordFrameAuthUI() {
        return context.getBean(JvAuthUIComponentsSpringConfig.NameBeans.BeanResetPasswordFrameAuthUI.getValue(),
                JvResetPasswordFrameAuthUI.class);
    }

    public JvVerifyCodePanelAuthUI getBeanVerifyCodePanelAuthUI(JvVerifyCodePanelAuthUI.RegimeWork rw) {
        return (JvVerifyCodePanelAuthUI) context.getBean(
                JvAuthUIComponentsSpringConfig.NameBeans.BeanVerifyCodePanelAuthUI.getValue(),
                rw);
    }

    public JvTitlePanelAuthUI getBeanTitlePanelAuthUI() {
        return context.getBean(
                JvAuthUIComponentsSpringConfig.NameBeans.BeanTitlePanelAuthUI.getValue(), JvTitlePanelAuthUI.class);
    }

    public JvDefinesAuthUI getBeanDefinesAuthUI() {
        return context.getBean(
                JvAuthUIComponentsSpringConfig.NameBeans.BeanDefinesAuthUI.getValue(), JvDefinesAuthUI.class);
    }
}