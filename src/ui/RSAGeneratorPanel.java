package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import rsa.RSAKeyGenerator;
import util.Constants;

public class RSAGeneratorPanel extends JPanel implements ActionListener {
    private final String newline = "\n";

    private JButton generateButton;
    private JButton clearButton;
    private JButton showOutputDirButton;
    private JButton showPriKeyButton;
    private JButton showPubKeyButton;
    private JTextArea generateLog;

    public RSAGeneratorPanel() {
        setLayout(null);

        // Panel 1 - Generate button
        generateButton = new JButton("Generate key pair");
        clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 25));
        generateButton.addActionListener(this);
        clearButton.addActionListener(this);

        JPanel panel1 = new JPanel();
        panel1.setBounds(15, 15, 240, 110);
        panel1.setBorder(BorderFactory.createTitledBorder("Key pair generator"));
        panel1.add(generateButton);
        panel1.add(clearButton);

        // Panel 2 - Key generate log
        generateLog = new JTextArea(9, 45);
        generateLog.setEditable(false);
        JScrollPane generateLogScrollPane = new JScrollPane(generateLog);
        JPanel panel2 = new JPanel();
        panel2.setBounds(15, 150, 500, 180);
        panel2.add(generateLogScrollPane);

        // Panel 3 - Show output button
        showOutputDirButton = new JButton("Show output directory");
        showPubKeyButton = new JButton("Open public key file");
        showPriKeyButton = new JButton("Open private key file");
        showOutputDirButton.setPreferredSize(new Dimension(160, 20));
        showPubKeyButton.setPreferredSize(new Dimension(155, 20));
        showPriKeyButton.setPreferredSize(new Dimension(155, 20));
        showOutputDirButton.addActionListener(this);
        showPubKeyButton.addActionListener(this);
        showPriKeyButton.addActionListener(this);
        showOutputDirButton.setEnabled(false);
        showPubKeyButton.setEnabled(false);
        showPriKeyButton.setEnabled(false);

        JPanel panel3 = new JPanel();
        panel3.setBounds(260, 15, 250, 110);
        panel3.setBorder(BorderFactory.createTitledBorder("Output"));
        panel3.add(showOutputDirButton);
        panel3.add(showPubKeyButton);
        panel3.add(showPriKeyButton);

        add(panel1);
        add(panel2);
        add(panel3);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateButton) {
            generateLog.append("Generating key pair..." + newline);

            new RSAKeyGenerator();

            generateLog.append("Key pair generated" + newline);
            generateLog.append("Public key file location:" + Constants.PUB_KEY_FILE.getPath() + newline);
            generateLog.append("Private key file location:" + Constants.PRI_KEY_FILE.getPath() + newline);
            generateLog.append(newline);

            showOutputDirButton.setEnabled(true);
            showPriKeyButton.setEnabled(true);
            showPubKeyButton.setEnabled(true);
        } else if (e.getSource() == clearButton) {
            generateLog.setText("");
            showOutputDirButton.setEnabled(false);
            showPubKeyButton.setEnabled(false);
            showPriKeyButton.setEnabled(false);
        } else if (e.getSource() == showOutputDirButton) {
            try {
                Desktop.getDesktop().open(new File(Constants.PUB_KEY_FILE.getParent()));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == showPubKeyButton) {
            try {
                Desktop.getDesktop().open(Constants.PUB_KEY_FILE);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == showPriKeyButton) {
            try {
                Desktop.getDesktop().open(Constants.PRI_KEY_FILE);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
