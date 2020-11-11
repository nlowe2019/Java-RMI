import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SealedObject;

public interface Auction extends java.rmi.Remote {	

    // matches item with given itemId
    // returns encrypted Auction item as SealedObject
    public SealedObject getSpec(int itemId, SealedObject clientId)
        throws RemoteException, InvalidKeySpecException;

    // Returns items collection
    public SealedObject getAll(SealedObject ClientId)
        throws RemoteException;

    // adds new item to auction item hashmap
    public void addListing(String itemTitle, float price, float reserve, String itemDescription, String itemCondition, SealedObject seller) 
        throws RemoteException;

    public void placeBid(int itemId, float bid, ClientId bidder)
        throws RemoteException;

    public void closeListing(int itemId)
        throws RemoteException;

    // encrypts any Serializable object
    public SealedObject aesEncrypt(Serializable toEncrypt)
        throws RemoteException, NoSuchAlgorithmException, InvalidKeySpecException;

    // decrypts any SealedObject
    public Object aesDecrypt(SealedObject toDecrypt)
        throws RemoteException, NoSuchAlgorithmException, InvalidKeySpecException;
    
}
