import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.blocks.*;
import org.jgroups.util.RspList;
import org.jgroups.util.Util;
import org.jgroups.util.Base64.InputStream;
import org.jgroups.util.Base64.OutputStream;

public class AuctionReplica extends ReceiverAdapter {

    private JChannel channel;
    private HashMap<Integer, AuctionItem> items = new HashMap<Integer, AuctionItem>();

    public AuctionReplica() throws Exception {

        channel = new JChannel();
        items.put(1, new AuctionItem(1, "test", 4f, 44f, "new", "description", new ClientId(1,"nick", "sadmail.com")));
        channel.setReceiver(this);
        channel.connect("AuctionCluster");
        eventLoop();
        //channel.close();
    }

    public void eventLoop() {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            try {
                System.out.print("> "); System.out.flush();
                String line=in.readLine().toLowerCase();
                if(line.startsWith("quit") || line.startsWith("exit"))
                    break;
                Message msg=new Message(null, null, line);
                channel.send(msg);
            }
            catch(Exception e) {
            }
        }
    }

    public void viewAccepted(View new_view) {
        System.out.println("new view: " + new_view);
        try {
            channel.getState(null, 10000);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void receive(Message msg) {
        String line=msg.getSrc() + ": " + msg.getObject();
        System.out.println(line);
    }

    public void getState(OutputStream output) throws Exception {
        synchronized(items) {
            Util.objectToStream(items, new DataOutputStream(output));
        }
    }

    public void setState(InputStream input) throws Exception {
        HashMap<Integer, AuctionItem> hm;
        hm=(HashMap<Integer, AuctionItem>)Util.objectFromStream(new DataInputStream(input));
        synchronized(items) {
            items.clear();
            items = hm;
        }
        System.out.println(items.size() + "Auction listings updated");
    }
    
    public HashMap<Integer, AuctionItem> getActive() {
        HashMap<Integer, AuctionItem> activeItems = new HashMap<Integer, AuctionItem>();
        for (AuctionItem item : items.values()) {
            if (item.isActive())
                activeItems.put(item.getId(), item);
        }
        System.out.println("return listings");
        return activeItems;
    }

    public void add(AuctionItem item) throws Exception {
        items.put(item.getId(), item);
        System.out.println(items.get(item.getId()));
    }

    public static void main(String args[]) throws Exception {

        new AuctionReplica();
    }
}