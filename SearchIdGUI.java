import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.security.spec.InvalidKeySpecException;

public class SearchIdGUI extends GUIPage implements ActionListener{

    private String title = "Search ID";
    private AuctionClient client;

    // Page Components: 
    private JPanel inputPanel;      // Search Box
    private JPanel resultsPanel;    // Results Box
 
    private JLabel nametag;         // Item Variable Names...
    private JLabel bidtag; 
    private JLabel descriptiontag; 
    private JLabel conditiontag; 
    private JLabel name;            // Item Variable Values...
    private JLabel bid; 
    private JLabel condition; 
    private JLabel description; 
 
    private JTextField inputID;     // Text input for ID
    private JButton submitID;       // Button to confirm input and display result

    public SearchIdGUI(AuctionClient c) {
        client = c;
        buildPage();
    }

    public void buildPage() {

        inputPanel = new JPanel();
        inputID = new JTextField();
        submitID = new JButton();
        resultsPanel = new JPanel();
        nametag = new JLabel("Name");
        bidtag = new JLabel("Current Bid");
        descriptiontag = new JLabel("Description");
        conditiontag = new JLabel("Condition");
        name = new JLabel("");
        bid = new JLabel("");
        condition = new JLabel("");
        description = new JLabel("");
        description.setVerticalAlignment(1);
        inputID.setToolTipText("Enter Item ID");
        submitID.addActionListener(this);
        submitID.setText("Submit");

        // Specifies Search Box Layout

        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        inputPanel.setBorder(BorderFactory.createTitledBorder(loweredetched, "Search by Item ID"));

        GroupLayout inputPanelLayout = new GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);
        inputPanelLayout.setHorizontalGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(inputPanelLayout.createSequentialGroup().addContainerGap()
                        .addComponent(inputID, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(submitID)
                        .addContainerGap(142, Short.MAX_VALUE))
        );
        inputPanelLayout.setVerticalGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(inputPanelLayout.createSequentialGroup().addContainerGap()
                        .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(inputID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(submitID))
                        .addContainerGap(39, Short.MAX_VALUE))
        );

        // Specifies Results Box Layout

        resultsPanel.setBorder(BorderFactory.createTitledBorder(loweredetched, "Result"));

        GroupLayout resultsPanelLayout = new GroupLayout(resultsPanel);
        resultsPanel.setLayout(resultsPanelLayout);
        resultsPanelLayout.setHorizontalGroup(
            resultsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(resultsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(resultsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(conditiontag, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(descriptiontag, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bidtag, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nametag, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(resultsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(condition, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bid, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(name, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(description, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        resultsPanelLayout.setVerticalGroup(
            resultsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(resultsPanelLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(resultsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nametag)
                    .addComponent(name))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(resultsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(bidtag)
                    .addComponent(bid))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(resultsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(conditiontag)
                    .addComponent(condition))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(resultsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(resultsPanelLayout.createSequentialGroup()
                        .addComponent(descriptiontag)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(description, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                .addContainerGap())
        );

        // Orders input and results panels in parent container

        GroupLayout thisLayout = new GroupLayout(this);
        this.setLayout(thisLayout);
        thisLayout.setHorizontalGroup(
            thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(thisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(inputPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        thisLayout.setVerticalGroup(
            thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resultsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Search by ID Button
        if (e.getSource() == submitID) {
            try {
                // Remotely calls getSpec method to return Auciton Item
                AuctionItem item = client.remoteGetSpec(Integer.parseInt(inputID.getText()));
                if(item == null)
                    JOptionPane.showMessageDialog(null, "Item not found.");
                // Fill results box with item details
                name.setText(item.getTitle());
                bid.setText(String.format("£%.2f", item.getPrice()));
                condition.setText(item.getCondition());
                description.setText("<html><p style=\"width:200px\">" + item.getDescription() + "</p></html>");
            } catch (NumberFormatException | RemoteException | InvalidKeySpecException | NullPointerException e1) {
            }
        }
    }
}