import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Random;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.rmi.Naming; // Import naming classes to bind to rmiregistry
import java.util.LinkedList;
import java.util.List;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.blocks.MethodCall;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.ResponseMode;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.util.RspList;
import org.jgroups.util.Util;
import org.jgroups.util.Base64.InputStream;
import org.jgroups.util.Base64.OutputStream;

import javax.crypto.*;

public class AuctionImpl extends UnicastRemoteObject implements Auction {

    private JChannel channel;

    private static final long serialVersionUID = 6192197272342675756L;

    //private HashMap<Integer, AuctionItem> items; // Hashmap of all item listings - key is their item id
    private HashMap<String, Integer> userChallenges; // Hashmap of challenges and the email they are for
    private KeyPair asymKeys; // Public/Private asymmetric keys
    private int keyCounter = 0; // Tracks number of item listings so new items have unique id's

    // Implementations must have an explicit constructor in order to declare the
    // RemoteException exception
    public AuctionImpl() throws Exception {
        super();
        //items = new HashMap<>();
        userChallenges = new HashMap<>();
        generateKeyPair();

        channel = new JChannel();
        channel.connect("AuctionCluster");
    }

    // Generates pair of asymmetric keys for clients to encrypt their email with
    private void generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        asymKeys = kpg.generateKeyPair();
    }

    // Returns public key to client
    public Key getPublicKey() throws RemoteException {
        return asymKeys.getPublic();
    }

    /*
    // Returns single Auction Item object with given id number
    public SealedObject getSpec(int itemId, ClientId clientId) throws RemoteException {
        // Decrypt client id
        System.out.println("Request from user " + clientId + " for item " + itemId);
        // Return requested auction item as SealedObject
        return (aesEncrypt(items.get(itemId), clientId.getEmail()));
    }

    // Updates item with new highest bid
    public void placeBid(int itemId, float bid, ClientId bidder) throws RemoteException {
        AuctionItem item = items.get(itemId);
        if (item.getPrice() < bid && item.isActive() && !item.getSeller().equals(bidder))
            items.get(itemId).bid(bid, bidder);
    }

    // Sets item status to closed
    public void closeListing(int itemId, ClientId seller) throws RemoteException {
        AuctionItem item = items.get(itemId);
        if (seller.equals(item.getSeller()))
            item.close();
    }
    */
    // Returns hashmap of all active item listings to client
    public HashMap<Integer, AuctionItem> getActiveListings(ClientId clientId) throws Exception {
        System.out.println("Request from user " + clientId + " for all active listings");

        //rsp_list=disp.callRemoteMethods(null, "handleFunctionCall", new Object[]{"getActive", null}, new Class[]{String.class, Object[].class}, opts);
        return null; //(HashMap<Integer, AuctionItem>) rsp_list.getFirst();
    }

    /*
    // Returns hashmap of all items which the specified user has bid on
    public HashMap<Integer, AuctionItem> getUserBids(ClientId clientId) throws RemoteException {
        System.out.println("Request from user " + clientId + " for their bids");
        int id = clientId.getId();
        HashMap<Integer, AuctionItem> userBidItems = new HashMap<Integer, AuctionItem>();
        for (AuctionItem item : items.values()) {
            if (item.getBidders().containsKey(id))
                userBidItems.put(item.getId(), item);
        }
        return userBidItems;
    }

    // Returns hashmap of all items the user is selling
    public HashMap<Integer, AuctionItem> getUserListings(ClientId clientId) throws RemoteException {
        System.out.println("Request from user " + clientId + " for their listings");
        HashMap<Integer, AuctionItem> userListings = new HashMap<Integer, AuctionItem>();
        for (AuctionItem item : items.values()) {
            if (item.getSeller().equals(clientId))
                userListings.put(item.getId(), item);
        }
        return userListings;
    }
    */
    // Adds new item to auction
    public void addListing(String itemTitle, float price, float reserve, String itemDescription, String itemCondition,
            ClientId seller) throws RemoteException {
        if (reserve > price) {
            AuctionItem item = new AuctionItem(keyCounter, itemTitle, price, reserve, itemDescription, itemCondition,
                    seller);
            System.out.println("Request from user " + seller + " to add: \n" + item);
            keyCounter++;
            //items.put(item.getId(), item);

            try {
                
                //rsp_list=disp.callRemoteMethods(null, "handleFunctionCall", new Object[]{"add", item}, new Class[]{String.class, AuctionItem.class}, opts);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // Writes user email and name, as well as new shared key and user id number to a
    // text file
    public boolean registerUser(String email, String name) throws RemoteException {
        try {
            return AccountManager.newUser(email, name);
        } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException e) {
            return false;
        }
    }

    // Authenticates the user with 3 steps and returns their clientid if they pass
    public ClientId loginUser(SealedObject emailRSA, int challenge)
            throws ClassNotFoundException, IllegalBlockSizeException, BadPaddingException, IOException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {

        String email = rsaDecrypt(emailRSA).toString();
        int challengeAnswer = userChallenges.get(email);    // Gets challenge generated in other function
        userChallenges.remove(email);                       // Removes challenge once checked

        if (challenge == challengeAnswer) {                 // If challenge response is correct, send user their clientId to use auction
            System.out.println(email + " was successfully authenticated.");
            try {
                return AccountManager.getUser(email);
            } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (IOException ioe) {
                System.out.println("Failed Login attempt from " + email);
            }
            return null;
        } else {
            return null;
        }
    }

    // Sends encrypted challenge (as SealedObject) to client for them to decrypt
    // with shared key
    public SealedObject sendChallenge(SealedObject emailRSA)
            throws NoSuchPaddingException, InvalidKeyException, ClassNotFoundException, IllegalBlockSizeException,
            BadPaddingException, IOException, NoSuchAlgorithmException {

        String email = rsaDecrypt(emailRSA).toString();        // Decrypts email with private key
        Random random = new Random();
        int challenge = random.nextInt(2 ^ 31);     // Generates random integer
        userChallenges.put(email, challenge);       // Stores challenge to be checked against response
        return aesEncrypt(challenge, email);
    }

    // ---------------------------------------------------Encryption Methods----------------------------------------------------//

    public Object rsaDecrypt(SealedObject toDecrypt) {

        Cipher cipher;
        try {
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, asymKeys.getPrivate());
            return toDecrypt.getObject(cipher).toString();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | ClassNotFoundException
                | IllegalBlockSizeException | BadPaddingException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

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