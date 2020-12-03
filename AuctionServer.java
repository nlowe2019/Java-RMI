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

public class AuctionServer {

    public AuctionServer() throws Exception {

        System.out.println("server class");
        try {
            Auction auction = new AuctionImpl();
            Naming.rebind("rmi://localhost/AuctionService", auction);   // Bind AuctionImpl to the local rmiregistry
        } 
        catch (Exception e) {
            System.out.println("Server Error: " + e);
        }
    }

    public static void main(String args[]) throws Exception {

        new AuctionServer();
    }
}