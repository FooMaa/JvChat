package org.foomaa.jvchat.cryptography;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.foomaa.jvchat.logger.Log;
import org.springframework.stereotype.Component;


@Component
public class HashCryptography {
    HashCryptography() {}

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
            Log.write(Log.TypeLog.Error, "Error when taking string hash.");
        }
        return result;
    }
}