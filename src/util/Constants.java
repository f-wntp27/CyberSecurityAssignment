package util;

import java.io.File;

public class Constants {
    // <String> file path, directory path
    public static String SOURCE_DIR = "src/";
    public static String INIT_INPUT_DIR = SOURCE_DIR + "testinput";
    public static String INIT_KEY_DIR = SOURCE_DIR + "testkey/";
    public static String INIT_OUTPUT_DIR = SOURCE_DIR + "testoutput/";

    public static String PUB_KEY_FILE_PATH = INIT_KEY_DIR + "public_key.txt";
    public static String PRI_KEY_FILE_PATH = INIT_KEY_DIR + "private_key.txt";

    public static String LOCALKEY_FILE_PATH = INIT_KEY_DIR + "localkey";

    public static String CIPHERTEXT_FILE_DIR = "src/testoutput_encrypt/";
    public static String DECRYPT_FILE_DIR = "src/testoutput_decrypt/";

    // <File> file object
    public static File PUB_KEY_FILE = new File(PUB_KEY_FILE_PATH);
    public static File PRI_KEY_FILE = new File(PRI_KEY_FILE_PATH);

    public static File SELECT_PUB_KEY_FILE = new File("");
    public static File SELECT_PLAINTEXT_FOLDER = new File("");
    public static File[] LIST_PLAINTEXT_FILE;
    public static File OUTPUT_CIPHERTEXT = new File("");
    public static File OUTPUT_LOCALKEY = new File(LOCALKEY_FILE_PATH);

    public static File SELECT_PRI_KEY_FILE = new File("");
    public static File SELECT_LOCALKEY_FILE = new File("");
    public static File SELECT_CIPHERTEXT_FOLDER = new File("");
    public static File[] LIST_CIPHERTEXT_FILE;
    public static File OUTPUT_DECRYPT_FILE = new File("");

    public static void checkFolder() {
        File testKeyDir = new File(INIT_KEY_DIR);
        if (!testKeyDir.exists()) {
            testKeyDir.mkdirs();
        }

        File testInputDir = new File(INIT_INPUT_DIR);
        if (!testInputDir.exists()) {
            testInputDir.mkdirs();
        }

        File testoutput_encrypt = new File(CIPHERTEXT_FILE_DIR);
        if (!testoutput_encrypt.exists()) {
            testoutput_encrypt.mkdir();
        }

        File testoutput_decrypt = new File(DECRYPT_FILE_DIR);
        if (!testoutput_decrypt.exists()) {
            testoutput_decrypt.mkdir();
        }
    }
}
