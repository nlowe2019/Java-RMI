import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.security.spec.InvalidKeySpecException;

public class LoginGUI extends GUIPage implements ActionListener {

    private static final long serialVersionUID = 2101866983500522994L;
    private String title = "Login";
    private AuctionClient client;

    // Page Components:

    private JPanel loginPanel;
    private JPanel registerPanel;

    private JLabel loginEmailTag;
    private JLabel registerNameTag;
    private JLabel registerEmailTag;

    private JTextField loginEmailInput;
    private JTextField registerNameInput;
    private JTextField registerEmailInput;

    private JButton loginButton;
    private JButton registerButton;

    public LoginGUI(AuctionClient c) {
        client = c;
        buildPage();
    }

    public void buildPage() {

        loginPanel = new JPanel();
        loginEmailTag = new JLabel("E-mail");
        loginEmailInput = new JTextField();
        loginButton = new JButton("Login");

        registerPanel = new JPanel();
        registerNameTag = new JLabel("Name");
        registerNameInput = new JTextField();
        registerEmailTag = new JLabel("E-mail");
        registerEmailInput = new JTextField();
        registerButton = new JButton("Register");

        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        registerPanel.setBorder(BorderFactory.createTitledBorder(loweredetched, "Register"));

        GroupLayout registerPanelLayout = new GroupLayout(registerPanel);
        registerPanel.setLayout(registerPanelLayout);
        registerPanelLayout.setHorizontalGroup(registerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(registerPanelLayout.createSequentialGroup().addContainerGap().addGroup(registerPanelLayout
                        .createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(registerButton)
                        .addGroup(registerPanelLayout.createSequentialGroup()
                                .addGroup(registerPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(registerEmailTag, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                        .addComponent(registerNameTag, GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(registerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(registerNameInput, GroupLayout.PREFERRED_SIZE, 200,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addComponent(registerEmailInput, GroupLayout.PREFERRED_SIZE, 200,
                                                GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        registerPanelLayout.setVerticalGroup(registerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(registerPanelLayout.createSequentialGroup().addContainerGap()
                        .addGroup(registerPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(registerNameTag).addComponent(registerNameInput,
                                        GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(registerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(registerEmailTag).addComponent(registerEmailInput,
                                        GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(registerButton)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        loginPanel.setBorder(BorderFactory.createTitledBorder(loweredetched, "Login"));

        GroupLayout loginPanelLayout = new GroupLayout(loginPanel);
        loginPanel.setLayout(loginPanelLayout);
        loginPanelLayout
                .setHorizontalGroup(
                        loginPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(GroupLayout.Alignment.TRAILING, loginPanelLayout.createSequentialGroup()
                                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(loginPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addComponent(loginButton)
                                                .addGroup(loginPanelLayout.createSequentialGroup()
                                                        .addComponent(loginEmailTag, GroupLayout.PREFERRED_SIZE, 80,
                                                                GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(loginEmailInput, GroupLayout.PREFERRED_SIZE, 200,
                                                                GroupLayout.PREFERRED_SIZE)))
                                        .addGap(456, 456, 456)));
        loginPanelLayout.setVerticalGroup(loginPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(loginPanelLayout.createSequentialGroup().addGap(9, 9, 9)
                        .addGroup(loginPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(loginEmailInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(loginEmailTag))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(loginButton)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        GroupLayout pageLayout = new GroupLayout(this);
        this.setLayout(pageLayout);
        pageLayout.setHorizontalGroup(pageLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(pageLayout.createSequentialGroup().addContainerGap()
                        .addGroup(pageLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(loginPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                        Short.MAX_VALUE)
                                .addComponent(registerPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap()));
        pageLayout
                .setVerticalGroup(
                        pageLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(pageLayout.createSequentialGroup().addContainerGap()
                                        .addComponent(loginPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(registerPanel, GroupLayout.PREFERRED_SIZE,
                                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(228, Short.MAX_VALUE)));
    }

    public String getTitle() {
        return title;
    }

    public void login() {
        System.out.println("login test");
    }

    public void register() {
        System.out.println("register test");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {

            // Login Button

            if (e.getSource() == loginButton) {
                if (loginEmailInput.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter your e-mail.");
                } else {
                    login();
                    client.remoteGetSpec(0);
                }
            }

            // Register Button

            if (e.getSource() == registerButton) {
                if (registerEmailInput.getText().equals("") || registerNameInput.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill out all fields.");
                } else {
                    register();
                    client.remoteGetSpec(0);
                }
            }

        } catch (RemoteException | InvalidKeySpecException exception) {
            exception.printStackTrace();
        }
    }
}