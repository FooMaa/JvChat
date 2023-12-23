package org.foomaa.jvchat.dbworker;

public class JvDbDefines {
    public static final String exec = "select * from chat_schema.logins_passwords ;";
    public static String insertFromRegForm(String login, String password) {
        String start = "SELECT * FROM chat_schema.logins_passwords_save(";
        System.out.println(String.join("", login, ",", password, ");"));
        return String.join("", login, ",", password, ");");
    }
}
