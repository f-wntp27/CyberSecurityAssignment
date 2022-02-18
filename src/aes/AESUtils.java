package aes;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    public static SecretKey generateAESKey() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256);
        SecretKey key = generator.generateKey();

        return key;
    }

    public static SecretKey getAESKey(byte[] keyBytes) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        return secretKeySpec;
    }

    public static IvParameterSpec randomIvParams() {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[16];
        random.nextBytes(iv);
        IvParameterSpec ivParams = new IvParameterSpec(iv);
        return ivParams;
    }

    public static IvParameterSpec getIVParams(byte[] ivBytes) {
        IvParameterSpec ivParams = new IvParameterSpec(ivBytes);
        return ivParams;
    }

    public static byte[] encrypt(SecretKey key, IvParameterSpec ivParams, byte[] content) throws
    NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, 
    InvalidKeyException, BadPaddingException, IllegalBlockSizeException 
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);

        return cipher.doFinal(content);
    }

    public static byte[] decrypt(SecretKey key, IvParameterSpec ivParams, byte[] content) throws
        NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException,
        BadPaddingException, IllegalBlockSizeException
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, ivParams);

        return cipher.doFinal(content);
    }
}
