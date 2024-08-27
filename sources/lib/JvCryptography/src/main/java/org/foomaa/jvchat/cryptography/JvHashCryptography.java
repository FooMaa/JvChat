package org.foomaa.jvchat.cryptography;

import org.foomaa.jvchat.logger.JvLog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;


public class JvHashCryptography {
    private static JvHashCryptography instance;

    private JvHashCryptography() {}

    public static JvHashCryptography getInstance() {
        if (instance == null) {
            instance = new JvHashCryptography();
        }
        return instance;
    }

    public String getHash(String inputString) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(inputString.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            result = hexString.toString();
        } catch (NoSuchAlgorithmException exception) {
            JvLog.write(JvLog.TypeLog.Error, "Ошибка взятия хеша от строки");
        }
        return result;
    }
}