package com.maxeagan;

import java.security.MessageDigest;

public class StringUtil {

    // Apply Sha256 to a string and return result.
    public static String applySha256(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            // Appies sha256 algorithm to out input
            byte[] hash = messageDigest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer(); // Contain hash as a hexadecimal
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

