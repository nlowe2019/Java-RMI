import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class AccountManager {

    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }

    public static void newUser(String email, String name) throws IOException, NoSuchAlgorithmException, FileNotFoundException,
            InvalidKeySpecException {

        File userdata = new File("users/" + email + ".txt");

        if(!userdata.exists()){

            userdata.createNewFile();
            SecretKey key = generateKey();
            // encode key as byte array
            byte[] encoded = key.getEncoded();
            FileOutputStream os = new FileOutputStream(userdata, true);
            // write key to file
            os.write(encoded);
            os.close();

            int id = newUserId();
            
            // write id, name and email to file
            FileWriter fileWriter = new FileWriter(userdata, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.printf("\n" + id + "\n" + email + "\n" + name);
            printWriter.close();
        }
    }

    public static ClientId getUser(String email) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {

        File userdata = new File("users/" + email + ".txt");

        FileReader fileReader = new FileReader(userdata);
        BufferedReader br = new BufferedReader(fileReader);
        char[] c = new char[32];
        br.read(c,0,32);
        br.readLine();
        int id = Integer.parseInt(br.readLine());
        br.readLine();
        String name = br.readLine();
        br.close();

        return new ClientId(id, email, name);
    }

    public static int newUserId() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        
        File userdata = new File("userCount.txt");

        FileReader fileReader = new FileReader(userdata);
        BufferedReader br = new BufferedReader(fileReader);
        int n = Integer.parseInt(br.readLine());
        br.close();

        n++;

        FileWriter fileWriter = new FileWriter(userdata);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.printf(Integer.toString(n));
        printWriter.close();
        return n;
    }

    public static SecretKey loadKey(String email) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException
    {
        File file = new File("users/" + email + ".txt");
        byte[] encoded = new byte[32];
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        dis.read(encoded, 0, 32);
        dis.close();

        return new SecretKeySpec(encoded, "AES");    }
}