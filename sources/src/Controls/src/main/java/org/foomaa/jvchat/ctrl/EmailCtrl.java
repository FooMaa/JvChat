package org.foomaa.jvchat.ctrl;

import java.util.Objects;

import lombok.Builder;

import org.foomaa.jvchat.network.EmailProcessor;

public class EmailCtrl {
    private final EmailProcessor emailProcessor;
    private final DbCtrl dbCtrl;

    @Builder
    EmailCtrl(EmailProcessor emailProcessor, DbCtrl dbCtrl) {
        this.emailProcessor = Objects.requireNonNull(emailProcessor, "emailProcessor is mandatory");
        this.dbCtrl = Objects.requireNonNull(dbCtrl, "dbCtrl is mandatory");
    }

    public boolean startVerifyFamousEmail(String email) {
        int code = (int) ((Math.random() * (999999 - 100000)) + 100000);
        String message = createVerifyFamousEmailMessage(code, email);
        if (emailProcessor.sendEmail(email, message)) {
            return dbCtrl.insertQueryToDB(DbCtrl.TypeExecutionInsert.VerifyFamousEmail, email, String.valueOf(code));
        }
        return false;
    }

    private String createVerifyFamousEmailMessage(int code, String email) {
        return String.format(
                "You have requested a password recovery. Your code: %d. Your login: %s. " + "The code is valid for 60 seconds; after the time expires, you must order a new one. " + "Don't tell or send the code to anyone. " + "If it was not you, contact support by email avodichenkov@gmail.com.",
                code, dbCtrl.getSingleDataFromDb(DbCtrl.TypeExecutionGetSingle.LoginByEmail, email));
    }

    public boolean startVerifyRegEmail(String email) {
        int code = (int) ((Math.random() * (999999 - 100000)) + 100000);
        String message = createVerifyRegEmailMessage(code);
        if (emailProcessor.sendEmail(email, message)) {
            return dbCtrl.insertQueryToDB(DbCtrl.TypeExecutionInsert.VerifyRegistrationEmail, email,
                    String.valueOf(code));
        }
        return false;
    }

    private String createVerifyRegEmailMessage(int code) {
        return String.format(
                "You register in the program, enter the code to confirm your email. Your code: %d. " + "The code is valid for 60 seconds; after the time expires, you must order a new one. " + "Don't tell or send the code to anyone. " + "If it was not you, contact support by email avodichenkov@gmail.com.",
                code);
    }
}
