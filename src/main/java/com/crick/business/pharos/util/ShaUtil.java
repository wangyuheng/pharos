package com.crick.business.pharos.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA 加密工具
 */
public class ShaUtil {

    private ShaUtil() {
    }

    private static final String SHA_1_ALGORITHM = "SHA-1";
    private static final String SHA_256_ALGORITHM = "SHA-256";

    public static String sha1(String source) {
        return sha(source, SHA_1_ALGORITHM);
    }

    public static String sha256(String source) {
        return sha(source, SHA_256_ALGORITHM);
    }

    private static String sha(String source, String instance) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(instance);
            md.update(source.getBytes());
            return new String(Hex.encodeHex(md.digest()));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
