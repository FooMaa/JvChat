package org.foomaa.jvchat.ctrl;

import org.foomaa.jvchat.network.JvEmailProcessor;
import org.foomaa.jvchat.settings.JvMainSettings;

public class JvEmailCtrl {
    private static JvEmailCtrl instance;
    private static JvEmailProcessor emailProc;

    private JvEmailCtrl() {
        if (JvMainSettings.getProfile() == JvMainSettings.TypeProfiles.SERVERS) {
            emailProc = JvEmailProcessor.getInstance();
        }
    }

    public static JvEmailCtrl getInstance() {
        if (instance == null) {
            instance = new JvEmailCtrl();
        }
        return instance;
    }

    public boolean startVerifyFamousEmail(String email) {
        int code = (int) ((Math.random() * (999999 - 100000) ) + 100000);
        String message =  createVerifyFamousEmailMessage(code, email);
        if (emailProc.sendEmail(email, message)) {
            JvDbCtrl.getInstance().insertQueryToDB(JvDbCtrl.TypeExecutionInsert.VerifyFamousEmail,
                    email, String.valueOf(code));
            return true;
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
                JvDbCtrl.getInstance().getInfoFromDb(JvDbCtrl.TypeExecutionGet.LoginByEmail, email));
    }

    public boolean startVerifyRegEmail(String email) {
        int code = (int) ((Math.random() * (999999 - 100000) ) + 100000);
        String message =  createVerifyRegEmailMessage(code);
        if (emailProc.sendEmail(email, message)) {
            JvDbCtrl.getInstance().insertQueryToDB(JvDbCtrl.TypeExecutionInsert.VerifyRegistrationEmail,
                    email, String.valueOf(code));
            return true;
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