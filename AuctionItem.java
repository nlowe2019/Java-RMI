import java.io.Serializable;
import java.util.HashMap;

public class AuctionItem implements Serializable {
    
    private static final long serialVersionUID = 6232100136411267879L;

    private int itemId;
    private String itemTitle;
    private float price;
    private float reserve;
    private String itemDescription;
    private String itemCondition;
    private ClientId seller;
    private ClientId highestBidder;
    private boolean active;

    private HashMap<Integer, Float> bidders;

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
            itemDescription = "No Description.";

        bidders = new HashMap<Integer, Float>();
    }

    public void bid(float bid, ClientId bidder) {
        if(bid > price && bidder != seller) {
            price = bid;
            highestBidder = bidder;

            bidders.put(bidder.getId(), bid);
        }
    }

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