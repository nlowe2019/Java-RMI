import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ConcurrentHashMap;

public class MyListingsGUI extends GUIPage implements ActionListener {

    private AuctionClient client;

    private JButton closeButton;
    private JButton refreshButton;

    public MyListingsGUI(AuctionClient c) {
        client = c;
        buildPage();
    }

    public void buildPage() {













    }

    public void getListings() {

    }

    //-----------------------------------------------Event Listeners-----------------------------------------------------------------------

    // Used for button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        if (e.getSource() == refreshButton) {
            try {
                getListings();
            } catch (RemoteException | InvalidKeySpecException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == closeButton) {
            try {

            } catch (RemoteException | InvalidKeySpecException e1) {
                e1.printStackTrace();
            }
        }
        */
    }

    // Waits for table rows to be selected
    private void rowSelect(MouseEvent evt) {                                     
        JTable source = (JTable)evt.getSource();
        int row = source.rowAtPoint( evt.getPoint() );
        String s=source.getModel().getValueAt(row, 0)+"";
    }
}