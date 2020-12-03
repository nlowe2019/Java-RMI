import javax.swing.*;
import java.util.ArrayList;

public class ClientGUI {

    private AuctionClient client;       // Link to Client
 
    // Main Containers 
    private JFrame frame;               // JFrame - root container
    private JTabbedPane tab;            // Tabbed Pane - Switches between pages
    private ArrayList<GUIPage> pages;   // List of pages to add to tabbed pane

    public ClientGUI(AuctionClient c) {

        client = c;

        // Initialise Swing Components
        frame = new JFrame();
        tab = new JTabbedPane();

        tab.addTab("Login", new LoginGUI(client)); 

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

    public void login() {

        tab.removeAll();

        pages = new ArrayList<>();

        // Add desired GUI pages

        pages.add(new AllListingsGUI(client));
        pages.add(new MyListingsGUI(client));
        pages.add(new BidsGUI(client));
        pages.add(new SearchIdGUI(client));

        // Adds pages to Tabbed Pane

        pages.forEach((page) -> tab.addTab(page.getTitle(), page)); 
        frame.pack();
    }
}