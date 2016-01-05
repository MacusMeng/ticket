package com.ibm.pmc.ticket.common.crypto;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class HmacSHA256 {

    private static Logger logger = LoggerFactory.getLogger(HmacSHA256.class);

    private static String HMACSHA256 = "HmacSHA256";

    public static String generateEncryptKey() {
        try {
            SecretKey secretKey = KeyGenerator.getInstance(HMACSHA256).generateKey();
            return Base64.encodeBase64String(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            logger.error("HMAC generate key exception", e);
            throw new CryptoException("HMAC generate key exception", e);
        }
    }

    public static String encrypt(String origin, String encryptKey) {
        try {
            SecretKey secretKey = new SecretKeySpec(Base64.decodeBase64(encryptKey), HMACSHA256);
            Mac mac = Mac.getInstance(HMACSHA256);
            mac.init(secretKey);

            return Base64.encodeBase64String(mac.doFinal(origin.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            logger.error("HMAC encrypt exception", e);
            throw new CryptoException("HMAC encrypt exception", e);
        }
    }

}
