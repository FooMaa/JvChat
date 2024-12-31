package org.foomaa.jvchat.dbworker;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component("beanDbRequests")
@Scope("singleton")
@Profile("servers")
public class JvDbRequests {
    private JvDbRequests() {}

    public String insertToRegForm(String login, String email, String password, String uuid) {
        return String.format(
                "SELECT * FROM jvchat_schema.auth_users_info_save('%s', '%s', '%s', '%s');",
                login,
                email,
                password,
                uuid);
    }

    public String insertCodeVerifyFamousEmail(String userUuid, String code) {
        return String.format(
                "SELECT * FROM jvchat_schema.verify_famous_email_save( %d,'%s');",
                userUuid,
                code);
    }

    public String insertChangePassword(String email, String password) {
        return String.format(
                "SELECT * FROM jvchat_schema.auth_users_info_change_password('%s', '%s');",
                email,
                password);
    }

    public String insertVerifyRegistrationEmail(String email, String code) {
        return String.format(
                "SELECT * FROM jvchat_schema.verify_registration_email_save( '%s','%s');",
                email,
                code);
    }

    public String insertOnlineUsersInfo(String login, String status) {
        return String.format(
                "SELECT * FROM jvchat_schema.online_users_info_save('%s', %s);",
                login,
                status);
    }

    public String insertChatsSentMessage(String loginSender, String loginReceiver, String status,
                                         String message, String uuidMessage, String datetime) {
        return String.format(
                "SELECT * FROM jvchat_schema.chats_messages_save_message('%s', '%s', %s, '%s', '%s', '%s');",
                loginSender,
                loginReceiver,
                status,
                message,
                uuidMessage,
                datetime);
    }

    public String insertChatsMessageStatusChange(String uuidMessage, String status) {
        return String.format(
                "SELECT * FROM jvchat_schema.chats_messages_save_message('%s', %s);",
                uuidMessage,
                status);
    }

    public String checkUserPassword(String login, String password) {
        return String.format(
                "SELECT * FROM jvchat_schema.auth_users_info_check_login_password('%s', '%s');",
                login,
                password);
    }

    public String checkLogin(String login) {
        return String.format(
                "SELECT * FROM jvchat_schema.auth_users_info_check_login('%s');",
                login);
    }

    public String checkEmail(String email) {
        return String.format(
                "SELECT * FROM jvchat_schema.auth_users_info_check_email('%s');",
                email);
    }

    public String checkVerifyFamousEmailCode(String email, String code) {
        return String.format(
                "SELECT * FROM jvchat_schema.verify_famous_email_check_email_code('%s', '%s');",
                email,
                code);
    }

    public String checkVerifyRegistrationEmail(String email, String code) {
        return String.format(
                "SELECT * FROM jvchat_schema.verify_registration_email_check_email_code('%s', '%s');",
                email,
                code);
    }

    public String getUserUuidByEmail(String email) {
        return String.format(
                "SELECT * FROM jvchat_schema.auth_users_info_get_uuid_by_email('%s');",
                email);
    }

    public String getUserUuidByLogin(String login) {
        return String.format(
                "SELECT * FROM jvchat_schema.auth_users_info_get_uuid_by_login('%s');",
                login);
    }

    public String getLogin(String email) {
        return String.format(
                "SELECT * FROM jvchat_schema.auth_users_info_get_login_by_email('%s');",
                email);
    }

    public String getChats(String login) {
        return String.format(
                "SELECT * FROM jvchat_schema.chats_messages_get_chats_by_login('%s');",
                login);
    }

    public String getStatusOnlineTimeUser(String login) {
        return String.format(
                "SELECT * FROM jvchat_schema.online_users_info_get_status_time_by_user_login('%s');",
                login);
    }

    public String getOnlineUsers() {
        return "SELECT * FROM jvchat_schema.online_users_info_get_online_users();";
    }

    public String getLastOnlineTimeUser(String login) {
        return String.format(
                "SELECT * FROM jvchat_schema.online_users_info_get_time_by_user_login('%s');",
                login);
    }

    public String getQuantityMessagesByLogins(String loginOne, String loginTwo, String quantity) {
        return String.format(
                "SELECT * FROM jvchat_schema.chats_messages_get_quantity_messages_by_logins('%s', '%s', %s);",
                loginOne,
                loginTwo,
                quantity);
    }
}