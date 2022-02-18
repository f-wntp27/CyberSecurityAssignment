package aes;

import java.io.File;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import rsa.RSAUtils;
import util.Constants;
import util.FileUtils;

public class AESEncrypter {
    private boolean encryptStatus = true;
    private String aesKeyString;

    public AESEncrypter() {
        encrypt();
    }

    public Boolean getEncryptStatus() {
        return encryptStatus;
    }

    public String getAESKeyString() {
        return aesKeyString;
    }

    public void encrypt() {
        // Constants.OUTPUT_CIPHERTEXT = new File(Constants.CIPHERTEXT_FILE_DIR + Constants.SELECT_PLAINTEXT_FILE.getName() + ".c5y2b3e4r4s1");
        try {
            if (Constants.SELECT_PUB_KEY_FILE.getPath().isEmpty()) {
                throw new Exception("public key not found");
            }
            if (Constants.LIST_PLAINTEXT_FILE == null || Constants.LIST_PLAINTEXT_FILE.length == 0) {
                throw new Exception("file not found");
            } else {
                // 1. Generate AES key
                SecretKey key = AESUtils.generateAESKey();
                aesKeyString = Base64.getEncoder().encodeToString(key.getEncoded());

                // 2. Random Iv parameter
                IvParameterSpec ivParams = AESUtils.randomIvParams();

                /* --- plaintext --- */
                for(File INPUT_FILE : Constants.LIST_PLAINTEXT_FILE) {
                    byte[] content = FileUtils.readBytesFromFile(INPUT_FILE);

                    // 3. Encrypt file content
                    byte[] encryptedContent = AESUtils.encrypt(key, ivParams, content);

                    // 4. Write to output
                    File outputFile = new File(Constants.CIPHERTEXT_FILE_DIR + INPUT_FILE.getName() + ".c5y2b3e4r4s1");
                    FileUtils.writeToFile(outputFile, encryptedContent);
                }

                // 5. Encrypt AES with public key
                PublicKey pubKey = RSAUtils.getPublicKey(Base64.getDecoder().decode(FileUtils.readBytesFromFile(Constants.SELECT_PUB_KEY_FILE)));
                byte[] encryptedKey = RSAUtils.encryptKey(pubKey, key.getEncoded());

                // 6. Combine encryted AES key and IV
                byte[] combineAesIV = FileUtils.combineBytes(encryptedKey, ivParams.getIV());

                // 7. Write encrypt key to localKey file
                FileUtils.writeToFile(Constants.OUTPUT_LOCALKEY, combineAesIV);
            }
        } catch (Exception e) {
            this.encryptStatus = false;
            e.printStackTrace();
        }
    }
}
