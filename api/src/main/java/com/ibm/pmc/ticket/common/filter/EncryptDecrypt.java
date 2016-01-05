package com.ibm.pmc.ticket.common.filter;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDecrypt {

    private String alg = "DES";
    private String secretKey = "HB9C11U0T3551768";

    /**
     * Encrypt a parameter
     */
    public String encryptParam(String param) throws Exception {

        String rc = null;

        byte[] bk = hexString2Byte(secretKey);
        rc = bytes2HexString(encrypt(param.getBytes(), bk));

        return rc;
    }

    /**
     * Decrypt a parameter
     */
    public String decryptParam(String param) throws Exception {

        String rc = null;

        byte[] bk = hexString2Byte(secretKey);
        rc = new String(decrypt(hexString2Byte(param), bk));

        return rc;
    }

    /**
     * Do encryption
     */
    private byte[] encrypt(byte[] data, byte[] key) throws Exception {

        SecretKey algkey = new SecretKeySpec(key, alg);
        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.ENCRYPT_MODE, algkey);
        byte[] cb = cipher.doFinal(data);

        return cb;
    }

    /**
     * Do decryption
     */
    private byte[] decrypt(byte[] data, byte[] key) throws Exception {

        SecretKey algkey = new SecretKeySpec(key, alg);
        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.DECRYPT_MODE, algkey);
        byte[] cb = cipher.doFinal(data);

        return cb;
    }

    /**
     * Convert hex String to Byte
     */
    private byte[] hexString2Byte(String hexString) {

        int length = (hexString.length() / 2);

        byte[] rc = new byte[length];
        char[] chars = hexString.toCharArray();

        for (int i = 0; i < length; i++) {

            int p = i * 2;

            rc[i] = (byte) (char2Byte(chars[p]) << 4 | char2Byte(chars[p + 1]));
        }

        return rc;
    }

    /**
     * Convert char to byte
     */
    private byte char2Byte(char c) {

        byte rc = (byte) "0123456789ABCDEF".indexOf(c);

        return rc;
    }

    /**
     * Convert bytes to hex string
     */
    private String bytes2HexString(byte[] bArray) {

        StringBuffer buffer = new StringBuffer(bArray.length);

        String tmpStr = null;
        int length = bArray.length;
        for (int i = 0; i < length; i++) {

            tmpStr = Integer.toHexString(0xFF & bArray[i]);

            if (tmpStr.length() < 2)
                buffer.append(0);

            buffer.append(tmpStr.toUpperCase());
        }

        return buffer.toString();
    }
}
