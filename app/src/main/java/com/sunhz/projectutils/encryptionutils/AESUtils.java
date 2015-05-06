package com.sunhz.projectutils.encryptionutils;

import android.text.TextUtils;

import com.sunhz.projectutils.Constance;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {

    private static final String AES = "AES";

    /**
     * 解密
     *
     * @param sSrc 待解密内容
     * @return 解密后的内容
     * @throws Exception
     */
    public static String Decrypt(String sSrc) throws Exception {
        // 判断Key是否正确
        if (TextUtils.isEmpty(Constance.Encryption.AES_UTILS_CLIENT_KEY)) {
            throw new NullPointerException("Constance.Encryption.AES_UTILS_CLIENT_KEY 不能为空");
        }
        // 判断Key是否为16位
        if (Constance.Encryption.AES_UTILS_CLIENT_KEY.length() != 16) {
            throw new IllegalArgumentException("Key长度不是16位");
        }
        byte[] raw = Constance.Encryption.AES_UTILS_CLIENT_KEY.getBytes("ASCII");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] encrypted1 = hex2byte(sSrc);
        byte[] original = cipher.doFinal(encrypted1);
        return new String(original);
    }

    /**
     * 加密
     *
     * @param sSrc 待加密内容
     * @return 加密后的结果
     * @throws Exception
     */
    public static String Encrypt(String sSrc) throws Exception {
        // 判断Key是否正确
        if (TextUtils.isEmpty(Constance.Encryption.AES_UTILS_CLIENT_KEY)) {
            throw new NullPointerException("Constance.Encryption.AES_UTILS_CLIENT_KEY 不能为空");
        }
        // 判断Key是否为16位
        if (Constance.Encryption.AES_UTILS_CLIENT_KEY.length() != 16) {
            throw new IllegalArgumentException("Key长度不是16位");
        }
        byte[] raw = Constance.Encryption.AES_UTILS_CLIENT_KEY.getBytes("ASCII");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        return byte2hex(encrypted).toLowerCase();
    }

    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
}
