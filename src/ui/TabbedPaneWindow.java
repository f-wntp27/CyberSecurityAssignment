package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabbedPaneWindow extends JPanel {
    public TabbedPaneWindow() {
        super(new GridLayout(1, 1));
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Generate Key Pair", new RSAGeneratorPanel());
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        tabbedPane.addTab("AES Encryptor", new AESEncrypterPanel());
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        tabbedPane.addTab("AES Decryptor", new AESDecrypterPanel());
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        add(tabbedPane);

        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }
}
