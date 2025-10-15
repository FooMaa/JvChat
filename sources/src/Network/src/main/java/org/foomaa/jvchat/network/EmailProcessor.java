package org.foomaa.jvchat.network;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

import org.foomaa.jvchat.logger.Log;
import org.foomaa.jvchat.settings.GetterSettings;


@Component("beanEmailProcessor")
@Scope("singleton")
@Profile("servers")
public class EmailProcessor {
    private static Session session;
    private final String host;
    private final String userLogin;
    private final String userPassword;
    private final int port = 465;

    private EmailProcessor() {
        host = "smtp.mail.ru";
        userLogin = GetterSettings.getInstance().getBeanServersInfoSettings().getEmailAddress();
        userPassword = GetterSettings.getInstance().getBeanServersInfoSettings().getMagicStringEmail();

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enabled", "false");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", String.valueOf(port));
        // for mail.ru
        props.put("mail.smtps.ssl.checkserveridentity", true);
        props.put("mail.smtp.ssl.trust", "smtp.mail.ru");
        props.put("mail.smtp.ssl.enable", "true");

        session = Session.getDefaultInstance(props);
    }

    public boolean sendEmail(String email, String msg) {
        MimeMessage message = new MimeMessage(session);
        try {
            message.setSubject("JvChat message");
            message.setText(msg);
            message.setFrom(new InternetAddress(userLogin));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSentDate(new Date());

            Transport transport = session.getTransport();
            transport.connect(host, port, userLogin, userPassword);
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (MessagingException exception) {
            Log.write(Log.TypeLog.Error, "Error sending email.");
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}