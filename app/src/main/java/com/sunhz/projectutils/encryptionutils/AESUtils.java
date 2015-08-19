/**
 * Copyright (c) 2015, Spencer 给立乐 (www.spencer-dev.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sunhz.projectutils.encryptionutils;

import android.text.TextUtils;

import com.sunhz.projectutils.Constance;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加/解密工具类
 * Created by Spencer (www.spencer-dev.com) on 15/2/21.
 */
public class AESUtils {

    private AESUtils() {

    }

    private static final String AES = "AES";

    /**
     * 解密
     *
     * @param sSrc 待解密内容
     * @return 解密后的内容
     * @throws Exception 解密失败
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
     * @throws Exception 加密失败
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
