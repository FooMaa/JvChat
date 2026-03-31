package org.foomaa.jvchat.uicomponents.auth;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.*;

import org.foomaa.jvchat.ctrl.MessagesDefinesCtrl;
import org.foomaa.jvchat.ctrl.SendMessagesCtrl;
import org.foomaa.jvchat.globaldefines.FontsGlobalDefines;
import org.foomaa.jvchat.settings.DisplaySettings;
import org.foomaa.jvchat.settings.UsersInfoSettings;
import org.foomaa.jvchat.tools.UsersTools;
import org.foomaa.jvchat.uicomponents.mainchat.MainFrameMainChatUI;

@Configuration
public class AuthUIConfig {
    @Bean
    @Scope("prototype")
    @Profile("users")
    public ActiveLabelAuthUI beanActiveLabelAuthUI(DisplaySettings displaySettings,
            FontsGlobalDefines fontsGlobalDefines, ToolTipAuthUIFactory toolTipAuthUIFactory) {
        return ActiveLabelAuthUI.builder().displaySettings(displaySettings)
                .fontsGlobalDefines(fontsGlobalDefines).toolTipAuthUIFactory(toolTipAuthUIFactory)
                .build();
    }

    @Bean
    @Profile("users")
    public ActiveLabelAuthUIFactory beanActiveLabelAuthUIFactory(
            ObjectProvider<ActiveLabelAuthUI> activeLabelAuthUIObjectProvider) {
        return ActiveLabelAuthUIFactory.builder()
                .activeLabelAuthUIObjectProvider(activeLabelAuthUIObjectProvider).build();
    }

    @Bean
    @Scope("prototype")
    @Profile("users")
    public ButtonAuthUI beanButtonAuthUI(DisplaySettings displaySettings,
            FontsGlobalDefines fontsGlobalDefines, ToolTipAuthUIFactory toolTipAuthUIFactory) {
        return ButtonAuthUI.builder().displaySettings(displaySettings)
                .fontsGlobalDefines(fontsGlobalDefines).toolTipAuthUIFactory(toolTipAuthUIFactory)
                .build();
    }

    @Bean
    @Profile("users")
    public ButtonAuthUIFactory beanButtonAuthUIFactory(
            ObjectProvider<ButtonAuthUI> buttonAuthUIObjectProvider) {
        return ButtonAuthUIFactory.builder().buttonAuthUIObjectProvider(buttonAuthUIObjectProvider)
                .build();
    }

    @Bean
    @Profile("users")
    public DefinesAuthUI beanDefinesAuthUI() {
        return DefinesAuthUI.builder().build();
    }

    @Bean
    @Profile("users")
    public EntryPanelAuthUI beanEntryPanelAuthUI(UsersInfoSettings usersInfoSettings,
            DisplaySettings displaySettings, @Lazy MainFrameMainChatUI mainFrameMainChatUI,
            SendMessagesCtrl sendMessagesCtrl, MessagesDefinesCtrl messagesDefinesCtrl,
            ActiveLabelAuthUIFactory activeLabelAuthUIFactory,
            ButtonAuthUIFactory buttonAuthUIFactory,
            ErrorLabelAuthUIFactory errorLabelAuthUIFactory,
            PasswordFieldAuthUIFactory passwordFieldAuthUIFactory,
            TextFieldAuthUIFactory textFieldAuthUIFactory,
            OptionPaneAuthUIFactory optionPaneAuthUIFactory) {
        return EntryPanelAuthUI.builder().usersInfoSettings(usersInfoSettings)
                .displaySettings(displaySettings).mainFrameMainChatUI(mainFrameMainChatUI)
                .sendMessagesCtrl(sendMessagesCtrl).messagesDefinesCtrl(messagesDefinesCtrl)
                .activeLabelAuthUIFactory(activeLabelAuthUIFactory)
                .buttonAuthUIFactory(buttonAuthUIFactory)
                .errorLabelAuthUIFactory(errorLabelAuthUIFactory)
                .passwordFieldAuthUIFactory(passwordFieldAuthUIFactory)
                .textFieldAuthUIFactory(textFieldAuthUIFactory)
                .optionPaneAuthUIFactory(optionPaneAuthUIFactory).build();
    }

    @Bean
    @Scope("prototype")
    @Profile("users")
    public ErrorLabelAuthUI beanErrorLabelAuthUI(DisplaySettings displaySettings,
            FontsGlobalDefines fontsGlobalDefines) {
        return ErrorLabelAuthUI.builder().displaySettings(displaySettings)
                .fontsGlobalDefines(fontsGlobalDefines).build();
    }

    @Bean
    @Profile("users")
    public ErrorLabelAuthUIFactory beanErrorLabelAuthUIFactory(
            ObjectProvider<ErrorLabelAuthUI> errorLabelAuthUIObjectProvider) {
        return ErrorLabelAuthUIFactory.builder()
                .errorLabelAuthUIObjectProvider(errorLabelAuthUIObjectProvider).build();
    }

    @Bean
    @Lazy
    @Profile("users")
    public MainFrameAuthUI beanMainFrameAuthUI(DisplaySettings displaySettings,
            EntryPanelAuthUI entryPanelAuthUI, NewPasswordPanelAuthUI newPasswordPanelAuthUI,
            RegistrationPanelAuthUI registrationPanelAuthUI,
            ResetPasswordPanelAuthUI resetPasswordPanelAuthUI,
            VerifyCodePanelAuthUI verifyCodePanelAuthUI, TitlePanelAuthUI titlePanel) {
        return MainFrameAuthUI.builder().displaySettings(displaySettings)
                .entryPanelAuthUI(entryPanelAuthUI).newPasswordPanelAuthUI(newPasswordPanelAuthUI)
                .registrationPanelAuthUI(registrationPanelAuthUI)
                .resetPasswordPanelAuthUI(resetPasswordPanelAuthUI)
                .verifyCodePanelAuthUI(verifyCodePanelAuthUI).titlePanel(titlePanel).build();
    }

    @Bean
    @Profile("users")
    public NewPasswordPanelAuthUI beanNewPasswordPanelAuthUI(DisplaySettings displaySettings,
            SendMessagesCtrl sendMessagesCtrl, MessagesDefinesCtrl messagesDefinesCtrl,
            ButtonAuthUIFactory buttonAuthUIFactory,
            ErrorLabelAuthUIFactory errorLabelAuthUIFactory,
            PasswordFieldAuthUIFactory passwordFieldAuthUIFactory,
            OptionPaneAuthUIFactory optionPaneAuthUIFactory) {
        return NewPasswordPanelAuthUI.builder().displaySettings(displaySettings)
                .sendMessagesCtrl(sendMessagesCtrl).messagesDefinesCtrl(messagesDefinesCtrl)
                .buttonAuthUIFactory(buttonAuthUIFactory)
                .errorLabelAuthUIFactory(errorLabelAuthUIFactory)
                .passwordFieldAuthUIFactory(passwordFieldAuthUIFactory)
                .optionPaneAuthUIFactory(optionPaneAuthUIFactory).build();
    }

    @Bean
    @Scope("prototype")
    @Profile("users")
    public OptionPaneAuthUI beanOptionPaneAuthUI() {
        return OptionPaneAuthUI.builder().build();
    }

    @Bean
    @Profile("users")
    public OptionPaneAuthUIFactory beanOptionPaneAuthUIFactory(
            ObjectProvider<OptionPaneAuthUI> optionPaneAuthUIObjectProvider) {
        return OptionPaneAuthUIFactory.builder()
                .optionPaneAuthUIObjectProvider(optionPaneAuthUIObjectProvider).build();
    }

    @Bean
    @Scope("prototype")
    @Profile("users")
    public PasswordFieldAuthUI beanPasswordFieldAuthUI(DisplaySettings displaySettings,
            FontsGlobalDefines fontsGlobalDefines, ToolTipAuthUIFactory toolTipAuthUIFactory) {
        return PasswordFieldAuthUI.builder().displaySettings(displaySettings)
                .fontsGlobalDefines(fontsGlobalDefines).toolTipAuthUIFactory(toolTipAuthUIFactory)
                .build();
    }

    @Bean
    @Profile("users")
    public PasswordFieldAuthUIFactory beanPasswordFieldAuthUIFactory(
            ObjectProvider<PasswordFieldAuthUI> passwordFieldAuthUIObjectProvider) {
        return PasswordFieldAuthUIFactory.builder()
                .passwordFieldAuthUIObjectProvider(passwordFieldAuthUIObjectProvider).build();
    }

    @Bean
    @Profile("users")
    public RegistrationPanelAuthUI beanRegistrationPanelAuthUI(DisplaySettings displaySettings,
            UsersTools usersTools, SendMessagesCtrl sendMessagesCtrl,
            MessagesDefinesCtrl messagesDefinesCtrl, ButtonAuthUIFactory buttonAuthUIFactory,
            ErrorLabelAuthUIFactory errorLabelAuthUIFactory,
            PasswordFieldAuthUIFactory passwordFieldAuthUIFactory,
            TextFieldAuthUIFactory textFieldAuthUIFactory,
            OptionPaneAuthUIFactory optionPaneAuthUIFactory) {
        return RegistrationPanelAuthUI.builder().displaySettings(displaySettings)
                .usersTools(usersTools).sendMessagesCtrl(sendMessagesCtrl)
                .messagesDefinesCtrl(messagesDefinesCtrl).buttonAuthUIFactory(buttonAuthUIFactory)
                .errorLabelAuthUIFactory(errorLabelAuthUIFactory)
                .passwordFieldAuthUIFactory(passwordFieldAuthUIFactory)
                .textFieldAuthUIFactory(textFieldAuthUIFactory)
                .optionPaneAuthUIFactory(optionPaneAuthUIFactory).build();
    }

    @Bean
    @Profile("users")
    public ResetPasswordPanelAuthUI beanResetPasswordPanelAuthUI(DisplaySettings displaySettings,
            UsersTools usersTools, SendMessagesCtrl sendMessagesCtrl,
            MessagesDefinesCtrl messagesDefinesCtrl, ButtonAuthUIFactory buttonAuthUIFactory,
            ErrorLabelAuthUIFactory errorLabelAuthUIFactory,
            TextFieldAuthUIFactory textFieldAuthUIFactory,
            OptionPaneAuthUIFactory optionPaneAuthUIFactory) {
        return ResetPasswordPanelAuthUI.builder().displaySettings(displaySettings)
                .usersTools(usersTools).sendMessagesCtrl(sendMessagesCtrl)
                .messagesDefinesCtrl(messagesDefinesCtrl).buttonAuthUIFactory(buttonAuthUIFactory)
                .errorLabelAuthUIFactory(errorLabelAuthUIFactory)
                .textFieldAuthUIFactory(textFieldAuthUIFactory)
                .optionPaneAuthUIFactory(optionPaneAuthUIFactory).build();
    }

    @Bean
    @Scope("prototype")
    @Profile("users")
    public TextFieldAuthUI beanTextFieldAuthUI(DisplaySettings displaySettings,
            FontsGlobalDefines fontsGlobalDefines, ToolTipAuthUIFactory toolTipAuthUIFactory) {
        return TextFieldAuthUI.builder().displaySettings(displaySettings)
                .fontsGlobalDefines(fontsGlobalDefines).toolTipAuthUIFactory(toolTipAuthUIFactory)
                .build();
    }

    @Bean
    @Profile("users")
    public TextFieldAuthUIFactory beanTextFieldAuthUIFactory(
            ObjectProvider<TextFieldAuthUI> textFieldAuthUIObjectProvider) {
        return TextFieldAuthUIFactory.builder()
                .textFieldAuthUIObjectProvider(textFieldAuthUIObjectProvider).build();
    }

    @Bean
    @Profile("users")
    public TitlePanelAuthUI beanTitlePanelAuthUI(DisplaySettings displaySettings,
            FontsGlobalDefines fontsGlobalDefines, ToolTipAuthUIFactory toolTipAuthUIFactory) {
        return TitlePanelAuthUI.builder().displaySettings(displaySettings)
                .fontsGlobalDefines(fontsGlobalDefines).toolTipAuthUIFactory(toolTipAuthUIFactory)
                .build();
    }

    @Bean
    @Scope("prototype")
    @Profile("users")
    public ToolTipAuthUI beanToolTipAuthUI(DisplaySettings displaySettings,
            FontsGlobalDefines fontsGlobalDefines) {
        return ToolTipAuthUI.builder().displaySettings(displaySettings)
                .fontsGlobalDefines(fontsGlobalDefines).build();
    }

    @Bean
    @Profile("users")
    public ToolTipAuthUIFactory beanToolTipAuthUIFactory(
            ObjectProvider<ToolTipAuthUI> toolTipAuthUIObjectProvider) {
        return ToolTipAuthUIFactory.builder()
                .toolTipAuthUIObjectProvider(toolTipAuthUIObjectProvider).build();
    }

    @Bean
    @Profile("users")
    public VerifyCodePanelAuthUI beanVerifyCodePanelAuthUI(DisplaySettings displaySettings,
            SendMessagesCtrl sendMessagesCtrl, MessagesDefinesCtrl messagesDefinesCtrl,
            ButtonAuthUIFactory buttonAuthUIFactory,
            ErrorLabelAuthUIFactory errorLabelAuthUIFactory,
            TextFieldAuthUIFactory textFieldAuthUIFactory,
            OptionPaneAuthUIFactory optionPaneAuthUIFactory) {
        return VerifyCodePanelAuthUI.builder().displaySettings(displaySettings)
                .sendMessagesCtrl(sendMessagesCtrl).messagesDefinesCtrl(messagesDefinesCtrl)
                .buttonAuthUIFactory(buttonAuthUIFactory)
                .errorLabelAuthUIFactory(errorLabelAuthUIFactory)
                .textFieldAuthUIFactory(textFieldAuthUIFactory)
                .optionPaneAuthUIFactory(optionPaneAuthUIFactory).build();
    }
}
