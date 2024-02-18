package org.foomaa.jvchat.dbworker;

public class JvDbDefines {
    public static String insertToRegForm(String login, String password) {
        return String.format(
                "SELECT * FROM chat_schema.logins_passwords_save('%s', '%s');",
                login,
                password);
    }

    public static String checkUserPassword(String login, String password) {
        return String.format(
                "SELECT * FROM chat_schema.logins_passwords_check_login_password('%s', '%s');",
                login,
                password);
    }

    public static String checkLogin(String login) {
        return String.format(
                "SELECT * FROM chat_schema.logins_passwords_check_login('%s');",
                login);
    }
}
