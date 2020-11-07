import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import javax.crypto.*;

public class AuctionImpl extends java.rmi.server.UnicastRemoteObject implements Auction {

    private static final long serialVersionUID = 6192197272342675756L;

    private HashMap<Integer, AuctionItem> items;

    // Implementations must have an explicit constructor in order to declare the RemoteException exception
    public AuctionImpl() throws java.rmi.RemoteException {
        super();
        
        // Dummy data stored in Hashmap
        items = new HashMap<>();

        newItem(5235, "Apple iPhone 11", (float) 499.99, "Black iPhone 11, comes with 64GB of storage and includes charging adapter.", "New");
        newItem(284, "Toshiba 800W Microwave Oven", (float) 63.50, "Adjustable power levels and time. Low-Noise operation (55db).", "Used");
        newItem(1503, "Office Chair", (float) 59.99, "Height adjustable swivel desk chair, black.", "New");
    }

    public SealedObject getSpec(int itemId, SealedObject clientId) throws java.rmi.RemoteException, InvalidKeySpecException {
        // Decrypt client id
        System.out.println("Request from client " + aesDecrypt(clientId) + " for item " + itemId);
        // Return requested auction item as SealedObject
        return (aesEncrypt(items.get(itemId)));
    }

    public void newItem(int itemId, String itemTitle, float price, String itemDescription, String itemCondition) throws java.rmi.RemoteException {
        // Instantiate new AuctionItem
        AuctionItem item = new AuctionItem(itemId, itemTitle, price, itemDescription, itemCondition);
        // Add new item to hashmap
        items.put(item.getId(), item);
    }

    public Object aesDecrypt(SealedObject toDecrypt) {
        try {
            // Reads shared key from text file
            SecretKey key = KeyManager.loadKey("keys.txt");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            // Returns decrypted object
            return toDecrypt.getObject(cipher);
                
        } catch (InvalidKeyException e) {
            System.out.println("\nInvalidKeyException");
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            System.out.println("\nNoSuchPaddingException");
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            System.out.println("\nIllegalBlockSizeException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("\nIOException");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("\nNoSuchAlogrithmException");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("\nClassNotFoundException");
            e.printStackTrace();
        } catch (BadPaddingException e) {
            System.out.println("\nBadPaddingException");
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            System.out.println("\nInvalidKeySpecException");
            e.printStackTrace();
        }
        return null;
    }

    // Takes any Serializable object to encrypt
    public SealedObject aesEncrypt(Serializable toEncrypt) throws java.rmi.RemoteException {
        try {
            // Reads shared key from text file
            SecretKey key = KeyManager.loadKey("keys.txt");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // Returns encrypted object
            return new SealedObject(toEncrypt, cipher);
                
        } catch (InvalidKeyException e) {
            System.out.println("\nInvalidKeyException");
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            System.out.println("\nNoSuchPaddingException");
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            System.out.println("\nIllegalBlockSizeException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("\nIOException");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("\nNoSuchAlgorithmException");
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            System.out.println("\nInvalidKeySpecException");
            e.printStackTrace();
        }
        return null;
    }
}