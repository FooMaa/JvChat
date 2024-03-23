package org.foomaa.jvchat.dbworker;

public class JvDbDefines {
    public static String insertToRegForm(String login, String email, String password) {
        return String.format(
                "SELECT * FROM chat_schema.auth_users_info_save('%s', '%s', '%s');",
                login,
                email,
                password);
    }

    public static String checkUserPassword(String login, String password) {
        return String.format(
                "SELECT * FROM chat_schema.auth_users_info_check_login_password('%s', '%s');",
                login,
                password);
    }

    public static String checkLogin(String login) {
        return String.format(
                "SELECT * FROM chat_schema.auth_users_info_check_login('%s');",
                login);
    }

    public static String checkEmail(String email) {
        return String.format(
                "SELECT * FROM chat_schema.auth_users_info_check_email('%s');",
                email);
    }

    public static String getUserId(String email) {
        return String.format(
                "SELECT * FROM chat_schema.auth_users_info_get_id_by_email('%s');",
                email);
    }

    public static String insertCodeVerifyEmail(int userId, String code) {
        return String.format(
                "SELECT * FROM chat_schema.verify_email_save( %d,'%s');",
                userId,
                code);
    }

    public static String checkEmailCode(String email, String code) {
        return String.format(
                "select * from chat_schema.verify_email_check_email_code('%s', '%s');",
                email,
                code);
    }
}
