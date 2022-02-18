package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import aes.AESDecrypter;
import util.Constants;

public class AESDecrypterPanel extends JPanel implements ActionListener {
    private JButton openPriKeyButton;
    private JButton openLocalKeyButton;
    private JButton openCipherTextButton;
    private JButton callDecryptButton;
    private JButton showDecryptButton;
    private JButton clearInputButton;
    private JButton clearLogButton;
    private JFileChooser choosePriKeyFile;
    private JFileChooser chooseLocalKeyFile;
    private JFileChooser chooseCipherTextFile;
    private JTextField priKeyPathTextField;
    private JTextField localKeyPathTextField;
    private JTextField cipherTextPathTextField;
    private JTextArea decLog;
    File decryptedFile;

    public AESDecrypterPanel() {
        setLayout(null);

        /* Panel 1 - File chooser */
        JPanel panel1 = new JPanel();
        panel1.setBounds(15, 15, 500, 110);
        panel1.setBorder(BorderFactory.createTitledBorder("Choose private key, localKey, and ciphertext"));
        panel1.setLayout(null);

        /* Subpanel 1_1 - choose private key file */
        priKeyPathTextField = new JTextField(27);
        choosePriKeyFile = new JFileChooser(Constants.INIT_KEY_DIR);
        openPriKeyButton = new JButton("Open");
        openPriKeyButton.setPreferredSize(new Dimension(75 ,20));
        openPriKeyButton.addActionListener(this);
        JPanel subPanel1_1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        subPanel1_1.setOpaque(false);
        subPanel1_1.setBounds(5, 15, 490, 30);
        subPanel1_1.add(new JLabel("Private key file path: "));
        subPanel1_1.add(priKeyPathTextField);
        subPanel1_1.add(openPriKeyButton);

        /* Subpanel 1_2 - choose localKey file */
        localKeyPathTextField = new JTextField(27);
        chooseLocalKeyFile = new JFileChooser(Constants.INIT_KEY_DIR);
        openLocalKeyButton = new JButton("Open");
        openLocalKeyButton.setPreferredSize(new Dimension(75 ,20));
        openLocalKeyButton.addActionListener(this);
        JPanel subPanel1_2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        subPanel1_2.setOpaque(false);
        subPanel1_2.setBounds(5, 45, 490, 30);
        subPanel1_2.add(new JLabel("localKey file path: "));
        subPanel1_2.add(localKeyPathTextField);
        subPanel1_2.add(openLocalKeyButton);

        /* Subpanel 1_3 - choose ciphertext folder */
        cipherTextPathTextField = new JTextField(27);
        chooseCipherTextFile = new JFileChooser(Constants.SOURCE_DIR);
        chooseCipherTextFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        openCipherTextButton = new JButton("Open");
        openCipherTextButton.setPreferredSize(new Dimension(75 ,20));
        openCipherTextButton.addActionListener(this);
        JPanel subPanel1_3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        subPanel1_3.setOpaque(false);
        subPanel1_3.setBounds(5, 75, 490, 30);
        subPanel1_3.add(new JLabel("Encrypted file path: "));
        subPanel1_3.add(cipherTextPathTextField);
        subPanel1_3.add(openCipherTextButton);

        panel1.add(subPanel1_1);
        panel1.add(subPanel1_2);
        panel1.add(subPanel1_3);

        /* Panel 2 - Decrypt button and show file after decrypted */
        callDecryptButton = new JButton("Start decryption");
        callDecryptButton.setPreferredSize(new Dimension(125 ,20));
        showDecryptButton = new JButton("Output file");
        showDecryptButton.setPreferredSize(new Dimension(115 ,20));
        clearInputButton = new JButton("Clear input");
        clearInputButton.setPreferredSize(new Dimension(100 ,20));
        clearLogButton = new JButton("Clear log");
        clearLogButton.setPreferredSize(new Dimension(88 ,20));

        callDecryptButton.addActionListener(this);
        showDecryptButton.addActionListener(this);
        showDecryptButton.setEnabled(false);
        clearInputButton.addActionListener(this);
        clearLogButton.addActionListener(this);

        JPanel panel2 = new JPanel();
        panel2.setBounds(15, 125, 500, 60);
        panel2.setBorder(null);
        panel2.setBorder(BorderFactory.createTitledBorder("Output"));
        panel2.add(callDecryptButton);
        panel2.add(showDecryptButton);
        panel2.add(clearInputButton);
        panel2.add(clearLogButton);

        /* Panel 3 - Decryption log */
        decLog = new JTextArea(8, 47);
        decLog.setEditable(false);
        JScrollPane decryptLogScrollPane = new JScrollPane(decLog);
        JPanel panel3 = new JPanel();
        panel3.setBounds(15, 185, 500, 140);
        panel3.add(decryptLogScrollPane);
        
        
        add(panel1);
        add(panel2);
        add(panel3);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openPriKeyButton) {
            int r = choosePriKeyFile.showOpenDialog(AESDecrypterPanel.this);

            if (r == JFileChooser.APPROVE_OPTION) {
                Constants.SELECT_PRI_KEY_FILE = choosePriKeyFile.getSelectedFile();
                priKeyPathTextField.setText(Constants.SELECT_PRI_KEY_FILE.getAbsolutePath());
            }
        } else if (e.getSource() == openLocalKeyButton) {
            int r = chooseLocalKeyFile.showOpenDialog(AESDecrypterPanel.this);

            if (r == JFileChooser.APPROVE_OPTION) {
                Constants.SELECT_LOCALKEY_FILE = chooseLocalKeyFile.getSelectedFile();
                localKeyPathTextField.setText(Constants.SELECT_LOCALKEY_FILE.getAbsolutePath());
            }
        } else if (e.getSource() == openCipherTextButton) {
            int r = chooseCipherTextFile.showOpenDialog(AESDecrypterPanel.this);

            if (r == JFileChooser.APPROVE_OPTION) {
                Constants.SELECT_CIPHERTEXT_FOLDER = chooseCipherTextFile.getSelectedFile();
                cipherTextPathTextField.setText(Constants.SELECT_CIPHERTEXT_FOLDER.getAbsolutePath());
                Constants.LIST_CIPHERTEXT_FILE = Constants.SELECT_CIPHERTEXT_FOLDER.listFiles();
            }

        } else if (e.getSource() == callDecryptButton) {
            decLog.append("-----------------------------\n");
            decLog.append("Decryption begin...\n");
            AESDecrypter aesDecrypter;
            try {
                aesDecrypter = new AESDecrypter();
                if (!aesDecrypter.getDecryptStatus()) {
                    throw new Exception("Decryption failed...");
                }
                decLog.append("AES: " + aesDecrypter.getAESKeyString() + "\n");
            } catch (Exception e1) {
                decLog.append(e1.getMessage() + "\n");
                return;
            }
            decLog.append("Decryption finished...\n\n");
            
            decLog.append("decrypted file path:\n");
            File outputDir = new File(Constants.DECRYPT_FILE_DIR);
            File[] outputFiles = outputDir.listFiles();
            for (File outFile : outputFiles) {
                decLog.append(outFile.getPath() + "\n");
            }

            showDecryptButton.setEnabled(true);

        } else if (e.getSource() == showDecryptButton) {
            try {
                Desktop.getDesktop().open(new File(Constants.DECRYPT_FILE_DIR));
            } catch (Exception e1) {
                decLog.append("Error: directory not found");
            }
        } else if (e.getSource() == clearInputButton) {
            choosePriKeyFile = new JFileChooser(Constants.INIT_KEY_DIR);
            chooseLocalKeyFile = new JFileChooser(Constants.INIT_KEY_DIR);
            chooseCipherTextFile.setSelectedFile(new File(Constants.SOURCE_DIR));

            priKeyPathTextField.setText("");
            Constants.SELECT_PRI_KEY_FILE = new File(priKeyPathTextField.getText());
            localKeyPathTextField.setText("");
            Constants.SELECT_LOCALKEY_FILE = new File(localKeyPathTextField.getText());
            cipherTextPathTextField.setText("");
            Constants.SELECT_CIPHERTEXT_FOLDER = new File(cipherTextPathTextField.getText());

            showDecryptButton.setEnabled(false);
        } else if (e.getSource() == clearLogButton) {
            decLog.setText("");
        }
    }
}
