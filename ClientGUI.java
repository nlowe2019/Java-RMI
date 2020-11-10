import javax.swing.*;
import java.util.ArrayList;

public class ClientGUI {

    private AuctionClient client;       // Link to Client
 
    // Main Containers 
    private JFrame frame;               // JFrame - root container
    private JTabbedPane tab;            // Tabbed Pane - Switches between pages
    private ArrayList<GUIPage> pages;   // List of pages to add to tabbed pane

    public ClientGUI(AuctionClient client) {

        this.client = client;

        // Initialise Swing Components
        frame = new JFrame();
        tab = new JTabbedPane();
        pages = new ArrayList<>();

        // Add desired GUI pages

        pages.add(new SearchIdGUI(client));
        pages.add(new AllListingsGUI(client));

        // Adds pages to Tabbed Pane

        pages.forEach((page) -> tab.addTab(page.getTitle(), page)); 

        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(tab)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(tab, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
        );

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Auction");
        frame.pack();
        frame.setVisible(true);
    }
}