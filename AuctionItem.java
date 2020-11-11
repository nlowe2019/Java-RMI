public class AuctionItem implements java.io.Serializable{
    
    private static final long serialVersionUID = 6232100136411267879L;

    private int itemId;
    private String itemTitle;
    private float price;
    private float reserve;
    private String itemDescription;
    private String itemCondition;
    private ClientId seller;
    private ClientId highestBidder;

    public AuctionItem(int itemId, String itemTitle, float price, float reserve, String itemDescription, String itemCondition, ClientId seller) {
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.itemCondition = itemCondition;
        this.price = price;
        this.reserve = reserve;
        this.seller = seller;
        if(itemDescription == "")
            itemDescription = "No Description.";

    }

    public void bid(float bid, ClientId bidder) {
        if(bid > price && bidder != seller) {
            price = bid;
            highestBidder = bidder;
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
    
    public String toString() {
        return "ID: " + Integer.toString(itemId) + 
                "\nItem Name: " + itemTitle + 
                "\nPrice: \u00A3" + String.format("%.2f", price) +
                "\nDescription: " + itemDescription +
                "\nCondition: " + itemCondition +
                "\n";
    }
}