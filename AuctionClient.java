import javax.crypto.*;
import java.rmi.Naming;
import java.util.HashMap;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;

public class AuctionClient {

    private ClientId id;
    private Auction auction;
    private ClientGUI gui;

    public static void main(String[] args) {
        new AuctionClient();
    }

    public AuctionClient() {

        gui = new ClientGUI(this);

        try {
            // Creates reference to the remote object through the remiregistry
            auction = (Auction) Naming.lookup("rmi://localhost/AuctionService");
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
        }
    }

    public ClientId getId() {
        return id;
    }

//--------------------------------------------------------Remote Methods-----------------------------------------------------------------

    // Returns item with id specified by user
    public AuctionItem remoteGetSpec(int itemID) throws RemoteException, InvalidKeySpecException {
        // Retrieve encrypted item from server
        //SealedObject item = auction.getSpec(itemID, id);
        // Decrypt and return item
        return null;//(AuctionItem) aesDecrypt(item, id.getEmail());
    }

    // Returns hashmap of all items which are currently active
    public HashMap<Integer, AuctionItem> remoteGetActiveListings() throws Exception {
        return (HashMap<Integer, AuctionItem>) auction.getActiveListings(id);
    }

    // Returns hashmap of all listings which user has bid on
    public HashMap<Integer, AuctionItem> remoteGetUserBids() throws RemoteException, InvalidKeySpecException {
        return null;//(HashMap<Integer, AuctionItem>) auction.getUserBids(id);
    }

    // Returns hashmap of all auction items listed by given user
    public HashMap<Integer, AuctionItem> remoteGetMyListings() throws RemoteException, InvalidKeySpecException {
        return null;//(HashMap<Integer, AuctionItem>) auction.getUserListings(id);
    }

    // Adds item listing to auction - will fail if reserve price is lower than starting price
    public void remoteAddListing(String itemTitle, float price, float reserve, String itemDescription, String itemCondition)
            throws RemoteException {
        auction.addListing(itemTitle, price, reserve, itemDescription, itemCondition, id);
    }

    // Places bid on item - will fail if item is not active, user is the seller, or bid is lower than current highest bid
    public void remotePlaceBid(int itemId, float bid) throws RemoteException {
        //auction.placeBid(itemId, bid, id);
    }

    // Closes specified item listing - will fail if passed id doesnt match item seller
    public void remoteCloseListing(int itemId) throws RemoteException {
        //auction.closeListing(itemId, id);
    }

    // Sends new email and name to server. Server then generates new id number and shared key and saves in file.
    public boolean remoteRegisterUser(String email, String name) throws RemoteException, NoSuchAlgorithmException, IOException {
        return auction.registerUser(email, name);
    }

    // Logs user into auction using 3-step authentication - throwing everything because deadlines 
    public boolean remoteLoginUser(String email) throws RemoteException, NoSuchAlgorithmException, IOException,
            IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, ClassNotFoundException,
            BadPaddingException {

        // Client retrieves public key from server to encrypt email
        Key publicKey = auction.getPublicKey();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        SealedObject emailRSA = new SealedObject(email, cipher);

        // Asks server for challenge and decrypts
        SealedObject challenge = auction.sendChallenge(emailRSA);
        int decryptedChallenge = (int) aesDecrypt(challenge, email);

        // Sends decrypted challenge back to server, clientid is returned to use auction if response was correct
        id = auction.loginUser(emailRSA, decryptedChallenge);
        if(id != null) {
            gui.login();
            return true;
        } else {
            return false;
        }
    }

// -----------------------------------------------------AES Encryption Methods----------------------------------------------------------------

    public Object aesDecrypt(SealedObject sealedItem, String email) {
        try {
            // Reads shared key from text file
            SecretKey key = AccountManager.loadKey(email);
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

    public SealedObject aesEncrypt(Serializable toEncrypt, String email) throws java.rmi.RemoteException, InvalidKeySpecException {
        try {
            SecretKey key = AccountManager.loadKey(email);
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
