import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

import javax.crypto.*;

public class AuctionImpl extends UnicastRemoteObject implements Auction {

    private static final long serialVersionUID = 6192197272342675756L;

    private HashMap<Integer, AuctionItem> items;
    private int keyCounter = 0;

    // Implementations must have an explicit constructor in order to declare the
    // RemoteException exception
    public AuctionImpl() throws RemoteException {
        super();

        items = new HashMap<>();
    }

    public SealedObject getSpec(int itemId, ClientId clientId) throws RemoteException {
        // Decrypt client id
        System.out.println("Request from user " + clientId + " for item " + itemId);
        // Return requested auction item as SealedObject
        return (aesEncrypt(items.get(itemId), clientId.getEmail()));
    }

    public void placeBid(int itemId, float bid, ClientId bidder) throws RemoteException {
        AuctionItem item = items.get(itemId);
        if(item.getPrice() < bid && item.isActive())
            items.get(itemId).bid(bid, bidder);
    }

    public void closeListing(int itemId) throws RemoteException {
        items.get(itemId).close();
    }

    public HashMap<Integer, AuctionItem> getActiveListings(ClientId clientId) throws RemoteException {
        System.out.println("Request from user " + clientId + " for all active listings");
        HashMap<Integer, AuctionItem> activeItems = new HashMap<Integer, AuctionItem>();
        for (AuctionItem item : items.values()) {
            if(item.isActive())
                activeItems.put(item.getId(), item);
        }
        return activeItems;
    }

    public HashMap<Integer, AuctionItem> getUserBids(ClientId clientId) throws RemoteException {
        System.out.println("Request from user " + clientId + " for their bids");
        int id = clientId.getId();
        HashMap<Integer, AuctionItem> userBidItems = new HashMap<Integer, AuctionItem>();
        for (AuctionItem item : items.values()) {
            if(item.getBidders().containsKey(id))
                userBidItems.put(item.getId(), item);
        }
        return userBidItems;
    }

    public HashMap<Integer, AuctionItem> getUserListings(ClientId clientId) throws RemoteException {
        System.out.println("Request from user " + clientId + " for their listings");
        HashMap<Integer, AuctionItem> userListings = new HashMap<Integer, AuctionItem>();
        for (AuctionItem item : items.values()) {
            if(item.getSeller().equals(clientId))
                userListings.put(item.getId(), item);
        }
        return userListings;
    }

    public void addListing(String itemTitle, float price, float reserve, String itemDescription, String itemCondition, ClientId seller)
            throws RemoteException {
        if(reserve > price) {
            AuctionItem item = new AuctionItem(keyCounter, itemTitle, price, reserve, itemDescription, itemCondition, seller);
            System.out.println("Request from user " + seller + " to add: \n" + item);
            keyCounter++;
            items.put(item.getId(), item);
        }
    }

    public void registerUser(String email, String name) throws RemoteException {
        try {
            AccountManager.newUser(email, name);
        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    public ClientId loginUser(String email) throws RemoteException {
        try {
            return AccountManager.getUser(email);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("Failed Login attempt from " + email);
        }
        return null;
    }

    //---------------------------------------------------Encrpytion Methods----------------------------------------------------//

    public Object aesDecrypt(SealedObject toDecrypt, String email) {
        try {
            // Reads shared key from text file
            SecretKey key = AccountManager.loadKey(email);
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
    public SealedObject aesEncrypt(Serializable toEncrypt, String email) throws RemoteException {
        try {
            // Reads shared key from text file
            SecretKey key = AccountManager.loadKey(email);
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