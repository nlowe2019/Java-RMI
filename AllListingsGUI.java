import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

public class AllListingsGUI extends GUIPage implements ActionListener {

    private static final long serialVersionUID = -7751215197630930587L;
    private String title = "Active Listings";
    private AuctionClient client;

    // Page Components:

    private JPanel addPanel;                // Add listing panel components
    private JLabel nametag;
    private JTextField nameinput;
    private JLabel startpricetag;
    private JTextField startpriceinput;
    private JLabel reservetag;
    private JTextField reserveinput;
    private JLabel conditiontag;
    private JComboBox<String> conditioncombo;
    private JLabel descriptiontag;
    private JTextArea descriptioninput;
    private JButton submitButton;

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

    public AllListingsGUI(AuctionClient c) {
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

        addPanel = new JPanel();
        nametag = new JLabel("Name");
        nameinput = new JTextField("");
        startpricetag = new JLabel("Starting Price");
        startpriceinput = new JTextField("");
        reservetag = new JLabel("Reserve Price");
        reserveinput = new JTextField("");
        conditiontag = new JLabel("Condition");
        conditioncombo = new JComboBox<>(new DefaultComboBoxModel<>(new String[] { "New", "Used", "Damaged" }));
        descriptiontag = new JLabel("Description");
        descriptioninput = new JTextArea("");
        submitButton = new JButton("Submit");

        bidButton.setEnabled(false);
        submitButton.addActionListener(this);
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

        descriptioninput.setLineWrap(true);

        // Specifies Add Listing Box Layout
        
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        addPanel.setBorder(BorderFactory.createTitledBorder(loweredetched, "Add Item"));

        GroupLayout addPanelLayout = new GroupLayout(addPanel);
        addPanel.setLayout(addPanelLayout);
        addPanelLayout.setHorizontalGroup(addPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(addPanelLayout.createSequentialGroup().addContainerGap().addGroup(addPanelLayout
                        .createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(descriptiontag, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .addGroup(addPanelLayout.createSequentialGroup().addGroup(addPanelLayout
                                .createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(descriptioninput)
                                .addGroup(addPanelLayout.createSequentialGroup().addGroup(addPanelLayout
                                        .createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(conditioncombo, GroupLayout.Alignment.LEADING, 0,
                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(
                                                addPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(nametag, GroupLayout.DEFAULT_SIZE, 143,
                                                                Short.MAX_VALUE)
                                                        .addComponent(nameinput))
                                        .addGroup(
                                                addPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(startpricetag, GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(startpriceinput, GroupLayout.PREFERRED_SIZE, 143,
                                                                GroupLayout.PREFERRED_SIZE))
                                        .addGroup(
                                                addPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(reservetag, GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(reserveinput, GroupLayout.PREFERRED_SIZE, 143,
                                                                GroupLayout.PREFERRED_SIZE))
                                        .addComponent(conditiontag, GroupLayout.PREFERRED_SIZE, 143,
                                                GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 90, Short.MAX_VALUE)))
                                .addContainerGap())))
                .addGroup(GroupLayout.Alignment.TRAILING,
                        addPanelLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(submitButton)
                                .addContainerGap()));
        addPanelLayout.setVerticalGroup(addPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(addPanelLayout.createSequentialGroup().addContainerGap().addComponent(nametag)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameinput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(startpricetag)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(startpriceinput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(reservetag)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reserveinput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(conditiontag)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(conditioncombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(descriptiontag)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(descriptioninput, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
                                Short.MAX_VALUE)
                        .addComponent(submitButton).addContainerGap()));

        // Specifies Results Box Layout

        allResultsPanel.setBorder(BorderFactory.createTitledBorder(loweredetched, "All Results"));

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

        GroupLayout pageLayout = new GroupLayout(this);
        this.setLayout(pageLayout);
        pageLayout.setHorizontalGroup(pageLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(pageLayout
                .createSequentialGroup().addContainerGap()
                .addComponent(addPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(allResultsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap()));
        pageLayout
                .setVerticalGroup(pageLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, pageLayout.createSequentialGroup().addContainerGap()
                                .addGroup(pageLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(addPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
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
        
        items = client.remoteGetActiveListings();

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
            boolean[] canEdit = new boolean[] { false, false, false, false, false };

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
    }

    public boolean validateSubmition() {
        return !(nameinput.getText().equals("") || 
                startpriceinput.getText().equals("") || 
                reserveinput.getText().equals(""));
    }
    
    //-----------------------------------------------Event Listeners-----------------------------------------------------------------------

    // Used for button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        try {

            // Refresh Button

            if (e.getSource() == refreshButton) {
                getListings();

            // Submit Button

            } else if (e.getSource() == submitButton) {
                if(validateSubmition())
                    try {
                        String name = nameinput.getText();
                        float startBid = Float.parseFloat(startpriceinput.getText());
                        float reserve = Float.parseFloat(reserveinput.getText());
                        String condition = conditioncombo.getSelectedItem().toString();
                        String description = descriptioninput.getText();
                        if(reserve > startBid)
                            client.remoteAddListing(name, startBid, reserve, description, condition);
                        else
                            JOptionPane.showMessageDialog(null, "Reserve price must be greater than starting price");
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Invalid price entered");
                    }
                else
                    JOptionPane.showMessageDialog(null, "Please fill out all fields");
            
            // Bid Button
            
            } else if (e.getSource() == bidButton) {
                int itemid = selectedItem.getId();
                float bid = Float.parseFloat(bidInput.getText());
                float currentbid = selectedItem.getPrice();
                if(client.getId().getId() != items.get(itemid).getSeller().getId()) {
                    if(bid > currentbid)
                        client.remotePlaceBid(itemid, bid);
                    else
                        JOptionPane.showMessageDialog(null, "Bid must be greater than current price");
                } else
                    JOptionPane.showMessageDialog(null, "Cannot bid on your own listing");
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
    }
}