import javax.crypto.*;
import java.rmi.Naming;
import java.util.HashMap;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;

public class AuctionClient {

    private ClientId id;
    private Auction auction;
    private ClientGUI gui;

    public static void main(String[] args) {
        new AuctionClient(1);
        new AuctionClient(2);
    }

    public AuctionClient(int n) {

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

    public AuctionItem remoteGetSpec(int itemID) throws RemoteException, InvalidKeySpecException {
        // Retrieve encrypted item from server
        SealedObject item = auction.getSpec(itemID, id);
        // Decrypt and return item
        return (AuctionItem) aesDecrypt(item);
    }

    public HashMap<Integer, AuctionItem> remoteGetActiveListings() throws RemoteException, InvalidKeySpecException {
        return (HashMap<Integer, AuctionItem>) auction.getActiveListings(id);
    }

    public HashMap<Integer, AuctionItem> remoteGetUserBids() throws RemoteException, InvalidKeySpecException {
        return (HashMap<Integer, AuctionItem>) auction.getUserBids(id);
    }

    public HashMap<Integer, AuctionItem> remoteGetMyListings() throws RemoteException, InvalidKeySpecException {
        return (HashMap<Integer, AuctionItem>) auction.getUserListings(id);
    }

    public void remoteAddListing(String itemTitle, float price, float reserve, String itemDescription, String itemCondition)
            throws RemoteException {
        auction.addListing(itemTitle, price, reserve, itemDescription, itemCondition, id);
    }

    public void remotePlaceBid(int itemId, float bid) throws RemoteException {
        auction.placeBid(itemId, bid, id);
    }

    public void remoteCloseListing(int itemId) throws RemoteException {
        auction.closeListing(itemId);
    }

    public void remoteRegisterUser(String email, String name) throws RemoteException, NoSuchAlgorithmException, IOException {
        auction.registerUser(email, name);
    }

    public void remoteLoginUser(String email) throws RemoteException, NoSuchAlgorithmException, IOException {
        id = auction.loginUser(email);
        if(id != null)
            gui.login();
    }

// -----------------------------------------------------Encryption Methods----------------------------------------------------------------

    public Object aesDecrypt(SealedObject sealedItem) {
        try {
            // Reads shared key from text file
            SecretKey key = AccountManager.loadKey(id.getEmail());
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
            SecretKey key = AccountManager.loadKey(id.getEmail());
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
