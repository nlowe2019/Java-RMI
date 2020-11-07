import javax.swing.*;
import java.awt.*;

import javax.crypto.*;
import java.rmi.Naming;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;

public class AuctionClient {

    public static void main(String[] args) {

        ClientId id = new ClientId(1);

        try {
            // Encrypt clientId object
            SealedObject sealedId = aesEncrypt(id);

            // Create the reference to the remote object through the remiregistry
            Auction auction = (Auction) Naming.lookup("rmi://localhost/AuctionService");

            // Use auction reference to call remote methods

            // Example of auction item without decryption:
            System.out.println("\nItem 5235 unencrypted: " + auction.getSpec(5235, sealedId) + "\n");
            // Auction items with encryption
            System.out.println(aesDecrypt(auction.getSpec(5235, aesEncrypt(id))));
            System.out.println(aesDecrypt(auction.getSpec(284, aesEncrypt(id))));
            System.out.println(aesDecrypt(auction.getSpec(1503, aesEncrypt(id))));
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

    public static Object aesDecrypt(SealedObject sealedItem) {
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

    public static SealedObject aesEncrypt(Serializable toEncrypt) throws java.rmi.RemoteException, InvalidKeySpecException {
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
