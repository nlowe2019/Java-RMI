import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

public class BidsGUI extends GUIPage implements ActionListener {

    private String title = "Bids";
    private AuctionClient client;

    // Page Components:
    private JPanel detailsPanel;            // Listing details panel components
    private JLabel nameTag;
    private JLabel name;
    private JLabel currentBidTag;
    private JLabel currentBid;
    private JLabel conditionTag;
    private JLabel myBidTag;
    private JLabel myBid;
    private JLabel condition;
    private JLabel descriptionTag;
    private JLabel description;
    private JLabel statusTag;
    private JLabel status;

    private JPanel allResultsPanel;         // Results panel components
    private JButton refreshButton;
    private JTable listingsTable;
    private JScrollPane tableScrollPane;
    private JLabel selectedName;
    private JLabel selectedPrice;
    private JButton bidButton;
    private JTextField bidInput;

    // Auction Data

    private HashMap<Integer, AuctionItem> items;
    private AuctionItem selectedItem;

    public BidsGUI(AuctionClient c) {
        client = c;
        buildPage();
    }

    public void buildPage() {

        allResultsPanel = new JPanel();
        listingsTable = new JTable();
        refreshButton = new JButton("Refresh");
        tableScrollPane = new JScrollPane();
        selectedName = new JLabel("");
        selectedPrice = new JLabel("");
        bidButton = new JButton("Place Bid");
        bidInput = new JTextField("");

        detailsPanel = new JPanel();
        nameTag = new JLabel("Name");
        name = new JLabel("");
        currentBidTag = new JLabel("Starting Price");
        currentBid = new JLabel("");
        myBidTag = new JLabel("Reserve Price");
        myBid = new JLabel("");
        conditionTag = new JLabel("Condition");
        condition = new JLabel("");
        descriptionTag = new JLabel("Description");
        description = new JLabel("");
        statusTag = new JLabel("Status");
        status = new JLabel("");

        bidButton.setEnabled(false);
        refreshButton.addActionListener(this);
        bidButton.addActionListener(this);
        bidInput.getDocument().addDocumentListener(new DocumentListener(){
            public void removeUpdate(DocumentEvent e) {
                if (bidInput.getText().equals("") || selectedItem == null)
                    bidButton.setEnabled(false);
            }
            public void insertUpdate(DocumentEvent e) {
                if (!bidInput.getText().equals("") && selectedItem != null)
                    bidButton.setEnabled(true);
            }
            public void changedUpdate(DocumentEvent e) {
            }
        });

        // Specifies Add Listing Box Layout

        detailsPanel.setBorder(BorderFactory.createTitledBorder("Add Item"));

        GroupLayout detailsPanelLayout = new GroupLayout(detailsPanel);
        detailsPanel.setLayout(detailsPanelLayout);
        detailsPanelLayout.setHorizontalGroup(
            detailsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, detailsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(detailsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(detailsPanelLayout.createSequentialGroup()
                        .addGroup(detailsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(descriptionTag, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                            .addGroup(detailsPanelLayout.createSequentialGroup()
                                .addComponent(myBidTag, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(myBid, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(myBidTag)
                    .addComponent(myBid))
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
                .addContainerGap())
        );

        // Specifies Results Box Layout

        allResultsPanel.setBorder(BorderFactory.createTitledBorder("All Results"));

        GroupLayout allResultsPanelLayout = new GroupLayout(allResultsPanel);
        allResultsPanel.setLayout(allResultsPanelLayout);
        allResultsPanelLayout.setHorizontalGroup(
            allResultsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(allResultsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(allResultsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(tableScrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(refreshButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                    .addGroup(GroupLayout.Alignment.TRAILING, allResultsPanelLayout.createSequentialGroup()
                        .addComponent(selectedName, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(selectedPrice, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addComponent(bidInput, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bidButton)))
                .addContainerGap())
        );
        allResultsPanelLayout.setVerticalGroup(
            allResultsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, allResultsPanelLayout.createSequentialGroup()
                .addComponent(refreshButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tableScrollPane, GroupLayout.PREFERRED_SIZE, 372, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(allResultsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(bidButton)
                    .addComponent(bidInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectedName)
                    .addComponent(selectedPrice))
                .addContainerGap())
        );

        // Orders both panels in parent container

        GroupLayout thisLayout = new GroupLayout(this);
        this.setLayout(thisLayout);
        thisLayout.setHorizontalGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(thisLayout
                .createSequentialGroup().addContainerGap()
                .addComponent(detailsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(allResultsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap()));
        thisLayout
                .setVerticalGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, thisLayout.createSequentialGroup().addContainerGap()
                                .addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(detailsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(allResultsPanel, GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap()));
    }

    // Returns page title
    public String getTitle() {
        return title;
    }

    // Updates table with listings from auction server
    public void getListings() throws RemoteException, InvalidKeySpecException {
        
        items = client.remoteGetUserBids();

        Object[][] listings = new Object[items.keySet().size()][5];
        int i = 0;
        for (Integer key : items.keySet())
        {
            AuctionItem item = items.get(key);
            listings[i][0] = item.getId();
            listings[i][1] = item.getTitle();
            listings[i][2] = String.format("£%.2f", item.getPrice());
            listings[i][3] = item.getCondition();
            listings[i][4] = item.getDescription();
            i++;
        }

        listingsTable.setModel(new DefaultTableModel(listings,
                new String[] { "Item ID", "Name", "Current Bid", "Condition", "Description" }) {
                private static final long serialVersionUID = 1L;
                Class[] types = new Class[] { Integer.class, String.class, String.class,
                    String.class, String.class };
            boolean[] canEdit = new boolean[] { false, false, false, false, false };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        listingsTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        listingsTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        listingsTable.getColumnModel().getColumn(2).setPreferredWidth(60);
        listingsTable.getColumnModel().getColumn(3).setPreferredWidth(46);
        tableScrollPane.setViewportView(listingsTable);
        listingsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                rowSelect(evt);
            }
        });
        
        selectedItem = null;
        bidInput.setText("");
        selectedName.setText("");
        selectedPrice.setText("");
        
        if(selectedItem != null)
            selectedItem = items.get(selectedItem.getId());
    }

    public void showResults() {
        name.setText(selectedItem.getTitle());
        currentBid.setText(String.format("£%.2f", selectedItem.getPrice()));
        myBid.setText(String.format("£%.2f", selectedItem.getBidders().get(client.getId().getId())));
        condition.setText(selectedItem.getCondition());
        description.setText("<html><p style=\"width:180px\">" + selectedItem.getDescription() + "</p></html>");
        if(selectedItem.isActive()) {
            status.setText("Active");
        } else {
            if(!selectedItem.isActive() && selectedItem.getHighestBidder().getId() == client.getId().getId())
                status.setText("Successful");
            else
                status.setText("Unsuccessful");
        }
    }
    
    //-----------------------------------------------Event Listeners-----------------------------------------------------------------------

    // Used for button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        try {

            // Refresh Button

            if (e.getSource() == refreshButton) {
                getListings();;
            
            // Bid Button
            
            } else if (e.getSource() == bidButton) {
                int itemid = selectedItem.getId();
                float bid = Float.parseFloat(bidInput.getText());
                float currentbid = selectedItem.getPrice();
                if(bid > currentbid)
                    client.remotePlaceBid(itemid, bid);
                else
                    JOptionPane.showMessageDialog(null, "Bid must be greater than current price");
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
        selectedName.setText(selectedItem.getTitle());
        selectedPrice.setText(String.format("£%.2f", selectedItem.getPrice()));
        showResults();
    }
}