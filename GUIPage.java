import javax.swing.JPanel;

abstract class GUIPage extends JPanel {
    
    private String title;
    private AuctionClient client;

    public String getTitle() {
        return title;
    }
}