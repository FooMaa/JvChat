package org.foomaa.jvchat.dbworker;

public class JvDbDefines {
    public static String insertToRegForm(String login, String email, String password) {
        return String.format(
                "SELECT * FROM chat_schema.auth_users_info_save('%s', '%s', '%s');",
                login,
                email,
                password);
    }

    public static String insertCodeVerifyFamousEmail(int userId, String code) {
        return String.format(
                "SELECT * FROM chat_schema.VerifyFamous_famous_email_save( %d,'%s');",
                userId,
                code);
    }

    public static String insertChangePassword(String email, String password) {
        return String.format(
                "SELECT * FROM chat_schema.auth_users_info_change_password('%s', '%s');",
                email,
                password);
    }

    public static String insertCodeCheckEmail(String email, String code) {
        return String.format(
                "SELECT * FROM chat_schema.check_registration_email_save( '%s','%s');",
                email,
                code);
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

    public static String checkVerifyFamousEmailCode(String email, String code) {
        return String.format(
                "SELECT * FROM chat_schema.VerifyFamous_famous_email_check_email_code('%s', '%s');",
                email,
                code);
    }

    public static String checkCheckEmailCode(String email, String code) {
        return String.format(
                "SELECT * FROM chat_schema.check_registration_email_check_email_code('%s', '%s');",
                email,
                code);
    }

    public static String getUserId(String email) {
        return String.format(
                "SELECT * FROM chat_schema.auth_users_info_get_id_by_email('%s');",
                email);
    }

    public static String getLogin(String email) {
        return String.format(
                "SELECT * FROM chat_schema.auth_users_info_get_login_by_email('%s');",
                email);
    }
}