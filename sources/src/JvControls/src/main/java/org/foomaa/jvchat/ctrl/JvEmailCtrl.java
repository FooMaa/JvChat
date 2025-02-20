package org.foomaa.jvchat.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;

import org.foomaa.jvchat.network.JvEmailProcessor;


public class JvEmailCtrl {
    private JvEmailProcessor emailProcessor;

    JvEmailCtrl() {}

    @Autowired(required = false)
    @Qualifier("beanEmailProcessor")
    @Profile("servers")
    @SuppressWarnings("unused")
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
                    .getBeanDbCtrl().insertQueryToDB(JvDbCtrl.TypeExecutionInsert.VerifyFamousEmail,
                    email, String.valueOf(code));
        }
        return false;
    }

    private String createVerifyFamousEmailMessage(int code, String email){
        return String.format(
                "You have requested a password recovery. Your code: %d. Your login: %s. " +
                        "The code is valid for 60 seconds; after the time expires, you must order a new one. " +
                        "Don't tell or send the code to anyone. " +
                        "If it was not you, contact support by email avodichenkov@gmail.com.",
                code,
                JvGetterControls.getInstance().getBeanDbCtrl().
                        getSingleDataFromDb(JvDbCtrl.TypeExecutionGetSingle.LoginByEmail, email));
    }

    public boolean startVerifyRegEmail(String email) {
        int code = (int) ((Math.random() * (999999 - 100000) ) + 100000);
        String message =  createVerifyRegEmailMessage(code);
        if (emailProcessor.sendEmail(email, message)) {
            return JvGetterControls.getInstance()
                    .getBeanDbCtrl().insertQueryToDB(JvDbCtrl.TypeExecutionInsert.VerifyRegistrationEmail,
                    email, String.valueOf(code));
        }
        return false;
    }

    private String createVerifyRegEmailMessage(int code){
        return String.format(
                "You register in the program, enter the code to confirm your email. Your code: %d. " +
                        "The code is valid for 60 seconds; after the time expires, you must order a new one. " +
                        "Don't tell or send the code to anyone. " +
                        "If it was not you, contact support by email avodichenkov@gmail.com.",
                code);
    }
}