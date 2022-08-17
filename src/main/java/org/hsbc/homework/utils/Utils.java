package org.hsbc.homework.utils;

import org.hsbc.homework.utils.exception.BusinessException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.hsbc.homework.utils.exception.ErrorCode.SYSTEM_ERROR;

public class Utils {


    public static String md5(String raw) {
        try {
            byte[] bytesOfMessage = raw.getBytes("UTF-8");

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(bytesOfMessage);

            return bytesToHex(digest);
        } catch (UnsupportedEncodingException ue) {
            throw new BusinessException(SYSTEM_ERROR);
        } catch (NoSuchAlgorithmException nae) {
            throw new BusinessException(SYSTEM_ERROR);
        }
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
