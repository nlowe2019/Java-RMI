import javax.swing.JPanel;

abstract class GUIPage extends JPanel {
    
    private static final long serialVersionUID = -7645444701253670590L;
    private String title;

    public String getTitle() {
        return title;
    }
}