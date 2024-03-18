package org.foomaa.jvchat.network;

import org.foomaa.jvchat.settings.JvMainSettings;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

public class JvEmailProcessor {
    private static JvEmailProcessor instance;
    private static Session session;

    private JvEmailProcessor() {
        String host = "smtp.mail.ru";
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "465");
        // for mail.ru
        props.put("mail.smtps.ssl.checkserveridentity", true);
        props.put("mail.smtps.ssl.trust", "*");
        props.put("mail.smtp.ssl.enable", "true");

        session = Session.getDefaultInstance(props);
    }

    public static JvEmailProcessor getInstance() {
        if(instance == null){
            instance = new JvEmailProcessor();
        }
        return instance;
    }

    public void sendEmail() throws MessagingException {
        MimeMessage message = new MimeMessage(session);

        message.setSubject("JvChat message");
        message.setText("Hello!");
        //message.setFrom( new InternetAddress("advokat378@gmail.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("advokat378@mail.ru"));
        message.setSentDate(new Date());

        String userLogin = JvMainSettings.getEmailAddress();
        String userPassword = JvMainSettings.getMagicStringEmail();

        Transport transport = session.getTransport();
        transport.connect("smtp.mail.ru", 465, userLogin, userPassword);
        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
        transport.close();
    }
}
