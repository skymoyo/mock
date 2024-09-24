package work.skymoyo.mock.core.admin.config;

import cn.hutool.core.date.DateUtil;
import work.skymoyo.mock.core.admin.utils.DateUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

public class SecurityUtil {

    private static final byte[] SECRET_KEY = "2022072713500000".getBytes();

    /**
     * 加密
     *
     * @param text
     * @return
     */
    public static String encrypt(String text) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes()));
    }

    /**
     * 解密
     *
     * @param text
     * @return
     */
    public static String decrypt(String text) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(SECRET_KEY, "AES");
        byte[] encryptText = Base64.getDecoder().decode(text);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        return new String(cipher.doFinal(encryptText));
    }

    public static void main(String[] args) throws Exception {
        String str = DateUtil.offsetHour(new Date(), 1).toString(DateUtils.YYYYMMDDHHMMSS);
        String encrypt = encrypt(str);
        System.out.println(encrypt);
        System.out.println();
        String encrypt1 = encrypt(encrypt);
        System.out.println(encrypt1);
        System.out.println();
        String encrypt2 = encrypt(encrypt1);
        System.out.println(encrypt2);
        System.out.println();
        System.out.println();

        String decrypt2 = decrypt(encrypt2);
        System.out.println(decrypt2);
        System.out.println();
        String decrypt1 = decrypt(decrypt2);
        System.out.println(decrypt1);
        System.out.println();
        String decrypt = decrypt(decrypt1);
        System.out.println(decrypt);
    }


}