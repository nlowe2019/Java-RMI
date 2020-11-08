public class AuctionItem implements java.io.Serializable{
    
    private static final long serialVersionUID = 6232100136411267879L;

    private int itemId;
    private String itemTitle;
    private float price;
    private String itemDescription;
    private String itemCondition;

    public AuctionItem(int itemId, String itemTitle, float price, String itemDescription, String itemCondition) {
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.itemCondition = itemCondition;
        this.price = price;
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
    
    public String toString() {
        return "ID: " + Integer.toString(itemId) + 
                "\nItem Name: " + itemTitle + 
                "\nPrice: \u00A3" + String.format("%.2f", price) +
                "\nDescription: " + itemDescription +
                "\nCondition: " + itemCondition +
                "\n";
    }
}