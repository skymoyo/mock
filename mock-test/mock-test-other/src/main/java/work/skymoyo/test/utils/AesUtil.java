package work.skymoyo.test.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.springframework.util.StringUtils;

public class AesUtil {

    private static byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
    private static SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);


    public static String encryptHex(String o) {
        if (StringUtils.isEmpty(o)) {
            return o;
        }
        return aes.encryptHex(o);
    }

    public static String decryptStr(String o) {
        if (StringUtils.isEmpty(o)) {
            return o;
        }
        return aes.decryptStr(o, CharsetUtil.CHARSET_UTF_8);
    }

}
