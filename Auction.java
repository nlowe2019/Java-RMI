import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SealedObject;

public interface Auction
          extends java.rmi.Remote {	

    // matches item with given itemId
    // returns encrypted Auction item as SealedObject
    public SealedObject getSpec(int itemId, SealedObject clientId)
        throws java.rmi.RemoteException, InvalidKeySpecException;

    // Returns items collection
    public SealedObject getAll(SealedObject ClientId)
        throws java.rmi.RemoteException;

    // adds new item to auction item hashmap
    public void addListing(int itemId, String itemTitle, float price, String itemDescription, String itemCondition, SealedObject seller) 
        throws java.rmi.RemoteException;

    // encrypts any Serializable object
    public SealedObject aesEncrypt(Serializable toEncrypt)
        throws java.rmi.RemoteException, NoSuchAlgorithmException, InvalidKeySpecException;

    // decrypts any SealedObject
    public Object aesDecrypt(SealedObject toDecrypt)
        throws java.rmi.RemoteException, NoSuchAlgorithmException, InvalidKeySpecException;
    
}
