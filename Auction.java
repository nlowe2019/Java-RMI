import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

import javax.crypto.SealedObject;

public interface Auction extends java.rmi.Remote {	

    // matches item with given itemId
    // returns encrypted Auction item as SealedObject
    public SealedObject getSpec(int itemId, ClientId clientId)
        throws RemoteException, InvalidKeySpecException;

    // Returns items collection
    public HashMap<Integer, AuctionItem> getActiveListings(ClientId ClientId)
        throws RemoteException;

    // Returns set of items which specified user has bidded on
    public HashMap<Integer, AuctionItem> getUserBids(ClientId ClientId)
        throws RemoteException;

    // Returns set of items which specified user has bidded on
    public HashMap<Integer, AuctionItem> getUserListings(ClientId ClientId)
        throws RemoteException;

    // adds new item to auction item hashmap
    public void addListing(String itemTitle, float price, float reserve, String itemDescription, String itemCondition, ClientId seller) 
        throws RemoteException;

    public void placeBid(int itemId, float bid, ClientId bidder)
        throws RemoteException;

    public void closeListing(int itemId)
        throws RemoteException;

    public void registerUser(String email, String name)
        throws RemoteException, NoSuchAlgorithmException, IOException;

    public ClientId loginUser(String email)
        throws RemoteException;

    // encrypts any Serializable object
    public SealedObject aesEncrypt(Serializable toEncrypt, String email)
        throws RemoteException, NoSuchAlgorithmException, InvalidKeySpecException;

    // decrypts any SealedObject
    public Object aesDecrypt(SealedObject toDecrypt, String email)
        throws RemoteException, NoSuchAlgorithmException, InvalidKeySpecException;
    
}
