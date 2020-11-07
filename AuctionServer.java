import java.rmi.Naming;	// Import naming classes to bind to rmiregistry

public class AuctionServer {

    public AuctionServer() {

        try {
            Auction auction = new AuctionImpl();
            Naming.rebind("rmi://localhost/AuctionService", auction);   // Bind AuctionImpl to the local rmiregistry
        } 
        catch (Exception e) {
            System.out.println("Server Error: " + e);
        }
    }

    public static void main(String args[]) {

        new AuctionServer();
    }
}