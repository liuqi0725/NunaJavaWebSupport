package com.liuqi.nuna.core.utils.crypt;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;


/**
 * 类说明 <br>
 *     DES 加密
 * <p>
 * 构造说明 :
 * <pre>
 *   老版本的 des 加密
 * </pre>
 * @deprecated 可能会在 1.1 中取消
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
public class DESCrypt {
	
	private byte[] desKey;  

    public DESCrypt(String desKey) {
        this.desKey = desKey.getBytes();  
    }  
  
    public byte[] desEncrypt(byte[] plainText) throws Exception {  
        SecureRandom sr = new SecureRandom();  
        byte rawKeyData[] = desKey;  
        DESKeySpec dks = new DESKeySpec(rawKeyData);  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);  
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key, sr);  
        byte data[] = plainText;  
        byte encryptedData[] = cipher.doFinal(data);  
        return encryptedData;  
    }  
  
    public byte[] desDecrypt(byte[] encryptText) throws Exception {  
        SecureRandom sr = new SecureRandom();  
        byte rawKeyData[] = desKey;  
        DESKeySpec dks = new DESKeySpec(rawKeyData);  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);  
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key, sr);  
        byte encryptedData[] = encryptText;  
        byte decryptedData[] = cipher.doFinal(encryptedData);  
        return decryptedData;  
    }  
  
    public String encrypt(String input) throws Exception {  
        return base64Encode(desEncrypt(input.getBytes()));  
    }  
  
    public String decrypt(String input) throws Exception {  
        byte[] result = base64Decode(input);  
        return new String(desDecrypt(result));  
    }  
  
    public static String base64Encode(byte[] s) {  
        if (s == null)  
            return null;  
        BASE64Encoder b = new BASE64Encoder();
        return b.encode(s);  
    }  
  
    public static byte[] base64Decode(String s) throws IOException {  
        if (s == null)  
            return null;  
        BASE64Decoder decoder = new BASE64Decoder();  
        byte[] b = decoder.decodeBuffer(s);  
        return b;  
    }
  
    public static void main(String[] args) throws Exception {  
        String key = "SSKJYXGS-DAM-V1.1-201709140930";
        DESCrypt crypt = new DESCrypt(key);
        String input = "{\"IP\":\"192.168.1.101\",\"MAC\":\"764F-A024-A37E-95C2-6MN0\",\"HOSTNAME\":\"DESKTOP-FR5RKMC\",\"SYSTEM\":\"Windows 10\",\"SYSTEM_VERSION\":\"10.0\",\"LICENSE\":\"764F-A024-A37E-95C2-6MN0\",\"C\":[29,27,22,1],\"V\":\"3598BFDA070E333EEDD825DDACF2C556\"}";

        String enStr = crypt.encrypt(input).replaceAll("\r|\n", "");
        System.out.println("Encode:" + enStr);
        System.out.println("Decode:" + crypt.decrypt(enStr));
//        System.out.println("http://test.dam:9100/index.php?r=dam/license/validation&info=" + enStr);

        String d = "%2BCPoWp2uvEWTpgLVlABgCY0%2FckXZlCMGLFY4moebk5jC5uZv9aDHTaBZytub1dVgdYY4cmWpECr4YO%2BTiEMIGHfATpkUKs0%2F8AEndbsbIOBZc6qDov%2FzHlvSRx7wJpaboYCI%2BTGBzOXNv5noO1IOgNERlsEApNQXhaWIkicbkXs6Upj3zOavmz%2Be02RtnFi3iAgc8lbJtR0Mb7na%2FxygVVaVC%2BnU7rLR5TIGtxzw0JnjPzwPGK4ZdO52sKpwYVNRcm%2FfjusPcHKaKBpMGQJh5AGUfP4TTvq2";
        System.out.println(crypt.decrypt(d));

    }
}
