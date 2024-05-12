package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.network.JvEmailProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;

public class JvEmailCtrl {
    private static JvEmailCtrl instance;
    private JvEmailProcessor emailProcessor;

    private JvEmailCtrl() {}

    static JvEmailCtrl getInstance() {
        if (instance == null) {
            instance = new JvEmailCtrl();
        }
        return instance;
    }

    @Autowired(required = false)
    @Qualifier("beanEmailProcessor")
    @Profile("servers")
    private void setEmailProcessor(JvEmailProcessor newEmailProcessor) {
        if (emailProcessor != newEmailProcessor) {
            emailProcessor = newEmailProcessor;
        }
    }

    public boolean startVerifyFamousEmail(String email) {
        int code = (int) ((Math.random() * (999999 - 100000) ) + 100000);
        String message =  createVerifyFamousEmailMessage(code, email);
        if (emailProcessor.sendEmail(email, message)) {
            return JvGetterControls.getInstance()
                    .getDbCtrl().insertQueryToDB(JvDbCtrl.TypeExecutionInsert.VerifyFamousEmail,
                    email, String.valueOf(code));
        }
        return false;
    }

    private String createVerifyFamousEmailMessage(int code, String email){
        return String.format(
                "Вы запросили восстановление пароля. Ваш код: %d. Ваш логин: %s. " +
                        "Код действителен в течение 60 секунд, по истечении времени следует заказать новый. " +
                        "Никому не говорите и не отправляйте код. " +
                        "Если это были не вы, свяжитесь с поддержкой по почте avodichenkov@mail.ru.",
                code,
                JvGetterControls.getInstance().getDbCtrl().
                        getInfoFromDb(JvDbCtrl.TypeExecutionGet.LoginByEmail, email));
    }

    public boolean startVerifyRegEmail(String email) {
        int code = (int) ((Math.random() * (999999 - 100000) ) + 100000);
        String message =  createVerifyRegEmailMessage(code);
        if (emailProcessor.sendEmail(email, message)) {
            return JvGetterControls.getInstance()
                    .getDbCtrl().insertQueryToDB(JvDbCtrl.TypeExecutionInsert.VerifyRegistrationEmail,
                    email, String.valueOf(code));
        }
        return false;
    }

    private String createVerifyRegEmailMessage(int code){
        return String.format(
                "Вы регистрируетесь в программе, для подтверждения почты введите код. Ваш код: %d. " +
                        "Код действителен в течение 60 секунд, по истечении времени следует заказать новый. " +
                        "Никому не говорите и не отправляйте код. " +
                        "Если это были не вы, свяжитесь с поддержкой по почте avodichenkov@mail.ru.",
                code);
    }
}