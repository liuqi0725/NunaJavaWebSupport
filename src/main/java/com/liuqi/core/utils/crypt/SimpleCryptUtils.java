package com.liuqi.core.utils.crypt;

import com.liuqi.core.utils.ExceptionUtils;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;


/**
 * 类说明 <br>
 * 常用加密算法
 * <p>
 * 构造说明 :
 * <pre>
 *     md5 以及 hash
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 12:53 PM 2018/11/9
 */
public class SimpleCryptUtils {

    private static final String SHA1 = "SHA-1";
    private static final String MD5 = "MD5";

    private static SecureRandom random = new SecureRandom();

    /**
     * 对输入字符串进行sha1散列.
     * @param input 字符串字节
     * @return sha1 散列值
     */
    public static byte[] sha1(byte[] input) {
        return digest(input, SHA1, null, 1);
    }

    /**
     * 对输入字符串进行sha1散列.
     * @param input 字符串字节
     * @param salt 加密盐
     * @return sha1 散列值
     */
    public static byte[] sha1(byte[] input, byte[] salt) {
        return digest(input, SHA1, salt, 1);
    }

    /**
     * 对输入字符串进行sha1散列.
     * @param input 字符串字节
     * @param salt 加密盐
     * @param iterations 迭代计算次数
     * @return sha1 散列值
     */
    public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
        return digest(input, SHA1, salt, iterations);
    }

    /**
     * 对字符串进行散列, 支持md5与sha1算法.
     * @param input 字符串字节
     * @param algorithm 算法 "md5"，"SHA-1"
     * @param salt 加密盐
     * @param iterations 迭代计算次数
     * @return 加密结果
     */
    private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);

            if (salt != null) {
                digest.update(salt);
            }

            byte[] result = digest.digest(input);

            for (int i = 1; i < iterations; i++) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (GeneralSecurityException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 生成随机的Byte[]作为salt.
     * @param numBytes  byte数组的大小
     * @return 盐
     */
    public static byte[] generateSalt(int numBytes) {
        Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);

        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return bytes;
    }

    /**
     * 对文件进行md5散列.
     * @param input 输入流
     * @return MD5 后的散列
     * @throws IOException 异常抛出
     */
    public static byte[] md5(InputStream input) throws IOException {
        return digest(input, MD5);
    }

    /**
     * 对文件进行sha1散列.
     * @param input 输入流
     * @return SHA-1 后的散列
     * @throws IOException 异常抛出
     */
    public static byte[] sha1(InputStream input) throws IOException {
        return digest(input, SHA1);
    }

    /**
     * 对文件进行散列，支持 MD5、sha-1
     * @param input 输入流
     * @param algorithm 算法
     * @return 计算后的散列
     * @throws IOException 异常抛出
     */
    private static byte[] digest(InputStream input, String algorithm) throws IOException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            int bufferLength = 8 * 1024;
            byte[] buffer = new byte[bufferLength];
            int read = input.read(buffer, 0, bufferLength);

            while (read > -1) {
                messageDigest.update(buffer, 0, read);
                read = input.read(buffer, 0, bufferLength);
            }

            return messageDigest.digest();
        } catch (GeneralSecurityException e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

}
