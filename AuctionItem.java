import java.io.Serializable;
import java.util.HashMap;

public class AuctionItem implements Serializable {
    
    private static final long serialVersionUID = 6232100136411267879L;

    private int itemId;                 // Unique id number
    private String itemTitle;           // Item name
    private float price;                // Current price of item
    private float reserve;              // Minimum bid for item to sell
    private String itemDescription;     // Short description of item
    private String itemCondition;       // Condition of item
    private ClientId seller;            // Seller ClientId
    private ClientId highestBidder;     // Highest bidders ClientId
    private boolean active;             // True/False if active or not

    private HashMap<Integer, Float> bidders;    // Contains all bidders and their last bid

    public AuctionItem(int itemId, String itemTitle, float price, float reserve, String itemDescription, String itemCondition, ClientId seller) {
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.itemCondition = itemCondition;
        this.price = price;
        this.reserve = reserve;
        this.seller = seller;
        this.active = true;
        if(itemDescription.equals(""))
            this.itemDescription = "No Description.";

        bidders = new HashMap<Integer, Float>();
    }

    public void bid(float bid, ClientId bidder) {
        price = bid;
        highestBidder = bidder;

        if(bidders.containsKey(bidder.getId()))
            bidders.remove(bidder.getId());
            
        // records all bidders in hashmap
        bidders.put(bidder.getId(), bid);
    }

    //-------------------------------------------Get Methods------------------------------------------------------------------

    public int getId() {
        return itemId;
    }
    public String getTitle() {
        return itemTitle;
    }
    public String getDescription() {
        return itemDescription;
    }
    public String getCondition() {
        return itemCondition;
    }
    public float getPrice() {
        return price;
    }
    public float getReserve() {
        return reserve;
    }
    public ClientId getSeller() {
        return seller;
    }
    public ClientId getBidder() {
        return highestBidder;
    }
    public boolean isActive() {
        return active;
    }
    public void close() {
        active = false;
    }
    public ClientId getHighestBidder() {
        return highestBidder;
    }
    public HashMap<Integer, Float> getBidders() {
        return bidders;
    }
    
    public String toString() {
        return "ID: " + Integer.toString(itemId) + 
                "\nItem Name: " + itemTitle + 
                "\nPrice: \u00A3" + String.format("%.2f", price) +
                "\nDescription: " + itemDescription +
                "\nCondition: " + itemCondition +
                "\n";
    }
}