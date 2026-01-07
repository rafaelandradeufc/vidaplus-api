package com.uninter.vidaplusapi.crypto;

import org.mindrot.jbcrypt.BCrypt;

public class HashUtils {

    public static String generateHash(String str) {
        String salt = BCrypt.gensalt(31);
        return BCrypt.hashpw(str, salt);
    }


    public static boolean verifyHash(String str, String hash) {
        if (str == null || hash == null) {
            return false;
        }

        try {
            return BCrypt.checkpw(str, hash);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}