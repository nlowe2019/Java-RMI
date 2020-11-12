import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

public class MyListingsGUI extends GUIPage implements ActionListener {

    private static final long serialVersionUID = -3154274472897087620L;
    private String title = "My Listings";
    private AuctionClient client;

    // Page Components:

    private JPanel detailsPanel;            // Listing details panel components
    private JLabel nameTag;
    private JLabel name;
    private JLabel currentBidTag;
    private JLabel currentBid;
    private JLabel reserveTag;
    private JLabel reserve;
    private JLabel conditionTag;
    private JLabel condition;
    private JLabel descriptionTag;
    private JLabel description;
    private JLabel statusTag;
    private JLabel status;
    private JButton closeButton;

    private JPanel closedPanel; 
    private JLabel finalBidTag;
    private JLabel finalBid;
    private JLabel buyerNameTag;
    private JLabel buyerName;
    private JLabel emailTag;
    private JLabel email;

    private JPanel tablePanel;         // Results panel components
    private JButton refreshButton;
    private JTable myListingsTable;
    private JScrollPane tableScrollPane;

    // Auction Data

    private HashMap<Integer, AuctionItem> items;
    private AuctionItem selectedItem;

    public MyListingsGUI(AuctionClient c) {
        client = c;
        buildPage();
    }

    public void buildPage() {

        tablePanel = new JPanel();
        myListingsTable = new JTable();
        refreshButton = new JButton("Refresh");
        tableScrollPane = new JScrollPane();

        detailsPanel = new JPanel();
        nameTag = new JLabel("Name");
        currentBidTag = new JLabel("Current Bid");
        reserveTag = new JLabel("Reserve Price");
        conditionTag = new JLabel("Condition");
        descriptionTag = new JLabel("Description");
        statusTag = new JLabel("Status");
        name = new JLabel(" ");
        currentBid = new JLabel(" ");
        reserve = new JLabel(" ");
        condition = new JLabel(" ");
        description = new JLabel(" ");
        status = new JLabel(" ");
        closeButton = new JButton("Close Listing");

        closedPanel = new JPanel();
        finalBidTag = new JLabel("Sold for");
        finalBid = new JLabel(" ");
        buyerNameTag = new JLabel("Buyer");
        buyerName = new JLabel(" ");
        emailTag = new JLabel("E-mail");
        email = new JLabel(" ");

        refreshButton.addActionListener(this);
        closeButton.addActionListener(this);
        closedPanel.setVisible(false);
        
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        detailsPanel.setBorder(BorderFactory.createTitledBorder(loweredetched, "Listing Details"));

        GroupLayout closedPanelLayout = new GroupLayout(closedPanel);
        closedPanel.setLayout(closedPanelLayout);
        closedPanelLayout.setHorizontalGroup(
            closedPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(closedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(closedPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(closedPanelLayout.createSequentialGroup()
                        .addComponent(finalBidTag, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(finalBid, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(closedPanelLayout.createSequentialGroup()
                        .addComponent(buyerNameTag, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buyerName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(GroupLayout.Alignment.TRAILING, closedPanelLayout.createSequentialGroup()
                        .addComponent(emailTag, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(email, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        closedPanelLayout.setVerticalGroup(
            closedPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(closedPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(closedPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(finalBidTag)
                    .addComponent(finalBid))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(closedPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(buyerNameTag)
                    .addComponent(buyerName))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(closedPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(emailTag)
                    .addComponent(email))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        GroupLayout detailsPanelLayout = new GroupLayout(detailsPanel);
        detailsPanel.setLayout(detailsPanelLayout);
        detailsPanelLayout.setHorizontalGroup(
            detailsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, detailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detailsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, detailsPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(closeButton))
                    .addGroup(detailsPanelLayout.createSequentialGroup()
                        .addGroup(detailsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(descriptionTag, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                            .addGroup(detailsPanelLayout.createSequentialGroup()
                                .addComponent(reserveTag, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(reserve, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
                            .addGroup(detailsPanelLayout.createSequentialGroup()
                                .addComponent(currentBidTag, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(currentBid, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
                            .addGroup(detailsPanelLayout.createSequentialGroup()
                                .addComponent(nameTag, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(name, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
                            .addGroup(detailsPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(description, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
                                .addGroup(detailsPanelLayout.createSequentialGroup()
                                    .addComponent(conditionTag, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(condition, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)))
                            .addGroup(detailsPanelLayout.createSequentialGroup()
                                .addComponent(statusTag, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(status, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(closedPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        detailsPanelLayout.setVerticalGroup(
            detailsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(detailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detailsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nameTag)
                    .addComponent(name))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detailsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(currentBidTag)
                    .addComponent(currentBid))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detailsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(reserveTag)
                    .addComponent(reserve))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(detailsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(conditionTag)
                    .addComponent(condition))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(descriptionTag)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(description, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(detailsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(statusTag)
                    .addComponent(status))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closedPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tablePanel.setBorder(BorderFactory.createTitledBorder(loweredetched, "My Listings"));


        GroupLayout tablePanelLayout = new GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, tablePanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(refreshButton, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
                    .addComponent(tableScrollPane, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE))
                .addContainerGap())
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addComponent(refreshButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableScrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        GroupLayout pageLayout = new GroupLayout(this);
        this.setLayout(pageLayout);
        pageLayout.setHorizontalGroup(
            pageLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(pageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(detailsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tablePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pageLayout.setVerticalGroup(
            pageLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, pageLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pageLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(tablePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(detailsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }

    // Updates table with listings from auction server
    public void getMyListings() throws RemoteException, InvalidKeySpecException {
        
        items = client.remoteGetAll();

        Object[][] listings = new Object[items.keySet().size()][4];
        int i = 0;
        for (Integer key : items.keySet())
        {
            AuctionItem item = items.get(key);
            if(item.getSeller().getId() == client.getId().getId()) {
                listings[i][0] = item.getId();
                listings[i][1] = item.getTitle();
                listings[i][2] = String.format("£%.2f", item.getPrice());
                listings[i][3] = String.format("£%.2f", item.getReserve());
                i++;
            }
        }

        myListingsTable.setModel(new DefaultTableModel(listings,
            new String[] { "Item ID", "Name", "Current Bid", "Reserve Price"}) {
            private static final long serialVersionUID = 1L;
            Class[] types = new Class[] { Integer.class, String.class, String.class, String.class };
            boolean[] canEdit = new boolean[] { false, false, false, false};

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        myListingsTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        myListingsTable.getColumnModel().getColumn(1).setPreferredWidth(160);
        tableScrollPane.setViewportView(myListingsTable);
        myListingsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                rowSelect(evt);
            }
        });
        
        if(selectedItem != null)
            selectedItem = items.get(selectedItem.getId());
    }

    public void closeListing() {
        try {
            client.remoteCloseListing(selectedItem.getId());
            getMyListings();
            showResults();
        } catch (RemoteException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    public void showResults() {
        name.setText(selectedItem.getTitle());
        currentBid.setText(String.format("£%.2f", selectedItem.getPrice()));
        reserve.setText(String.format("£%.2f", selectedItem.getReserve()));
        condition.setText(selectedItem.getCondition());
        description.setText("<html><p style=\"width:180px\">" + selectedItem.getDescription() + "</p></html>");
        if(!selectedItem.isActive() && selectedItem.getPrice() > selectedItem.getReserve()) {
            closedPanel.setVisible(true);
            status.setText("Successful");
            finalBid.setText(String.format("£%.2f", selectedItem.getPrice()));
            buyerName.setText(selectedItem.getBidder().getName());
            email.setText(selectedItem.getBidder().getEmail());
        } else if(!selectedItem.isActive() && !(selectedItem.getPrice() > selectedItem.getReserve())) {
            status.setText("Unsuccessful");
            closedPanel.setVisible(false);
        } else {
            status.setText("Active");
            closedPanel.setVisible(false);
        }
    }

    // Returns page title
    public String getTitle() {
        return title;
    }

    //-----------------------------------------------Event Listeners-----------------------------------------------------------------------

   // Used for button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        try {

            if (e.getSource() == refreshButton) {
                    getMyListings();
            } else if (e.getSource() == closeButton) {
                if(selectedItem != null) {
                    if(selectedItem.isActive())
                        closeListing();
                    else
                        JOptionPane.showMessageDialog(null, "This listing has already been closed");
                }
            }

        } catch (RemoteException | InvalidKeySpecException e1) {
               e1.printStackTrace();
        }
    }

    // Waits for table rows to be selected
    private void rowSelect(MouseEvent evt) {                                     
        JTable source = (JTable)evt.getSource();
        int row = source.rowAtPoint( evt.getPoint() );
        int itemid = Integer.parseInt(source.getModel().getValueAt(row, 0) + "");
        selectedItem = items.get(itemid);
        showResults();
    }
}