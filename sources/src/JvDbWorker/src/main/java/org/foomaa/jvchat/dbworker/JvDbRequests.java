package org.foomaa.jvchat.dbworker;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("beanDbRequests")
@Scope("singleton")
@Profile("servers")
public class JvDbRequests {
    private JvDbRequests() {}

    public String insertToRegForm(String login, String email, String password) {
        return String.format(
                "SELECT * FROM chat_schema.auth_users_info_save('%s', '%s', '%s');",
                login,
                email,
                password);
    }

    public String insertCodeVerifyFamousEmail(int userId, String code) {
        return String.format(
                "SELECT * FROM chat_schema.verify_famous_email_save( %d,'%s');",
                userId,
                code);
    }

    public String insertChangePassword(String email, String password) {
        return String.format(
                "SELECT * FROM chat_schema.auth_users_info_change_password('%s', '%s');",
                email,
                password);
    }

    public String insertVerifyRegistrationEmail(String email, String code) {
        return String.format(
                "SELECT * FROM chat_schema.verify_registration_email_save( '%s','%s');",
                email,
                code);
    }

    public String checkUserPassword(String login, String password) {
        return String.format(
                "SELECT * FROM chat_schema.auth_users_info_check_login_password('%s', '%s');",
                login,
                password);
    }

    public String checkLogin(String login) {
        return String.format(
                "SELECT * FROM chat_schema.auth_users_info_check_login('%s');",
                login);
    }

    public String checkEmail(String email) {
        return String.format(
                "SELECT * FROM chat_schema.auth_users_info_check_email('%s');",
                email);
    }

    public String checkVerifyFamousEmailCode(String email, String code) {
        return String.format(
                "SELECT * FROM chat_schema.verify_famous_email_check_email_code('%s', '%s');",
                email,
                code);
    }

    public String checkVerifyRegistrationEmail(String email, String code) {
        return String.format(
                "SELECT * FROM chat_schema.verify_registration_email_check_email_code('%s', '%s');",
                email,
                code);
    }

    public String getUserId(String email) {
        return String.format(
                "SELECT * FROM chat_schema.auth_users_info_get_id_by_email('%s');",
                email);
    }

    public String getLogin(String email) {
        return String.format(
                "SELECT * FROM chat_schema.auth_users_info_get_login_by_email('%s');",
                email);
    }

    public String getChats(String login) {
        return String.format(
                "SELECT * FROM chat_schema.chats_messages_get_chats_by_login('%s');",
                login);
    }
}