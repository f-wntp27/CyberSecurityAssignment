package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Dimension;
import java.awt.Desktop;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import aes.AESEncrypter;
import util.Constants;

public class AESEncrypterPanel extends JPanel implements ActionListener {
    private JButton openPubKeyButton;
    private JButton openPlainTextButton;
    private JButton callEncryptButton;
    private JButton showEncryptButton;
    private JButton clearInputButton;
    private JButton clearLogButton;
    private JFileChooser choosePubKeyFile;
    private JFileChooser choosePlainTextFolder;
    private JTextField pubKeyPathTextField;
    private JTextField plainTextPathTextField;
    private JTextArea encLog;

    public AESEncrypterPanel() {
        setLayout(null);

        /* Panel 1 - File chooser */
        JPanel panel1 = new JPanel();
        panel1.setBounds(15, 15, 500, 80);
        panel1.setBorder(BorderFactory.createTitledBorder("Choose public key and plaintext"));
        panel1.setLayout(null);

        /* Subpanel 1_1 - choose public key file */
        pubKeyPathTextField = new JTextField(27);
        choosePubKeyFile = new JFileChooser(Constants.INIT_KEY_DIR);
        openPubKeyButton = new JButton("Open");
        openPubKeyButton.setPreferredSize(new Dimension(75 ,20));
        openPubKeyButton.addActionListener(this);
        JPanel subPanel1_1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        subPanel1_1.setOpaque(false);
        subPanel1_1.setBounds(5, 15, 490, 30);
        subPanel1_1.add(new JLabel("Public key file path: "));
        subPanel1_1.add(pubKeyPathTextField);
        subPanel1_1.add(openPubKeyButton);

        /* Subpanel 1_2 - choose plaintext folder */
        plainTextPathTextField = new JTextField(27);
        choosePlainTextFolder = new JFileChooser(Constants.SOURCE_DIR);
        choosePlainTextFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        choosePlainTextFolder.setSelectedFile(new File(Constants.SOURCE_DIR));
        openPlainTextButton = new JButton("Open");
        openPlainTextButton.setPreferredSize(new Dimension(75 ,20));
        openPlainTextButton.addActionListener(this);
        JPanel subPanel1_2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        subPanel1_2.setOpaque(false);
        subPanel1_2.setBounds(5, 45, 490, 30);
        subPanel1_2.add(new JLabel("Plaintext folder: "));
        subPanel1_2.add(plainTextPathTextField);
        subPanel1_2.add(openPlainTextButton);

        panel1.add(subPanel1_1);
        panel1.add(subPanel1_2);

        /* Panel 2 - Encrypt button and Output button */
        callEncryptButton = new JButton("Start encryption");
        callEncryptButton.setPreferredSize(new Dimension(125 ,20));
        showEncryptButton = new JButton("Output folder");
        showEncryptButton.setPreferredSize(new Dimension(115 ,20));
        clearInputButton = new JButton("Clear input");
        clearInputButton.setPreferredSize(new Dimension(100 ,20));
        clearLogButton = new JButton("Clear log");
        clearLogButton.setPreferredSize(new Dimension(88 ,20));
        callEncryptButton.addActionListener(this);
        showEncryptButton.addActionListener(this);
        showEncryptButton.setEnabled(false);
        clearInputButton.addActionListener(this);
        clearLogButton.addActionListener(this);

        JPanel panel2 = new JPanel();
        panel2.setBounds(15, 95, 500, 60);
        panel2.setBorder(null);
        panel2.setBorder(BorderFactory.createTitledBorder("Output"));
        panel2.add(callEncryptButton);
        panel2.add(showEncryptButton);
        panel2.add(clearInputButton);
        panel2.add(clearLogButton);

        /* Panel 3 - Encryption log */
        encLog = new JTextArea(10,47);
        encLog.setEditable(false);
        JScrollPane encryptLogScrollPane = new JScrollPane(encLog);
        JPanel panel3 = new JPanel();
        panel3.setBounds(15, 155, 500, 180);
        panel3.add(encryptLogScrollPane);

        add(panel1);
        add(panel2);
        add(panel3);
    }

    public void actionPerformed(ActionEvent e)  {
        if (e.getSource() == openPubKeyButton) {
            int r = choosePubKeyFile.showOpenDialog(AESEncrypterPanel.this);

            if (r == JFileChooser.APPROVE_OPTION) {
                Constants.SELECT_PUB_KEY_FILE = choosePubKeyFile.getSelectedFile();
                pubKeyPathTextField.setText(Constants.SELECT_PUB_KEY_FILE.getAbsolutePath());
            }
        } else if (e.getSource() == openPlainTextButton) {
            int r = choosePlainTextFolder.showOpenDialog(AESEncrypterPanel.this);

            if (r == JFileChooser.APPROVE_OPTION) {
                Constants.SELECT_PLAINTEXT_FOLDER = choosePlainTextFolder.getSelectedFile();
                plainTextPathTextField.setText(Constants.SELECT_PLAINTEXT_FOLDER.getAbsolutePath());
                Constants.LIST_PLAINTEXT_FILE = Constants.SELECT_PLAINTEXT_FOLDER.listFiles();
            }
        } else if (e.getSource() == callEncryptButton) {
            encLog.append("-----------------------------\n");
            encLog.append("Encryption begin...\n");
            try {
                AESEncrypter aesEncrypter = new AESEncrypter();
                if (!aesEncrypter.getEncryptStatus()) {
                    throw new Exception("Encryption failed...");
                }
                encLog.append("AES: " + aesEncrypter.getAESKeyString() + "\n");
            } catch (Exception e1) {
                encLog.append(e1.getMessage() + "\n");
                return;
            }
            encLog.append("Encryption finished...\n\n");
            encLog.append("ciphertext path:\n");
            File outputDir = new File(Constants.CIPHERTEXT_FILE_DIR);
            File[] outputFiles = outputDir.listFiles();
            for (File outFile : outputFiles) {
                encLog.append(outFile.getPath() + "\n");
            }
            encLog.append("\n");
            encLog.append("localkey path:\n");
            encLog.append(Constants.OUTPUT_LOCALKEY.getPath() + "\n");
            
            showEncryptButton.setEnabled(true);
        } else if (e.getSource() == showEncryptButton) {
            try {
                Desktop.getDesktop().open(new File(Constants.CIPHERTEXT_FILE_DIR));
            } catch (Exception e1) {
                encLog.append("Error: directory not found\n");
            }
        } else if (e.getSource() == clearInputButton) {
            pubKeyPathTextField.setText("");
            plainTextPathTextField.setText("");
            Constants.SELECT_PUB_KEY_FILE = new File(pubKeyPathTextField.getText());
            Constants.SELECT_PLAINTEXT_FOLDER = new File(plainTextPathTextField.getText());
            choosePubKeyFile = new JFileChooser(Constants.INIT_KEY_DIR);
            choosePlainTextFolder.setSelectedFile(new File(Constants.SOURCE_DIR));
            
            showEncryptButton.setEnabled(false);
        } else if (e.getSource() == clearLogButton) {
            encLog.setText("");
        }
    }
}
