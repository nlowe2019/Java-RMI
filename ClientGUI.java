import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.security.spec.InvalidKeySpecException;

public class ClientGUI implements ActionListener {

    private AuctionClient client;

    private JFrame frame;
    private JPanel inputPanel;
    private JPanel resultsPanel;

    private JLabel nametag;
    private JLabel bidtag;
    private JLabel descriptiontag;
    private JLabel conditiontag;
    private JLabel name;
    private JLabel bid;
    private JLabel condition;
    private JLabel description;

    private JTextField inputID;
    private JButton submitID;

    public ClientGUI(AuctionClient client) {

        this.client = client;

        frame = new JFrame();

        inputPanel = new JPanel();
        inputID = new JTextField();
        submitID = new JButton();
        resultsPanel = new JPanel();
        nametag = new JLabel();
        bidtag = new JLabel();
        descriptiontag = new JLabel();
        conditiontag = new JLabel();
        name = new JLabel();
        bid = new JLabel();
        condition = new JLabel();
        description = new JLabel();
        description.setVerticalAlignment(1);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        inputPanel.setBorder(BorderFactory.createTitledBorder(loweredetched, "Search by Item ID"));

        inputID.setToolTipText("Enter Item ID");
        submitID.addActionListener(this);

        submitID.setText("Submit");

        GroupLayout inputPanelLayout = new GroupLayout(inputPanel);
        inputPanel.setLayout(inputPanelLayout);
        inputPanelLayout.setHorizontalGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(inputPanelLayout.createSequentialGroup().addContainerGap()
                        .addComponent(inputID, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(submitID)
                        .addContainerGap(142, Short.MAX_VALUE)));
        inputPanelLayout.setVerticalGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(inputPanelLayout.createSequentialGroup().addContainerGap()
                        .addGroup(inputPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(inputID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(submitID))
                        .addContainerGap(39, Short.MAX_VALUE)));

        resultsPanel.setBorder(BorderFactory.createTitledBorder(loweredetched, "Result"));

        nametag.setText("Name");
        bidtag.setText("Current Bid");
        descriptiontag.setText("Description");
        conditiontag.setText("Condition");
        name.setText("");
        bid.setText("");
        condition.setText("");
        description.setText("");

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

        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(inputPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultsPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(resultsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitID) {
            try {
                AuctionItem item = client.remoteGetSpec(Integer.parseInt(inputID.getText()));
                if(item == null)
                    JOptionPane.showMessageDialog(frame, "Item not found.");
                name.setText(item.getTitle());
                bid.setText(String.format("£%.2f", item.getPrice()));
                condition.setText(item.getCondition());
                description.setText("<html><p style=\"width:100px\">" + item.getDescription() + "</p></html>");
            } catch (NumberFormatException | RemoteException | InvalidKeySpecException | NullPointerException e1) {
            }
        }


    }
}