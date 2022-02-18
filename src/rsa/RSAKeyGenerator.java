package rsa;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import util.Constants;
import util.FileUtils;

public class RSAKeyGenerator {
    public RSAKeyGenerator() {
        try {
            generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateKeyPair() throws NoSuchAlgorithmException, InterruptedException  {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair keyPair = generator.generateKeyPair();

        FileUtils.writeToFile(Constants.PUB_KEY_FILE, Base64.getEncoder().encode(keyPair.getPublic().getEncoded()));
        FileUtils.writeToFile(Constants.PRI_KEY_FILE, Base64.getEncoder().encode(keyPair.getPrivate().getEncoded()));
    }
}
