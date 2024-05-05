package org.foomaa.jvchat.network;

import org.foomaa.jvchat.settings.JvMainSettings;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

public class JvEmailProcessor {
    private static JvEmailProcessor instance;
    private static Session session;
    private final String host = "smtp.mail.ru";
    private final String userLogin = JvMainSettings.getEmailAddress();
    private final String userPassword = JvMainSettings.getMagicStringEmail();
    private final int port = 465;

    private JvEmailProcessor() {
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

    public static JvEmailProcessor getInstance() {
        if(instance == null) {
            instance = new JvEmailProcessor();
        }
        return instance;
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
            System.out.println("Ошибка при отправке письма");
            return false;
        }
        return true;
    }
}