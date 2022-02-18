package aes;

import java.io.File;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import rsa.RSAUtils;
import util.Constants;
import util.FileUtils;

public class AESDecrypter {
    private boolean decrypteStatus = true;
    private String aesKeyString;
    private byte[] decryptContent;

    public AESDecrypter() {
        decrypt();
    }

    public Boolean getDecryptStatus() {
        return decrypteStatus;
    }

    public String getAESKeyString() {
        return aesKeyString;
    }

    public byte[] getDecryptBytes() {
        return decryptContent;
    }

    public void decrypt() {
        try {
            if (Constants.SELECT_PRI_KEY_FILE.getPath().isEmpty() || Constants.SELECT_LOCALKEY_FILE.getPath().isEmpty()) {
                throw new Exception("private key or localkey not found");
            }
            if (Constants.LIST_CIPHERTEXT_FILE.length == 0 || Constants.LIST_CIPHERTEXT_FILE == null) {
                throw new Exception("file not found");
            }
            byte[] localKeyContent = FileUtils.readBytesFromFile(Constants.SELECT_LOCALKEY_FILE);
            /* --- local key --- */
            // 1. First 256 bytes is encrypted AES key
            byte[] encryptedKey = Arrays.copyOfRange(localKeyContent, 0, 256);

            // 2. Next 16 bytes for IV
            byte[] ivBytes = Arrays.copyOfRange(localKeyContent, 256, localKeyContent.length);

            // 3. Decrypt AES key with private key
            PrivateKey priKey = RSAUtils.getPrivateKey(Base64.getDecoder().decode(FileUtils.readBytesFromFile(Constants.SELECT_PRI_KEY_FILE)));
            byte[] aesKeyBytes = RSAUtils.decryptKey(priKey, encryptedKey);
            aesKeyString = Base64.getEncoder().encodeToString(aesKeyBytes);

            /* --- ciphertext --- */
            for(File CIPHERTEXT: Constants.LIST_CIPHERTEXT_FILE) {
                // 4. Read encrypt content bytes
                byte[] fileBytes = FileUtils.readBytesFromFile(CIPHERTEXT);

                // 5. Decrypt file content
                SecretKey aesKey = AESUtils.getAESKey(aesKeyBytes);
                IvParameterSpec ivParams = AESUtils.getIVParams(ivBytes);
                decryptContent = AESUtils.decrypt(aesKey, ivParams, fileBytes);
                File outputFile = new File(Constants.DECRYPT_FILE_DIR + CIPHERTEXT.getName().substring(0, CIPHERTEXT.getName().lastIndexOf(".c5y2b3e4r4s1")));
                FileUtils.writeToFile(outputFile, decryptContent);
            }
            
        } catch (Exception e) {
            decrypteStatus = false;
            e.printStackTrace();
        }
    }
}
