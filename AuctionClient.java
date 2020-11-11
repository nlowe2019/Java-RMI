import javax.crypto.*;
import java.rmi.Naming;
import java.util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;

public class AuctionClient {

    private ClientGUI gui;
    private ClientId id;
    private Auction auction;

    public static void main(String[] args) {
        new AuctionClient(1);
    }

    public AuctionClient(int n) {

        id = new ClientId(n);
        gui = new ClientGUI(this);

        try {
            // Creates reference to the remote object through the remiregistry
            auction = (Auction) Naming.lookup("rmi://localhost/AuctionService");

            remoteAddListing("Apple iPhone 11", 399.99f, 499.99f, 
            "Black iPhone 11, comes with 64GB of storage and includes charging adapter.", "New", aesEncrypt(id));
            remoteAddListing("Toshiba Microwave", 63.50f, 80.40f, 
                    "Adjustable power levels and time. Low-Noise operation (55db).", "Used", aesEncrypt(id));
            remoteAddListing("Office Chair", 59.99f, 69.99f, "Height adjustable swivel desk chair, black.", "New", aesEncrypt(id));
        }

        // Catch the exceptions that may occur
        catch (MalformedURLException murle) {
            System.out.println("\nMalformedURLException");
            murle.printStackTrace();
        } catch (RemoteException re) {
            System.out.println("\nRemoteException");
            re.printStackTrace();
        } catch (NotBoundException nbe) {
            System.out.println("\nNotBoundException");
            nbe.printStackTrace();
        } catch (java.lang.ArithmeticException ae) {
            System.out.println("\njava.lang.ArithmeticException");
            ae.printStackTrace();
        } catch (InvalidKeySpecException ikse) {
            System.out.println("\nInvalidKeySpecException");
            ikse.printStackTrace();
        }
    }

    public ClientId getId() {
        return id;
    }

//--------------------------------------------------------Remote Methods-----------------------------------------------------------------

    public AuctionItem remoteGetSpec(int itemID) throws RemoteException, InvalidKeySpecException {
        // Retrieve encrypted item from server
        SealedObject item = auction.getSpec(itemID, aesEncrypt(id));
        // Decrypt and return item
        return (AuctionItem) aesDecrypt(item);
    }

    public ConcurrentHashMap<Integer, AuctionItem> remoteGetAll() throws RemoteException, InvalidKeySpecException {
        return (ConcurrentHashMap<Integer, AuctionItem>) aesDecrypt(auction.getAll(aesEncrypt(id)));
    }

    public void remoteAddListing(String itemTitle, float price, float reserve, String itemDescription, String itemCondition, SealedObject seller)
            throws RemoteException {
        auction.addListing(itemTitle, price, reserve, itemDescription, itemCondition, seller);
    }

    public void remotePlaceBid(int itemId, float bid, ClientId bidder) throws RemoteException {
        auction.placeBid(itemId, bid, bidder);
    }

    public void remoteCloseListing(int itemId) throws RemoteException {
        auction.closeListing(itemId);
    }

// -----------------------------------------------------Encryption Methods----------------------------------------------------------------

    public Object aesDecrypt(SealedObject sealedItem) {
        try {
            // Reads shared key from text file
            SecretKey key = KeyManager.loadKey("keys.txt");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            // Returns decrypted object
            return sealedItem.getObject(cipher);
                
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

    public SealedObject aesEncrypt(Serializable toEncrypt) throws java.rmi.RemoteException, InvalidKeySpecException {
        try {
            SecretKey key = KeyManager.loadKey("keys.txt");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
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
