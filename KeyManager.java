import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class KeyManager {

    public static SecretKey newKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public static void saveKey(SecretKey key) throws IOException {
        // encode key as byte array
        byte[] encoded = key.getEncoded();
        FileOutputStream os = new FileOutputStream("keys.txt");
        // write key to file
        os.write(encoded);
        os.close();
    }

    public static SecretKey loadKey(String filepath) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException
    {
        File file = new File(filepath);
        byte[] encoded = new byte[(int) file.length()];
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        dis.readFully(encoded);
        dis.close();
        return new SecretKeySpec(encoded, "AES");
    }

    public static void main(String[] args) throws Exception {
        SecretKey key = newKey();
        saveKey(key);
    }
}