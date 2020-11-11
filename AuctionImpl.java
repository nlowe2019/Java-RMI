import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.*;

public class AuctionImpl extends java.rmi.server.UnicastRemoteObject implements Auction {

    private static final long serialVersionUID = 6192197272342675756L;

    private ConcurrentHashMap<Integer, AuctionItem> items;
    private int keyCounter = 0;

    // Implementations must have an explicit constructor in order to declare the
    // RemoteException exception
    public AuctionImpl() throws java.rmi.RemoteException {
        super();

        items = new ConcurrentHashMap<>();
    }

    public SealedObject getSpec(int itemId, SealedObject clientId) throws java.rmi.RemoteException {
        // Decrypt client id
        System.out.println("Request from client " + aesDecrypt(clientId) + " for item " + itemId);
        // Return requested auction item as SealedObject
        return (aesEncrypt(items.get(itemId)));
    }

    public void placeBid(int itemId, float bid, ClientId bidder) throws RemoteException {
        items.get(itemId).bid(bid, bidder);
    }

    public SealedObject getAll(SealedObject clientId) throws RemoteException {
        System.out.println("Request from client " + aesDecrypt(clientId) + " for all listings");
        return aesEncrypt(items);
    }

    public void addListing(String itemTitle, float price, float reserve, String itemDescription, String itemCondition, SealedObject seller)
            throws java.rmi.RemoteException {
        if(reserve > price) {
            AuctionItem item = new AuctionItem(keyCounter, itemTitle, price, reserve, itemDescription, itemCondition, (ClientId)aesDecrypt(seller));
            System.out.println("Request from client " + aesDecrypt(seller) + " to add: \n" + item);
            keyCounter++;
            items.put(item.getId(), item);
        }
    }

    //---------------------------------------------------Encrpytion Methods----------------------------------------------------//

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