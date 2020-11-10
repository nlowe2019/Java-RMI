import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ConcurrentHashMap;

public class AllListingsGUI extends GUIPage implements ActionListener {

    private String title = "All Listings";
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
    private JTextField descriptioninput;
    private JButton submitButton;

    private JPanel allResultsPanel;         // Results panel components
    private JButton refreshButton;
    private JTable listingsTable;
    private JScrollPane tableScrollPane;

    // Auction Data

    ConcurrentHashMap<Integer, AuctionItem> items;

    public AllListingsGUI(AuctionClient c) {
        client = c;
        buildPage();
    }

    public void buildPage() {

        allResultsPanel = new JPanel();
        listingsTable = new JTable();
        refreshButton = new JButton("Refresh");
        tableScrollPane = new JScrollPane();

        addPanel = new JPanel();
        nametag = new JLabel("Name");
        nameinput = new JTextField();
        startpricetag = new JLabel("Starting Price");
        startpriceinput = new JTextField();
        reservetag = new JLabel("Reserve Price");
        reserveinput = new JTextField();
        conditiontag = new JLabel("Condition");
        conditioncombo = new JComboBox<>(new DefaultComboBoxModel<>(new String[] { "New", "Used", "Damaged" }));
        descriptiontag = new JLabel("Description");
        descriptioninput = new JTextField();
        submitButton = new JButton("Submit");

        // Specifies Add Listing Box Layout

        addPanel.setBorder(BorderFactory.createTitledBorder("Add Item"));

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

        allResultsPanel.setBorder(BorderFactory.createTitledBorder("All Results"));

        refreshButton.addActionListener(this);

        GroupLayout allResultsPanelLayout = new GroupLayout(allResultsPanel);
        allResultsPanel.setLayout(allResultsPanelLayout);
        allResultsPanelLayout.setHorizontalGroup(allResultsPanelLayout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(allResultsPanelLayout.createSequentialGroup().addContainerGap()
                        .addGroup(allResultsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(tableScrollPane, GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                                .addGroup(allResultsPanelLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(refreshButton, GroupLayout.PREFERRED_SIZE, 101,
                                                GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap()));
        allResultsPanelLayout.setVerticalGroup(allResultsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING,
                        allResultsPanelLayout.createSequentialGroup().addComponent(refreshButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tableScrollPane, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap()));

        // Orders both panels in parent container

        GroupLayout thisLayout = new GroupLayout(this);
        this.setLayout(thisLayout);
        thisLayout.setHorizontalGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(thisLayout
                .createSequentialGroup().addContainerGap()
                .addComponent(addPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(allResultsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap()));
        thisLayout
                .setVerticalGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, thisLayout.createSequentialGroup().addContainerGap()
                                .addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
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
        
        items = client.remoteGetAll();

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
            Class[] types = new Class[] { java.lang.Integer.class, java.lang.String.class, java.lang.String.class,
                    java.lang.String.class, java.lang.String.class };
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
    }
    
    //-----------------------------------------------Event Listeners-----------------------------------------------------------------------

    // Used for button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == refreshButton) {
            try {
                getListings();
            } catch (RemoteException | InvalidKeySpecException e1) {
                e1.printStackTrace();
            }
        }
    }

    // Waits for table rows to be selected
    private void rowSelect(MouseEvent evt) {                                     
        JTable source = (JTable)evt.getSource();
               int row = source.rowAtPoint( evt.getPoint() );
               String s=source.getModel().getValueAt(row, 0)+"";
    }
}