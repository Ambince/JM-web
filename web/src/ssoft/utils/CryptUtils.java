package ssoft.utils;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * <pre>职责: 用户加密和解密算法
 * 使用方法：
 * </pre>
 *
 * @author sunsn 2016-1-5 下午02:54:43
 * @see #
 * @since <pre>修改记录（修改时间、修改人、修改内容、修改原因）</pre>
 */
public class CryptUtils {
    public static String key = "4353463241234";

    /**
     * 消息摘要算法名称：SHA1
     */
    public static final String DIGEST_SHA1 = "SHA1";

    /**
     * 消息摘要算法名称：MD5
     */
    public static final String DIGEST_MD5 = "MD5";

    /**
     * 计算消息摘要值
     *
     * @param algorithm - 算法，如：MD5, SHA1，推荐使用SHA1
     * @param text
     * @return - 16进制字符串
     * @throws Exception
     */
    public static String generateDigest(String algorithm, String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] value = digest.digest(text.getBytes("UTF-8"));
            return parseByteToHexString(value).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * 计算消息摘要值
     * MD5
     *
     * @param text
     * @return - 16进制字符串
     * @throws Exception
     */
    public static String generateDigest(String text) {
        return generateDigest(DIGEST_MD5, text);
    }

    /**
     * 将字节数组转换为16进制字符串
     *
     * @param content
     * @return
     * @throws Exception
     * @author Julian
     */
    public static String parseByteToHexString(byte[] content) {
        if (content == null) throw new IllegalArgumentException("the parameter is null.");

        String hex = null;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < content.length; i++) {
            hex = Integer.toHexString(content[i] & 0xFF);
            if (hex.length() == 1) hex = '0' + hex;

            buffer.append(hex.toUpperCase());
        }

        return buffer.toString();
    }

    /**
     * 使用MD5，已过时，替代方法为generateDigest
     *
     * @param s
     * @return
     * @see #generateDigest(String, String)
     */
    @Deprecated
    public static final String strMD5(String s) {

        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密算法
     */

    public static String encrypt(String txt) {

        String encrypt_key = "0f9cfb7a9acced8a4167ea8006ccd098";
        int ctr = 0;
        String tmp = "";
        int i;
        for (i = 0; i < txt.length(); i++) {
            ctr = (ctr == encrypt_key.length()) ? 0 : ctr;
            tmp = tmp + encrypt_key.charAt(ctr) + (char) (txt.charAt(i) ^ encrypt_key.charAt(ctr));
            ctr++;
        }
        return base64_encode(key(tmp, key));
    }

    /**
     * 解密算法
     */

    public static String decrypt(String cipherText) {

        // base64解码
        cipherText = base64_decode(cipherText);
        cipherText = key(cipherText, key);
        String tmp = "";
        for (int i = 0; i < cipherText.length(); i++) {
            int c = cipherText.charAt(i) ^ cipherText.charAt(i + 1);
            String x = "" + (char) c;
            tmp += x;
            i++;
        }
        return tmp;
    }

    public static String key(String txt, String encrypt_key) {

        encrypt_key = strMD5(encrypt_key);
        int ctr = 0;
        String tmp = "";
        for (int i = 0; i < txt.length(); i++) {
            ctr = (ctr == encrypt_key.length()) ? 0 : ctr;
            int c = txt.charAt(i) ^ encrypt_key.charAt(ctr);
            String x = "" + (char) c;
            tmp = tmp + x;
            ctr++;
        }
        return tmp;

    }

    public static String base64_encode(String str) {
        return new sun.misc.BASE64Encoder().encode(str.getBytes());
    }

    public static String base64_decode(String str) {

        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        if (str == null)
            return null;
        try {
            return new String(decoder.decodeBuffer(str));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     * <pre>功能描述: des加密
     * 使用方法：
     * 使用约束：
     * 逻辑：
     * </pre>
     *
     * @param source
     * @param target
     * @throws Exception
     * @see #
     * @since <pre>create sunsn 2016-2-24 上午10:57:19
     * 修改记录（修改时间、修改人、修改内容、修改原因）
     * </pre>
     */
    public static Key desKey;

    public static void encryptByDES(File source, File target) {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        CipherInputStream cis = null;
        try {
            Cipher cipher = Cipher.getInstance("DES");
            if (null == desKey) {
                desKey = getDesKey(key);
            }
            cipher.init(Cipher.ENCRYPT_MODE, desKey);

            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(target);
            cis = new CipherInputStream(inputStream, cipher);
            byte[] buffer = new byte[1024];
            int i;
            while ((i = cis.read(buffer)) > 0) {
                outputStream.write(buffer, 0, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (null != cis) {
                    cis.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
                if (null != outputStream) {
                    outputStream.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * <pre>功能描述: 生成密钥
     * 使用方法：
     * 使用约束：
     * 逻辑：
     * </pre>
     *
     * @param key
     * @see #
     * @since <pre>create sunsn 2016-2-24 上午10:57:32
     * 修改记录（修改时间、修改人、修改内容、修改原因）
     * </pre>
     */
    public static Key getDesKey(String key) {
        KeyGenerator generator = null;
        try {
            generator = KeyGenerator.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        generator.init(new SecureRandom(key.getBytes()));
        Key desKey = generator.generateKey();
        return desKey;
    }

	/*public static void decryptByDES(String file, ServletOutputStream outputStream) {
		FileInputStream inputStream = null;
		CipherOutputStream cos = null;
		try {
			Cipher cipher = Cipher.getInstance("DES");
			if(null == desKey) {
				desKey = getDesKey(key);
			}
			cipher.init(Cipher.DECRYPT_MODE, desKey);
			inputStream = new FileInputStream(file);
			cos = new CipherOutputStream(outputStream, cipher);
			byte[] buffer = new byte[1024];
			int i;
			while ((i = inputStream.read(buffer)) > 0) {
				cos.write(buffer, 0, i);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try{
				if (null != cos) {
					cos.close();
				}
				if (null != inputStream) {
					inputStream.close();
				}
			}catch (Exception e){
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		}
	}*/
}
